package com.hugai.core.midjourney.client;

import com.hugai.common.constants.Constants;
import com.hugai.common.modules.entity.config.model.CmjChannelConfigModel;
import com.hugai.common.modules.entity.config.vo.CmjAccountDetailVO;
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
import reactor.util.function.Tuples;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/9/25 14:47
 */
public class DiscordSocketClient {

    /**
     * 全局帐号存储
     */
    private static Map<String, DiscordAccount> discordAccountMap = new ConcurrentHashMap<>();

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

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES);
        if (Constants.BOOLEAN.TRUE.equals(discordAccount.getIfProxy())){
            clientBuilder.proxy(
                    new Proxy(Proxy.Type.HTTP, new InetSocketAddress(resourceMain.getProxyHost(), resourceMain.getProxyPort()))
            );
        }
        OkHttpClient client = clientBuilder.build();
        WebSocket webSocket = client.newWebSocket(request, new DiscordSocketListener(discordAccount));

        DiscordSocketClient.getDiscordAccountMap().put(discordAccount.getUserName(), discordAccount);
        return webSocket;
    }

    private static Request getRequest(DiscordAccount discordAccount) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(DiscordConstant.DISCORD_WSS_URL + "/?encoding=json&v=9&compress=zlib-stream");
        SocketClientParamConstants.getDefaultBrowserHeaders(discordAccount).forEach(requestBuilder::header);
        return requestBuilder.build();
    }

    public static DiscordAccount buildAccount(CmjAccountDetailVO param) {
        DiscordAccount discordAccount = new DiscordAccount(param.getUserName(), param.getUserToken(), param.getUserAgent());
        List<CmjChannelConfigModel> channelConfigList = param.getChannelConfigList();
        discordAccount.setChannelConfigList(
                channelConfigList.stream().map(channelConfig -> Tuples.of(channelConfig.getGuildId(), channelConfig.getChannelId())).collect(Collectors.toList())
        );
        discordAccount.setChannelIds(param.getChannelIds());
        discordAccount.setAutoData(SocketClientParamConstants.getDefaultAutoData(discordAccount));
        discordAccount.setIfProxy(param.getIfProxy());
        return discordAccount;
    }

    public static Map<String, DiscordAccount> getDiscordAccountMap() {
        return discordAccountMap;
    }
}
