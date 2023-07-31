package com.hugai.modules.chat.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.constants.Constants;
import com.hugai.core.openai.entity.response.api.ChatResponse;
import com.hugai.core.openai.entity.response.api.CompletionResponse;
import com.hugai.core.openai.enums.RoleEnum;
import com.hugai.core.openai.utils.TokenCalculateUtil;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.session.sessionType.context.SessionBusinessContext;
import com.hugai.core.session.sessionType.service.BusinessChatService;
import com.hugai.core.session.sessionType.service.BusinessDomainService;
import com.hugai.core.session.valid.Send;
import com.hugai.core.session.valid.SendDomain;
import com.hugai.modules.session.entity.convert.SessionRecordConvert;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.hugai.modules.chat.service.ChatService;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.session.service.SessionRecordService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.utils.OptionalUtil;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 聊天会话 业务实现
 *
 * @author WuHao
 * @since 2023/6/6 10:52
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private SessionRecordService sessionRecordService;
    @Resource
    private SessionInfoService sessionInfoService;

    /**
     * 发送消息
     *
     * @param param
     */
    @Transactional
    @Override
    public void sendChatMessage(SessionCacheData param) {
        ValidatorUtil.validateEntity(param, Send.class);

        final Long sessionId = param.getSessionId();

        final String sessionType = param.getSessionType();
        param.setUserId(SecurityContextUtil.getUserId());

        List<SessionRecordModel> recordList = handleSessionChat(param, sessionId, sessionType);

        sessionRecordService.responseInsertHandle(param,recordList);
    }

    /**
     * 使用chat模型发送
     *
     * @param param
     * @param sessionId
     * @param sessionType
     * @return
     */
    @NotNull
    private List<SessionRecordModel> handleSessionChat(SessionCacheData param, Long sessionId, String sessionType) {
        BusinessChatService businessService = SessionBusinessContext.createContext().initChatService(sessionType).getService();
        // 初始化数据
        businessService.initCacheData(param);

        List<ChatResponse> allChatResponse = CollUtil.newArrayList();
        // 用户临时响应
        allChatResponse.add(ChatResponse.builder().role(RoleEnum.user.name()).content(param.getContent()).build());
        allChatResponse.addAll(businessService.requestOpenApiChat());
        // 添加记录
        List<SessionRecordModel> recordList = SessionRecordConvert.INSTANCE.chatResponseConvertModel(allChatResponse);
        recordList.forEach(item -> {
            ChatMessage chatMessage = SessionRecordConvert.INSTANCE.convertChatMessage(item);
            int consumerToken = TokenCalculateUtil.getTokenNumOfContents(chatMessage);
            item.setSessionId(sessionId);
            item.setConsumerToken(consumerToken);
            item.setIfShow(Constants.BOOLEAN.TRUE);
            item.setIfContext(Constants.BOOLEAN.TRUE);
            item.setIfDomainTop(Constants.BOOLEAN.FALSE);
        });
        return recordList;
    }

    /**
     * 领域会话发送消息
     *
     * @param param
     */
    @Transactional
    @Override
    public void sendDomainMessage(SessionCacheData param) {
        ValidatorUtil.validateEntity(param, SendDomain.class);

        final Long sessionId = param.getSessionId();

        final String sessionType = param.getSessionType();
        param.setUserId(SecurityContextUtil.getUserId());

//        List<SessionRecordModel> recordList = handleSessionDomain(param, sessionId, sessionType);
        List<SessionRecordModel> recordList = handleSessionChat(param, sessionId, sessionType);

        sessionRecordService.responseInsertHandle(param,recordList, list -> {
            OptionalUtil.ofNullList(list).forEach(item -> {
                item.setDomainUniqueKey(param.getDomainUniqueKey());
            });
        });
    }

    /**
     * 使用text模型发送（token消耗比较大）
     *
     * @param param
     * @param sessionId
     * @param sessionType
     * @return
     */
    @NotNull
    private List<SessionRecordModel> handleSessionDomain(SessionCacheData param, Long sessionId, String sessionType) {
        BusinessDomainService businessService = SessionBusinessContext.createContext().initDomainService(sessionType).getService();
        // 初始化数据
        businessService.initCacheData(param);

        List<CompletionResponse> allChatResponse = CollUtil.newArrayList();
        // 用户临时响应
        allChatResponse.add(CompletionResponse.builder().role(RoleEnum.user.name()).content(param.getContent()).build());
        allChatResponse.addAll(businessService.requestOpenApiCompletion());
        // 添加记录
        List<SessionRecordModel> recordList = SessionRecordConvert.INSTANCE.domainResponseConvertModel(allChatResponse);
        recordList.forEach(item -> {
            int consumerToken = TokenCalculateUtil.getTokenNumOfContent(item.getContent());
            item.setSessionId(sessionId);
            item.setConsumerToken(consumerToken);
            item.setIfShow(Constants.BOOLEAN.TRUE);
            item.setIfContext(Constants.BOOLEAN.TRUE);
            item.setIfDomainTop(Constants.BOOLEAN.FALSE);
            OR.run(item.getRole(), Objects::isNull, () -> item.setRole(RoleEnum.assistant.name()));
        });
        return recordList;
    }
}
