package com.hugai.core.midjourney.client.api;

import com.alibaba.fastjson2.JSONObject;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author WuHao
 * @since 2023/9/25 13:57
 */
public interface Api {

    @POST("/api/v9/interactions")
    Single<JSONObject> interactions(@Body JSONObject param);

    @POST("/api/v9/interactions")
    Call<Void> interactionsVoid(@Body JSONObject param);

    @POST("/api/v9/channels/{channelId}/attachments")
    Single<String> attachments(@Path("channelId") String channelId, @Body JSONObject param);

    @POST("/api/v9/channels/{channelId}/messages")
    Single<String> messages(@Path("channelId") String channelId, @Body JSONObject param);

}
