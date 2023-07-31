package com.hugai.core.session.sessionType.service;

import com.hugai.core.openai.entity.response.api.ChatResponse;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;

import java.util.List;
import java.util.function.Consumer;

/**
 * 会话策略 业务接口
 *
 * @author WuHao
 * @since 2023/6/5 13:24
 */
public interface BusinessChatService extends BusinessService {

    /**
     * 发送消息 聊天/文本
     *
     * @return
     */
    default List<ChatResponse> requestOpenApiChat() {
        return requestOpenApiChat(request -> {
        });
    }

    List<ChatResponse> requestOpenApiChat(Consumer<ChatCompletionRequest> requestConsumer);

}
