package com.hugai.core.midjourney.pool;

import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.org.bebas.core.function.OR;
import okhttp3.WebSocket;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * discord ws缓存池
 *
 * @author WuHao
 * @since 2023/9/25 14:55
 */
public class DiscordSocketAccountPool {

    private static final ConcurrentHashMap<String, DiscordAccountCacheObj> CACHE = new ConcurrentHashMap<>();

    public static DiscordAccountCacheObj get(String userName) {
        return CACHE.get(userName);
    }

    public static DiscordAccountCacheObj getOne() {
        return CACHE.values().stream().findFirst().orElse(null);
    }

    public static void add(String userName, DiscordAccountCacheObj cacheBean) {
        CACHE.put(userName, cacheBean);
    }

    public static void add(String userName, DiscordAccount discordAccount, WebSocket webSocket) {
        CACHE.put(userName, new DiscordAccountCacheObj(discordAccount, webSocket));
    }

    public static void update(String userName, String sessionId) {
        OR.run(get(userName), Objects::nonNull, cacheBean -> {
            cacheBean.setSessionId(sessionId);
        });
    }

    public static void remove(String userName) {
        OR.run(get(userName), Objects::nonNull, DiscordAccountCacheObj::cancel);
        CACHE.remove(userName);
    }

}
