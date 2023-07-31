package com.hugai.core.session.sessionType.strategy;

import cn.hutool.core.lang.Assert;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.flow.SessionType;
import com.hugai.core.openai.api.ChatOpenApi;
import com.hugai.core.openai.entity.response.api.ChatResponse;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.session.sessionType.manager.SessionStrategyManager;
import com.hugai.core.session.sessionType.service.BusinessChatService;
import com.hugai.core.sse.CacheSsePool;
import com.hugai.modules.session.entity.convert.SessionRecordConvert;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 聊天/多轮对话 策略实现
 *
 * @author WuHao
 * @since 2023/6/5 13:15
 */
public class ChatSessionStrategyImpl extends SessionStrategyManager implements BusinessChatService {
    /**
     * sessionType标识
     *
     * @return
     */
    @Override
    public SessionType sessionTypeSign() {
        return SessionType.CHAT;
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

        SseEmitter sse = CacheSsePool.get(cacheData.getSseId());

        ChatOpenApi chatOpenApi = SpringUtils.getBean(ChatOpenApi.class);

        ChatCompletionRequest chatCompletionRequest = this.openApiChatRequestBuild().get();
        requestConsumer.accept(chatCompletionRequest);

        return chatOpenApi.streamChat(() -> chatCompletionRequest, sse);
    }

    /**
     * openApi chat接口请求参数构建
     *
     * @return
     */
    protected Supplier<ChatCompletionRequest> openApiChatRequestBuild() {
        int onceToken = this.sessionTypeSign().modulesToken().getOnce();

        ChatMessage thisChatMessage = new ChatMessage(ChatMessageRole.USER.value(), this.getCacheData().getContent());

        List<ChatMessage> chatMessages = new ArrayList<>();
        String ifConc = this.getCacheData().getIfConc();
        if (Objects.isNull(ifConc) || Constants.BOOLEAN.TRUE.equals(ifConc)) {
            chatMessages.addAll(SessionRecordConvert.INSTANCE.convertChatMessage(this.getSessionValidRequestContext()));
        }
        chatMessages.add(thisChatMessage);

        return () -> ChatCompletionRequest.builder()
                .model(this.getUseModel())
                .messages(chatMessages)
                .n(1)
                .maxTokens(onceToken)
                .logitBias(new HashMap<>())
                .build();
    }
}
