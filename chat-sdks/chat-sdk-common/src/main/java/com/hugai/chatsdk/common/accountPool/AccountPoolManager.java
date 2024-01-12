package com.hugai.chatsdk.common.accountPool;

import cn.hutool.core.lang.Assert;
import com.hugai.chatsdk.common.entity.account.ClientParam;
import com.hugai.chatsdk.common.service.AccountCacheManager;
import com.org.bebas.exception.BusinessException;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2023/11/29 14:50
 */
public class AccountPoolManager implements AccountCacheManager {

    public static ConcurrentHashMap<String, OkHttpClient> accountPool = new ConcurrentHashMap<>();

    /**
     * 获取 http client 通过 clientParam
     *
     * @param clientParam
     * @param requestConsumer
     * @return
     */
    public static OkHttpClient getClient(ClientParam clientParam, Consumer<Request.Builder> requestConsumer) {
        Assert.notNull(clientParam, () -> new BusinessException("对话SDK获取连接信息失败，连接不能为空"));

        String baseUrl = clientParam.getBaseUrl();
        Assert.notEmpty(baseUrl, () -> new BusinessException("对话SDK未找到源"));

        OkHttpClient client = accountPool.get(baseUrl);

        if (Objects.isNull(client)) {
            Integer timeoutValue = clientParam.getTimeoutValue();
            Integer maxConnect = clientParam.getMaxConnect();

            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(timeoutValue, TimeUnit.SECONDS)
                    .writeTimeout(timeoutValue, TimeUnit.SECONDS)
                    .readTimeout(timeoutValue, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Request.Builder requestBuilder = chain.request().newBuilder();
                        requestConsumer.accept(requestBuilder);
                        return chain.proceed(requestBuilder.build());
                    })
                    .connectionPool(new ConnectionPool(maxConnect, timeoutValue, TimeUnit.SECONDS));
            // 设置代理
            if (clientParam.getIfProxy()) {
                String proxyHost = clientParam.getProxyHost();
                Integer proxyPort = clientParam.getProxyPort();
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                clientBuilder.proxy(proxy);
            }

            client = clientBuilder.build();
        }
        return client;
    }

    @Override
    public void cacheRemove(String baseUrl) {
        accountPool.remove(baseUrl);
    }

    @Override
    public void cacheAll() {
        accountPool.clear();
    }
}
