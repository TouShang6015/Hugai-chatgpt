package com.hugai.core.midjourney.client;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hugai.common.utils.okhttp.OkhttpClientUtil;
import com.hugai.core.midjourney.client.api.Api;
import com.hugai.core.midjourney.common.constants.DiscordConstant;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.spring.SpringUtils;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Discord http客户端
 *
 * @author WuHao
 * @since 2023/9/25 13:47
 */
public class DiscordApiClient {

    private Api api;

    private DiscordAccount discordAccount;

    public DiscordApiClient(DiscordAccount discordAccount) {
        this.discordAccount = discordAccount;
    }

    public static DiscordApiClient init(DiscordAccount discordAccount) {
        DiscordApiClient apiClient = new DiscordApiClient(discordAccount);
        Retrofit retrofit = OkhttpClientUtil.defaultRetrofit(apiClient.getClient(), DiscordConstant.DISCORD_SERVER_URL);
        apiClient.discordAccount = discordAccount;
        apiClient.api = retrofit.create(Api.class);
        return apiClient;
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .connectTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .header("Authorization", this.discordAccount.getUserToken())
                            .header("User-Agent", this.discordAccount.getUserAgent())
                            .build();
                    return chain.proceed(request);
                })
                .connectionPool(new ConnectionPool(20, 1L, TimeUnit.MINUTES));
        // 设置代理
        ResourceMainVO resourceMain = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceMain();
        if (StrUtil.isNotEmpty(resourceMain.getProxyHost()) && Objects.nonNull(resourceMain.getProxyPort())) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(resourceMain.getProxyHost(), resourceMain.getProxyPort()));
            builder.proxy(proxy);
        }
        return builder.build();
    }

    // =========================== api ===========================

    public void interactionsVoid(String param) {
        Assert.notEmpty(param);
        JSONObject jsonObject = JSON.parseObject(param);
        try {
            api.interactionsVoid(jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String attachments(String channelId, String param) {
        Assert.notEmpty(param);
        return OkhttpClientUtil.execute(api.attachments(channelId, JSON.parseObject(param)));
    }

    public String messages(String channelId, String param) {
        Assert.notEmpty(param);
        return OkhttpClientUtil.execute(api.messages(channelId, JSON.parseObject(param)));
    }

}
