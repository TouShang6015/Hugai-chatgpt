package com.hugai.core.midjourney.client;

import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.midjourney.common.constants.DiscordConstant;
import com.hugai.core.midjourney.common.constants.SocketClientParamConstants;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.core.midjourney.listener.DiscordSocketListener;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.org.bebas.core.spring.SpringUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author WuHao
 * @since 2023/9/25 14:47
 */
public class DiscordSocketClient {

    /**
     * 全局帐号存储
     */
    private static Set<DiscordAccount> discordAccountList = new HashSet<>();

    /**
     * 简历Discord ws连接，根据配置帐号
     *
     * @param discordAccount
     * @return
     */
    public static WebSocket connection(DiscordAccount discordAccount) {
        if (Objects.isNull(discordAccount)) {
            return null;
        }
        Request request = getRequest(discordAccount);
        ResourceMainVO resourceMain = SpringUtils.getBean(BaseResourceWebApi.class).getResourceMain();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .proxy(
                        new Proxy(Proxy.Type.HTTP, new InetSocketAddress(resourceMain.getProxyHost(), resourceMain.getProxyPort()))
                ).build();
        return client.newWebSocket(request, new DiscordSocketListener(discordAccount));
    }

    private static Request getRequest(DiscordAccount discordAccount) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(DiscordConstant.DISCORD_WSS_URL + "/?encoding=json&v=9&compress=zlib-stream");
        SocketClientParamConstants.getDefaultBrowserHeaders(discordAccount).forEach(requestBuilder::header);
        return requestBuilder.build();
    }

    public static Set<DiscordAccount> getDiscordAccountList() {
        return discordAccountList;
    }
}
