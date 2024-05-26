package com.hugai.chatsdk.ollama.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hugai.chatsdk.ollama.entity.ChatCompletionsRequest;
import com.hugai.chatsdk.ollama.entity.ChatCompletionsResponse;
import com.org.bebas.exception.BusinessException;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.HttpException;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * @author WuHao
 * @since 2024/5/25 4:34
 */
public record ApiService(Api api, ExecutorService executorService) {

    public ChatCompletionsResponse createChatCompletion(ChatCompletionsRequest request) {
        return execute(api.createChatCompletion(request));
    }

    public void streamChatCompletion(ChatCompletionsRequest request, Callback<ResponseBody> callback) {
        request.setStream(true);
        api.createChatCompletionStream(request).enqueue(callback);
    }

    public static <T> T execute(Single<T> apiCall) {
        try {
            return apiCall.blockingGet();
        } catch (HttpException e) {
            try {
                if (e.response() == null || e.response().errorBody() == null) {
                    throw e;
                }
                String errorBody = e.response().errorBody().string();
                throw new BusinessException(errorBody, e.code());
            } catch (IOException ex) {
                // couldn't parse OpenAI error
                throw e;
            }
        }
    }

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapper;
    }

    public void shutdownExecutor() {
        Objects.requireNonNull(this.executorService, "executorService must be set in order to shut down");
        this.executorService.shutdown();
    }

}
