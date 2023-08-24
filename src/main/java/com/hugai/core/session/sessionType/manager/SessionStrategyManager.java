package com.hugai.core.session.sessionType.manager;

import cn.hutool.core.lang.Assert;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.flow.SessionStatus;
import com.hugai.core.openai.api.ChatOpenApi;
import com.hugai.core.openai.entity.response.api.ChatResponse;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.session.sessionType.service.BusinessChatService;
import com.hugai.core.session.sessionType.service.BusinessService;
import com.hugai.modules.session.entity.convert.SessionRecordConvert;
import com.hugai.modules.session.entity.dto.SessionRecordDTO;
import com.hugai.modules.session.entity.model.DomainModel;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.hugai.modules.system.entity.vo.baseResource.ResourceOpenaiVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.flowenum.utils.FlowEnumUtils;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 会话策略管理器
 *
 * @author WuHao
 * @since 2023/6/5 13:16
 */
public abstract class SessionStrategyManager extends SessionCacheDataManager<BusinessService> implements BusinessService, BusinessChatService {

    /**
     * 获取领域配置数据
     *
     * @return
     */
    @Override
    public DomainModel getDomainModel() {
        return null;
    }

    /**
     * 新增会话时初始化一条会话信息
     *
     * @return
     */
    @Override
    public SessionRecordDTO addSessionInitFirstRecord() {
        return null;
    }

    /**
     * 发送消息 聊天/文本
     *
     * @return
     */
    @Override
    public List<ChatResponse> requestOpenApiChat(Consumer<ChatCompletionRequest> requestConsumer) {
        SessionCacheData cacheData = this.getCacheData();
        String content = cacheData.getContent();
        Assert.notEmpty(content, () -> new BusinessException("发送内容不能为空"));

        ChatOpenApi chatOpenApi = SpringUtils.getBean(ChatOpenApi.class);

        ChatCompletionRequest chatCompletionRequest = this.openApiChatRequestBuild().get();
        requestConsumer.accept(chatCompletionRequest);

        return chatOpenApi.streamChat(() -> chatCompletionRequest, cacheData.getConnectId());
    }

    protected abstract Supplier<ChatCompletionRequest> openApiChatRequestBuild();

    /**
     * 计算会话可请求的有效上下文信息
     *
     * @return
     */
    @Override
    public List<SessionRecordModel> getSessionValidRequestContext() {
        Long sessionId = this.getCacheData().getSessionId();
        Assert.notNull(sessionId, () -> new BusinessException("会话策略异常，会话号不能为空"));

        SessionStatus sessionStatus = FlowEnumUtils.getEnumByKey(this.getDataSessionInfo().getStatus(), SessionStatus.class);
        Assert.isFalse(sessionStatus.equals(SessionStatus.END), () -> new BusinessException("当前会话已结束"));

        int contextMaxToken = this.getContextMaxToken();

        Deque<SessionRecordModel> deque = new LinkedList<>();

        int cursorToken = 0;        // token游标
        List<SessionRecordModel> recordList = this.getDataSessionRecordList().stream().sorted(Comparator.comparing(SessionRecordModel::getId).reversed()).collect(Collectors.toList());

        SessionRecordModel topRecord = recordList.stream().filter(item -> Constants.BOOLEAN.TRUE.equals(item.getIfDomainTop()) && Constants.BOOLEAN.TRUE.equals(item.getIfContext())).findFirst().orElse(null);
        if (Objects.nonNull(topRecord)){
            cursorToken += topRecord.getConsumerToken();
        }
        // 是否连续对话
        String ifConc = this.getCacheData().getIfConc();
        if (Objects.isNull(ifConc) || Constants.BOOLEAN.TRUE.equals(ifConc)) {
            // 过滤掉领域会话顶部元素
            List<SessionRecordModel> filterTopRecordList = recordList.stream().filter(item -> Objects.isNull(topRecord) || !item.getId().equals(topRecord.getId())).collect(Collectors.toList());
            for (SessionRecordModel recordItem : filterTopRecordList) {
                if (
                        !Constants.BOOLEAN.TRUE.equals(recordItem.getIfContext())
                                || Constants.DelFlag.DEL.equals(String.valueOf(recordItem.getDelFlag()))
                ){
                    // 非上下文、已删除的数据过滤掉
                    continue;
                }
                // 超过上限就跳出循环
                if (cursorToken >= contextMaxToken) break;

                cursorToken += recordItem.getConsumerToken();
                deque.offerFirst(recordItem);
            }
        }
        // 设置始终在上下文中的数据
        OR.run(topRecord, Objects::nonNull,deque::offerFirst);
        // 队列转换数组 返回有效请求信息
        return deque.stream().map(SessionRecordConvert.INSTANCE::convertToDTO).collect(Collectors.toList());
    }

    /**
     * 获取上下文可请求的最大token
     *
     * @return
     */
    @Override
    public int getContextMaxToken() {
        if (this.getCacheData().getContextMaxToken() == 0) {
            return this.sessionTypeSign().modulesToken().getContextMax();
        }
        return this.getCacheData().getContextMaxToken();
    }

    @Override
    public String getUseModel() {
        String useModel = null;
        useModel = Optional.ofNullable(this.getCacheData()).orElseGet(SessionCacheData::new).getUseModel();
        if (Objects.isNull(useModel)){
            DomainModel domainModel = this.getDomainModel();
            if (Objects.nonNull(domainModel)){
                useModel = domainModel.getUseModel();
            }
            if (Objects.isNull(useModel)){
                ResourceOpenaiVO resourceOpenai = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceOpenai();
                useModel = resourceOpenai.getChatModel();
            }
        }
        return useModel;
    }
}
