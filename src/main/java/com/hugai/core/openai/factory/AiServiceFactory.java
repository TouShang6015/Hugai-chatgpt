package com.hugai.core.openai.factory;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hugai.common.utils.RsaUtils;
import com.hugai.config.properties.KeyRsaConfig;
import com.hugai.core.apikey.OpenAiKeyFactory;
import com.hugai.core.openai.service.OpenAiApi;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.core.security.context.UserThreadLocal;
import com.hugai.modules.system.entity.vo.baseResource.ResourceOpenaiVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * openAiService 工厂
 * <p>* 如无需代理配置请使用 {@link OpenAiService}</p>
 *
 * @author WuHao
 * @since 2023/5/24 10:00
 */
public class AiServiceFactory {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    public static OkHttpClient getClient(String token) {
        ResourceOpenaiVO resourceOpenai = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceOpenai();

        Assert.notEmpty(token, () -> new RuntimeException("ApiKey不能为空，请检查参数配置"));

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new AuthenticationInterceptor(token))
                .connectionPool(new ConnectionPool(50, 3L, TimeUnit.MINUTES)).build();

        OkHttpClient.Builder clientBuilder = client.newBuilder();
        // 设置代理
        if (StrUtil.isNotEmpty(resourceOpenai.getProxyHost()) && StrUtil.isNotEmpty(resourceOpenai.getProxyHost())) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(resourceOpenai.getProxyHost(), resourceOpenai.getProxyPort()));
            clientBuilder.proxy(proxy);
        }
        return clientBuilder.build();
    }

    /**
     * 创建代理openAiService
     *
     * @param token
     * @return
     */
    public static OpenAiService createService(String token) {
        OkHttpClient client = getClient(token);

        ObjectMapper mapper = OpenAiService.defaultObjectMapper();

        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);

        return new OpenAiService(retrofit.create(OpenAiApi.class), client.dispatcher().executorService(), token);

    }

    public static OpenAiService createService(String token,String decryptToken) {
        OkHttpClient client = getClient(token);

        ObjectMapper mapper = OpenAiService.defaultObjectMapper();

        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);

        return new OpenAiService(retrofit.create(OpenAiApi.class), client.dispatcher().executorService(), token,decryptToken);

    }

    /**
     * 创建代理openAiService
     *
     * @return
     */
    public static OpenAiService createService() {
        Long userId = UserThreadLocal.getUserId();
        Assert.notNull(userId,() -> new BusinessException("未获取到线程内的用户信息"));
        String decryptKey = OpenAiKeyFactory.getKey(userId);
        String token = RsaUtils.decryptByPrivateKey(KeyRsaConfig.getPrivateKey(), decryptKey);
        return createService(token,decryptKey);
    }

}
