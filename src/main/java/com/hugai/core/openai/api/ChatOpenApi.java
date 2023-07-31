package com.hugai.core.openai.api;

import cn.hutool.core.collection.CollUtil;
import com.hugai.core.openai.entity.response.api.ChatResponse;
import com.hugai.modules.system.entity.vo.baseResource.ResourceOpenaiVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.spring.SpringUtils;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author WuHao
 * @since 2023/5/31 15:26
 */
public interface ChatOpenApi {

    /**
     * 流式请求
     *
     * @param chatCompletionRequestSupplier
     * @param sse
     * @return
     */
    List<ChatResponse> streamChat(Supplier<ChatCompletionRequest> chatCompletionRequestSupplier, SseEmitter sse);

    /**
     * 流式请求
     *
     * @param content
     * @param sse
     * @return
     */
    default ChatResponse streamChat(String content, int maxToken, SseEmitter sse) {
        List<ChatMessage> chatMessages = CollUtil.newArrayList(new ChatMessage(ChatMessageRole.SYSTEM.value(), content));
        return this.streamChat(() -> {
                    ResourceOpenaiVO resourceOpenai = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceOpenai();
                    return ChatCompletionRequest.builder()
                            .model(resourceOpenai.getChatModel())
                            .messages(chatMessages)
                            .n(1)
                            .maxTokens(maxToken)
                            .logitBias(new HashMap<>())
                            .build();
                }
                , sse).get(0);
    }

    /**
     * 非流式请求
     *
     * @param chatCompletionRequestSupplier
     * @param sse
     * @return
     */
    List<ChatResponse> normalChat(Supplier<ChatCompletionRequest> chatCompletionRequestSupplier, SseEmitter sse);

    /**
     * 非流式请求
     *
     * @param content
     * @param sse
     * @return
     */
    default ChatResponse normalChat(String content, int maxToken, SseEmitter sse) {
        List<ChatMessage> chatMessages = CollUtil.newArrayList(new ChatMessage(ChatMessageRole.SYSTEM.value(), content));
        ResourceOpenaiVO resourceOpenai = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceOpenai();
        return this.normalChat(() ->
                        ChatCompletionRequest.builder()
                                .model(resourceOpenai.getChatModel())
                                .messages(chatMessages)
                                .n(1)
                                .maxTokens(maxToken)
                                .logitBias(new HashMap<>())
                                .build()
                , sse).get(0);
    }

}
