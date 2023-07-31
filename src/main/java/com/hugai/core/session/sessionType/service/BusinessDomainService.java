package com.hugai.core.session.sessionType.service;

import com.hugai.core.openai.entity.response.api.CompletionResponse;
import com.theokanning.openai.completion.CompletionRequest;

import java.util.List;
import java.util.function.Consumer;

/**
 * 会话策略 业务接口
 *
 * @author WuHao
 * @since 2023/6/5 13:24
 */
public interface BusinessDomainService extends BusinessService, BusinessChatService {

    /**
     * 发送消息
     *
     * @return
     */
    default List<CompletionResponse> requestOpenApiCompletion() {
        return this.requestOpenApiCompletion(completionRequest -> {
        });
    }

    List<CompletionResponse> requestOpenApiCompletion(Consumer<CompletionRequest> requestConsumer);

}
