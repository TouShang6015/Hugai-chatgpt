package com.hugai.chatsdk.ollama.api;

import com.hugai.chatsdk.ollama.entity.ChatCompletionsRequest;
import com.hugai.chatsdk.ollama.entity.ChatCompletionsResponse;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

/**
 * @author wuhao
 * @since 2024/5/25 3:15
 */
public interface Api {

    @POST("/api/chat")
    Single<ChatCompletionsResponse> createChatCompletion(@Body ChatCompletionsRequest request);

    @Streaming
    @POST("/api/chat")
    Call<ResponseBody> createChatCompletionStream(@Body ChatCompletionsRequest request);

}
