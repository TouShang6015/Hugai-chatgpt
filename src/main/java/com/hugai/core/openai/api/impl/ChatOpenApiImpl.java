package com.hugai.core.openai.api.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.core.openai.api.ChatOpenApi;
import com.hugai.core.openai.entity.response.TokenUsageNum;
import com.hugai.core.openai.entity.response.api.ChatResponse;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.core.openai.utils.ContentUtil;
import com.hugai.core.openai.utils.TokenCalculateUtil;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author WuHao
 * @since 2023/5/31 16:23
 */
@Slf4j
@Service
public class ChatOpenApiImpl implements ChatOpenApi {

    /**
     * 通用发送
     *
     * @param chatCompletionRequest
     * @param serviceConsumer
     * @return
     */
    public List<ChatResponse> coreSend(ChatCompletionRequest chatCompletionRequest, BiConsumer<OpenAiService, Map<Integer, ChatResponse>> serviceConsumer) {

        OpenAiService service = AiServiceFactory.createService();

        Map<Integer, ChatResponse> apiResponseMap = MapUtil.newHashMap();
        for (int i = 0; i < chatCompletionRequest.getN(); i++) {
            apiResponseMap.put(i, ChatResponse.builder().index(i).contentSB(new StringBuilder()).build());
        }

        serviceConsumer.accept(service, apiResponseMap);

        List<ChatResponse> apiResponses = new ArrayList<>(apiResponseMap.values());

        // 计算消耗的token数量
        int requestTokenNum = TokenCalculateUtil.getTokenNumOfContents(chatCompletionRequest.getMessages());
        apiResponses.forEach(item -> {
            int responseTokenNum = TokenCalculateUtil.getTokenNumOfContent(item.getContent());
            item.setTokenUsageNum(
                    TokenUsageNum.builder()
                            .requestTokenUseNum(requestTokenNum)
                            .responseTokenUseNum(responseTokenNum)
                            .tokenUseNum(requestTokenNum + responseTokenNum)
                            .build()
            );
            item.setContent(item.getContentSB().toString());
        });
        log.info("[ChatOpenApi] - 响应数据处理结果：{}", JSON.toJSONString(apiResponses));
        return apiResponses;
    }

    /**
     * 流式请求
     *
     * @param chatCompletionRequestSupplier
     * @param sse
     * @return
     */
    @Override
    public List<ChatResponse> streamChat(Supplier<ChatCompletionRequest> chatCompletionRequestSupplier, SseEmitter sse) {
        ChatCompletionRequest chatCompletionRequest = chatCompletionRequestSupplier.get();

        log.info("[ChatOpenApi] streamChat 请求参数：{}", JSON.toJSONString(chatCompletionRequest));

        return this.coreSend(chatCompletionRequest, (service, apiResponseMap) -> {
            service.streamChatCompletion(chatCompletionRequest)
                    .doOnError(throwable -> {
                        throwable.printStackTrace();
                        int statusCode = ((OpenAiHttpException) throwable).statusCode;
                        if (HttpStatus.UNAUTHORIZED == statusCode){
                            SpringUtils.getBean(IOpenaiKeysService.class).removeByOpenaiKey(service.getToken());
                        }
                    })
                    .blockingForEach(chunk -> {
                        // 日志打印
                        log.info("[ChatOpenApi] streamChat 响应结果：{}", JSON.toJSONString(chunk));

                        List<ChatCompletionChoice> choices = chunk.getChoices();

                        Optional.ofNullable(choices).orElseGet(ArrayList::new).forEach(res -> {
                            ChatResponse apiResponse = apiResponseMap.get(res.getIndex());
                            ChatMessage message = Optional.ofNullable(res.getMessage()).orElseGet(ChatMessage::new);

                            OR.run(message.getRole(), StrUtil::isNotEmpty, apiResponse::setRole);
                            OR.run(res.getFinishReason(), StrUtil::isNotEmpty, apiResponse::setFinishReason);

                            String resContent = Optional.ofNullable(message.getContent()).orElse("");
                            apiResponse.getContentSB().append(resContent);
                            apiResponse.setContent(apiResponse.getContentSB().toString());

                            // sse发送
                            try {
                                resContent = ContentUtil.convertNormal(resContent);
                                sse.send(SseEmitter.event().data(resContent));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    });

            // 关闭资源
            service.shutdownExecutor();
        });
    }

    /**
     * 非流式请求
     *
     * @param chatCompletionRequestSupplier
     * @param sse
     * @return
     */
    @Override
    public List<ChatResponse> normalChat(Supplier<ChatCompletionRequest> chatCompletionRequestSupplier, SseEmitter sse) {
        ChatCompletionRequest chatCompletionRequest = chatCompletionRequestSupplier.get();

        log.info("[ChatOpenApi] normalChat 请求参数：{}", JSON.toJSONString(chatCompletionRequest));

        return this.coreSend(chatCompletionRequest, (service, apiResponseMap) -> {

            ChatCompletionResult response;
            try {
                response = service.createChatCompletion(chatCompletionRequest);
            }catch (OpenAiHttpException e){
                e.printStackTrace();
                int statusCode = e.statusCode;
                if (HttpStatus.UNAUTHORIZED == statusCode){
                    SpringUtils.getBean(IOpenaiKeysService.class).removeByOpenaiKey(service.getToken());
                }
                throw new BusinessException(e.getMessage());
            }

            log.info("[ChatOpenApi] normalChat 响应结果：{}", JSON.toJSONString(response));

            List<ChatCompletionChoice> choices = response.getChoices();

            Optional.ofNullable(choices).orElseGet(ArrayList::new).forEach(res -> {
                ChatResponse apiResponse = apiResponseMap.get(res.getIndex());
                ChatMessage message = Optional.ofNullable(res.getMessage()).orElseGet(ChatMessage::new);

                OR.run(message.getRole(), StrUtil::isNotEmpty, apiResponse::setRole);
                OR.run(res.getFinishReason(), StrUtil::isNotEmpty, apiResponse::setFinishReason);

                String resContent = Optional.ofNullable(message.getContent()).orElse("");
                apiResponse.getContentSB().append(resContent);

                // sse发送
                try {
                    resContent = ContentUtil.convertNormal(resContent);
                    sse.send(resContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

}
