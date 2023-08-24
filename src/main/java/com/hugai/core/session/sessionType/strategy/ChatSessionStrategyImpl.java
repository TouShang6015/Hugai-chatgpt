package com.hugai.core.session.sessionType.strategy;

import com.hugai.common.constants.Constants;
import com.hugai.common.enums.flow.SessionType;
import com.hugai.core.session.sessionType.manager.SessionStrategyManager;
import com.hugai.modules.session.entity.convert.SessionRecordConvert;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 聊天/多轮对话 策略实现
 *
 * @author WuHao
 * @since 2023/6/5 13:15
 */
public class ChatSessionStrategyImpl extends SessionStrategyManager {
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
