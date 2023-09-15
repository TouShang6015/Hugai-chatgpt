package com.hugai.core.openai.api.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.core.openai.api.CompletionOpenApi;
import com.hugai.core.openai.entity.response.TokenUsageNum;
import com.hugai.core.openai.entity.response.api.CompletionResponse;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.handler.MessageSendHandler;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.core.openai.utils.TokenCalculateUtil;
import com.org.bebas.core.function.OR;
import com.org.bebas.exception.BusinessException;
import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author WuHao
 * @since 2023/6/19 9:50
 */
@Slf4j
@Service
public class CompletionOpenApiImpl implements CompletionOpenApi {
    /**
     * 流式请求
     *
     * @param requestSupplier
     * @param connectId
     * @return
     */
    @Override
    public List<CompletionResponse> streamCompletion(Supplier<CompletionRequest> requestSupplier, String connectId) {
        CompletionRequest completionRequest = requestSupplier.get();

        log.info("[CompletionOpenApi] stream 请求参数：{}", JSON.toJSONString(completionRequest));

        MessageSendHandler messageSendHandler = new MessageSendHandler(connectId);

        List<CompletionResponse> completionResponses;
        try {
            completionResponses = this.coreSend(completionRequest, (service, apiResponseMap) -> {
                service.streamCompletion(completionRequest)
                        .doOnError(Throwable::printStackTrace)
                        .blockingForEach(chunk -> {
                            // 日志打印
                            log.info("[CompletionOpenApi] stream 响应结果：{}", JSON.toJSONString(chunk));

                            List<CompletionChoice> choices = chunk.getChoices();

                            Optional.ofNullable(choices).orElseGet(ArrayList::new).forEach(res -> {
                                CompletionResponse apiResponse = apiResponseMap.get(res.getIndex());

                                OR.run(res.getFinish_reason(), StrUtil::isNotEmpty, apiResponse::setFinishReason);

                                String content = Optional.ofNullable(apiResponse.getContent()).orElse("");
                                String resContent = Optional.ofNullable(res.getText()).orElse("");
                                apiResponse.setContent(content + resContent);

                                messageSendHandler.queueAdd(resContent);
                            });
                        });

                // 关闭资源
                service.shutdownExecutor();
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } finally {
            messageSendHandler.close();
        }

        return completionResponses;
    }

    /**
     * 正常请求
     *
     * @param requestSupplier
     * @param connectId
     * @return
     */
    @Override
    public List<CompletionResponse> normalCompletion(Supplier<CompletionRequest> requestSupplier, String connectId) {

        CompletionRequest request = requestSupplier.get();

        log.info("[CompletionOpenApi] normal 请求参数：{}", JSON.toJSONString(request));

        MessageSendHandler messageSendHandler = new MessageSendHandler(connectId);

        List<CompletionResponse> completionResponses;
        try {
            completionResponses = this.coreSend(request, (service, apiResponseMap) -> {

                CompletionResult response = service.createCompletion(request);

                //            log.info("[CompletionOpenApi] normal 响应结果：{}", JSON.toJSONString(response));

                List<CompletionChoice> choices = response.getChoices();

                Usage usage = response.getUsage();

                Optional.ofNullable(choices).orElseGet(ArrayList::new).forEach(res -> {
                    CompletionResponse apiResponse = apiResponseMap.get(res.getIndex());

                    OR.run(res.getFinish_reason(), StrUtil::isNotEmpty, apiResponse::setFinishReason);

                    String content = Optional.ofNullable(apiResponse.getContent()).orElse("");
                    String resContent = Optional.ofNullable(res.getText()).orElse("");
                    apiResponse.setContent(content + resContent);

                    apiResponse.setTokenUsageNum(
                            TokenUsageNum.builder()
                                    .requestTokenUseNum((int) usage.getPromptTokens())
                                    .responseTokenUseNum((int) usage.getCompletionTokens())
                                    .tokenUseNum((int) usage.getTotalTokens())
                                    .build()
                    );

                    messageSendHandler.queueAdd(resContent);
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } finally {
            messageSendHandler.close();
        }

        return completionResponses;
    }

    /**
     * 通用发送
     *
     * @param completionRequest
     * @param serviceConsumer
     * @return
     */
    public List<CompletionResponse> coreSend(CompletionRequest completionRequest, BiConsumer<OpenAiService, Map<Integer, CompletionResponse>> serviceConsumer) {

        OpenAiService service = AiServiceFactory.createService();

        Map<Integer, CompletionResponse> apiResponseMap = MapUtil.newHashMap();
        for (int i = 0; i < completionRequest.getN(); i++) {
            apiResponseMap.put(i, CompletionResponse.builder().index(i).build());
        }

        serviceConsumer.accept(service, apiResponseMap);

        List<CompletionResponse> apiResponses = new ArrayList<>(apiResponseMap.values());

        // 计算消耗的token数量
        int requestTokenNum = TokenCalculateUtil.getTokenNumOfContent(completionRequest.getPrompt());
        apiResponses.forEach(item -> {
            int responseTokenNum = TokenCalculateUtil.getTokenNumOfContent(item.getContent());
            OR.run(item.getTokenUsageNum(), Objects::isNull, () -> {
                TokenUsageNum tokenUsageNum = TokenUsageNum.builder().requestTokenUseNum(requestTokenNum).responseTokenUseNum(responseTokenNum).tokenUseNum(requestTokenNum + responseTokenNum).build();
                item.setTokenUsageNum(tokenUsageNum);
            });

        });
        log.info("[CompletionOpenApi] - 响应数据处理结果：{}", JSON.toJSONString(apiResponses));
        return apiResponses;
    }

}
