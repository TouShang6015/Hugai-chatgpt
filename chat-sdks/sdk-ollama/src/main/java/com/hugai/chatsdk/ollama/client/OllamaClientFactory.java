package com.hugai.chatsdk.ollama.client;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hugai.chatsdk.common.accountPool.AccountPoolManager;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.account.ClientParam;
import com.hugai.chatsdk.common.service.AccountCacheManager;
import com.hugai.chatsdk.ollama.api.Api;
import com.hugai.chatsdk.ollama.api.ApiService;
import com.org.bebas.core.function.OR;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhao
 * @since 2024/5/26 3:34
 */
public class OllamaClientFactory implements AccountCacheManager {

    public static final ConcurrentHashMap<String, ApiService> serviceCacheMap = new ConcurrentHashMap<>();

    public static ApiService getService(ChatSdkAccount account) {

        ClientParam clientParam = account.getClientParam();
        String baseUrl = clientParam.getBaseUrl();

        ApiService apiService = serviceCacheMap.get(baseUrl);

        if (Objects.isNull(apiService)) {
            String apiToken = account.getApiToken();
            OkHttpClient client = AccountPoolManager.getClient(account.getClientParam(), request -> {
                OR.run(apiToken, StrUtil::isNotEmpty, () -> request.header("Authorization", "Bearer " + apiToken));
            });

            ObjectMapper mapper = ApiService.defaultObjectMapper();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiService = new ApiService(retrofit.create(Api.class), client.dispatcher().executorService());
        }

        return apiService;
    }

    @Override
    public void cacheRemove(String baseUrl) {
        serviceCacheMap.remove(baseUrl);
    }

    @Override
    public void cacheAll() {
        serviceCacheMap.clear();
    }
}
