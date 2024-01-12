package com.hugai.core.chat.handlers;

import cn.hutool.core.collection.CollUtil;
import com.hugai.chatsdk.common.entity.convert.RecordConvert;
import com.hugai.chatsdk.common.entity.session.RecordData;
import com.hugai.chatsdk.common.utils.TokenCalculateUtil;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.ChatRole;
import com.hugai.common.modules.entity.config.model.ChatModelModel;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import com.hugai.core.chat.entity.ChatRequestParam;
import com.hugai.modules.config.service.IChatModelService;
import com.hugai.modules.config.service.IChatSdkService;
import com.hugai.modules.session.service.SessionRecordService;
import com.org.bebas.core.function.OR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/11/29 13:49
 */
@Service
public class ChatRequestHandler {

    @Resource
    private SessionRecordService sessionRecordService;
    @Resource
    private IChatSdkService chatSdkService;
    @Resource
    private IChatModelService chatModelService;

    /**
     * 构建对话请求参数内容
     *
     * @param chatRequestParam
     * @return
     */
    public List<RecordData> buildChatSdkParam(ChatRequestParam chatRequestParam) {
        String content = chatRequestParam.getContent();

        ChatModelModel chatModel = chatModelService.getById(chatRequestParam.getChatModelId());

        List<SessionRecordModel> sessionRecordModels = sessionRecordService.cacheGetListBySessionId(chatRequestParam.getSessionId());
        SessionRecordModel topRecord = sessionRecordModels.stream().filter(item -> Constants.BOOLEAN.TRUE.equals(item.getIfDomainTop()) && Constants.BOOLEAN.TRUE.equals(item.getIfContext())).findFirst().orElse(null);

        List<RecordData> buildList = CollUtil.newArrayList();
        // 添加顶部内容
        OR.run(topRecord, Objects::nonNull, e -> {
            buildList.add(RecordConvert.INSTANCE.sessionConvertRecord(e));
        });

        if (chatRequestParam.getIfConc()) {
            OR.run(this.getSessionContext(sessionRecordModels, topRecord, chatModel, content), CollUtil::isNotEmpty, e -> {
                buildList.addAll(RecordConvert.INSTANCE.sessionConvertRecord(e));
            });
        }

        buildList.add(
                RecordData.builder().role(ChatRole.user.name()).content(content).build()
        );
        return buildList;
    }

    /**
     * 获取上下文对话信息
     *
     * @param sessionRecordList
     * @param topRecord
     * @param chatModel
     * @return
     */
    private List<SessionRecordModel> getSessionContext(List<SessionRecordModel> sessionRecordList, SessionRecordModel topRecord, ChatModelModel chatModel, String userContent) {
        Integer maxToken = chatModel.getMaxToken();

        Deque<SessionRecordModel> deque = new LinkedList<>();

        int cursorToken = 0;        // token游标
        List<SessionRecordModel> recordList = sessionRecordList.stream().sorted(Comparator.comparing(SessionRecordModel::getId).reversed()).collect(Collectors.toList());

        if (Objects.nonNull(topRecord)) {
            cursorToken += topRecord.getConsumerToken();
        }
        cursorToken += TokenCalculateUtil.getTokenNumOfContent(userContent);

        List<SessionRecordModel> filterTopRecordList = recordList.stream().filter(item -> !item.getId().equals(Optional.ofNullable(topRecord).orElseGet(SessionRecordModel::new).getId())).collect(Collectors.toList());
        for (SessionRecordModel recordItem : filterTopRecordList) {
            if (
                    !Constants.BOOLEAN.TRUE.equals(recordItem.getIfContext()) || Constants.DelFlag.DEL.equals(String.valueOf(recordItem.getDelFlag()))
            ) {
                // 非上下文、已删除的数据过滤掉
                continue;
            }
            // 超过上限就跳出循环
            if (cursorToken >= maxToken) break;

            cursorToken += recordItem.getConsumerToken();
            deque.offerFirst(recordItem);
        }

        return new ArrayList<>(deque);
    }

    public SessionRecordService sessionRecordService() {
        return sessionRecordService;
    }

    public IChatSdkService chatSdkService() {
        return chatSdkService;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ChatRequestHandler that = (ChatRequestHandler) obj;
        return Objects.equals(this.sessionRecordService, that.sessionRecordService) &&
                Objects.equals(this.chatSdkService, that.chatSdkService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionRecordService, chatSdkService);
    }

    @Override
    public String toString() {
        return "ChatRequestHandler[" +
                "sessionRecordService=" + sessionRecordService + ", " +
                "chatSdkService=" + chatSdkService + ']';
    }


}
