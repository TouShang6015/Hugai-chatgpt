package com.hugai.core.session.sessionType.strategy;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.enums.flow.SessionType;
import com.hugai.core.session.entity.SessionCacheData;
import com.hugai.core.session.sessionType.manager.SessionStrategyManager;
import com.hugai.core.session.sessionType.service.BusinessChatService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * 单轮会话策略实现
 *
 * @author WuHao
 * @since 2023/6/5 13:14
 */
public class OnceSessionStrategyImpl extends SessionStrategyManager implements BusinessChatService {

    /**
     * sessionType标识
     *
     * @return
     */
    @Override
    public SessionType sessionTypeSign() {
        return SessionType.ONCE;
    }

    /**
     * openApi chat接口请求参数构建
     *
     * @return
     */
    protected Supplier<ChatCompletionRequest> openApiChatRequestBuild() {
        SessionCacheData cacheData = this.getCacheData();
        int onceToken = this.sessionTypeSign().modulesToken().getOnce();
        List<ChatMessage> chatMessages = CollUtil.newArrayList(new ChatMessage(ChatMessageRole.USER.value(), cacheData.getContent()));

        return () -> ChatCompletionRequest.builder()
                .model(this.getUseModel())
                .messages(chatMessages)
                .n(1)
                .maxTokens(onceToken)
                .logitBias(new HashMap<>())
                .build();
    }

}
