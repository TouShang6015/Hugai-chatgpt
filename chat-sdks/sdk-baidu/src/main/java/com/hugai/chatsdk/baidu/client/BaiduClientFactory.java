package com.hugai.chatsdk.baidu.client;

import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.common.entity.account.ClientParam;
import com.hugai.chatsdk.common.service.AccountCacheManager;
import com.org.bebas.exception.BusinessException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WuHao
 * @since 2024/1/12 11:12
 */
public class BaiduClientFactory implements AccountCacheManager {

    public static final ConcurrentHashMap<String, OkHttpClient> clientCacheMap = new ConcurrentHashMap<>();

    /**
     * 构建连接对象
     *
     * @param account
     * @return
     */
    public static Tuple2<OkHttpClient, Request.Builder> createClient(ChatSdkAccount account, String accessToken) {
        ClientParam clientParam = account.getClientParam();
        String apiToken = account.getApiToken();
        String apiSecret = account.getApiSecret();
        String hostUrl = clientParam.getBaseUrl();

        OkHttpClient client = clientCacheMap.get(apiToken + apiSecret);

        if (Objects.isNull(client)) {
            try {
                client = new OkHttpClient().newBuilder().build();
                clientCacheMap.put(apiToken + apiSecret, client);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("[百度千帆] Client连接 构建连接失败");
            }
        }

        try {
            String finalBaseUrl = String.format(hostUrl + "?access_token=%s", accessToken);
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(finalBaseUrl);
            requestBuilder.addHeader("Content-Type", "application/json");
            return Tuples.of(client, requestBuilder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("[百度千帆] Client连接 构建连接失败");
        }

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
