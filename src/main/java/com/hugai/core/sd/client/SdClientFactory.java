package com.hugai.core.sd.client;

import com.hugai.common.utils.okhttp.OkhttpClientUtil;
import com.hugai.core.sd.client.api.Api;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.spring.SpringUtils;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;

/**
 * @author WuHao
 * @since 2023/9/11 10:35
 */
public class SdClientFactory {

    public static OkHttpClient getClient() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(chain -> {
                    // 设置请求头
                    String token = "";
                    Request request = chain.request()
                            .newBuilder()
                            .header("Authorization" , "Bearer " + token)
                            .build();
                    return chain.proceed(request);
                })
                .connectionPool(new ConnectionPool(20, 10L, TimeUnit.MINUTES)).build();

        return client;
    }

    public static SdApiClientService createService() {
        OkHttpClient client = getClient();

        String sdHostUrl = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceDraw().getSdHostUrl();

        Retrofit retrofit = OkhttpClientUtil.defaultRetrofit(client, sdHostUrl);

        return new SdApiClientService(retrofit.create(Api.class));

    }

}
