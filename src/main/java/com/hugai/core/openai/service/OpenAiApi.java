package com.hugai.core.openai.service;

import com.hugai.core.openai.model.account.Usage;
import com.hugai.core.openai.model.account.UserGrants;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author WuHao
 * @since 2023/6/1 14:24
 */
public interface OpenAiApi extends com.theokanning.openai.client.OpenAiApi {

    @GET("/v1/dashboard/billing/subscription")
    Single<UserGrants> getUserGrants();

    @GET("/v1/dashboard/billing/usage")
    Single<Usage> getUserUsage(@Query("start_date") String startData, @Query("end_date") String endData);

}
