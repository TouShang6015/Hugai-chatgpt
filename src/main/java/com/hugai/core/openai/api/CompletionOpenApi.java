package com.hugai.core.openai.api;

import com.hugai.core.openai.entity.response.api.CompletionResponse;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
     * @param sse
     * @return
     */
    List<CompletionResponse> streamCompletion(Supplier<CompletionRequest> requestSupplier, SseEmitter sse);

    /**
     * 非流式请求
     *
     * @param requestSupplier
     * @param sse
     * @return
     */
    List<CompletionResponse> normalCompletion(Supplier<CompletionRequest> requestSupplier, SseEmitter sse);

}
