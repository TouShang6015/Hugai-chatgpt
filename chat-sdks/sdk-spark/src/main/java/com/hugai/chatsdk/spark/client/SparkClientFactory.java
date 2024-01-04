package com.hugai.chatsdk.spark.client;

import com.hugai.chatsdk.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.entity.account.ClientParam;
import com.hugai.chatsdk.service.AccountCacheManager;
import com.hugai.chatsdk.spark.utils.SparkUtil;
import com.org.bebas.exception.BusinessException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WuHao
 * @since 2023/12/27 13:36
 */
public class SparkClientFactory implements AccountCacheManager {

    public static final ConcurrentHashMap<String, Tuple2<OkHttpClient, Request>> clientCacheMap = new ConcurrentHashMap<>();

    /**
     * 构建连接对象
     *
     * @param account
     * @return
     */
    public static Tuple2<OkHttpClient, Request> createClient(ChatSdkAccount account) {
        ClientParam clientParam = account.getClientParam();
        String apiToken = account.getApiToken();
        String apiSecret = account.getApiSecret();
        String hostUrl = clientParam.getBaseUrl();

        Tuple2<OkHttpClient, Request> client = clientCacheMap.get(apiToken + apiSecret);

        if (Objects.isNull(client)) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
                String authUrl = SparkUtil.getAuthUrl(hostUrl, apiToken, apiSecret);
                String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
                Request request = new Request.Builder().url(url).build();
                client = Tuples.of(okHttpClient, request);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("[Spark]构建连接失败");
            }
        }

        return client;
    }

    @Override
    public void cacheRemove(String key) {
        clientCacheMap.remove(key);
    }

    @Override
    public void cacheAll() {
        clientCacheMap.clear();
    }
}
