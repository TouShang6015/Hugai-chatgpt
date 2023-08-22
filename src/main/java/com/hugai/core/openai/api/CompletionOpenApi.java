package com.hugai.core.openai.api;

import com.hugai.core.openai.entity.response.api.CompletionResponse;
import com.theokanning.openai.completion.CompletionRequest;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author WuHao
 * @since 2023/6/19 9:50
 */
public interface CompletionOpenApi {

    /**
     * 流式请求
     *
     * @param requestSupplier
     * @param connectId
     * @return
     */
    List<CompletionResponse> streamCompletion(Supplier<CompletionRequest> requestSupplier, String connectId);

    /**
     * 非流式请求
     *
     * @param requestSupplier
     * @param connectId
     * @return
     */
    List<CompletionResponse> normalCompletion(Supplier<CompletionRequest> requestSupplier, String connectId);

}
