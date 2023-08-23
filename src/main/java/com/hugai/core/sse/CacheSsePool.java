package com.hugai.core.sse;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.org.bebas.core.function.OR;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;
import java.util.Set;

/**
 * sse缓存池
 * <p> 用的 hutool的 {@link TimedCache}</p>
 *
 * @author WuHao
 * @since 2023/6/1 16:33
 */
public class CacheSsePool {

    /**
     * 默认缓存过期时长 毫秒 2分钟
     */
    public final static long DEFAULT_TIMEOUT = 1000 * 60 * 2;

    private static final TimedCache<String, SseEmitter> CACHE = CacheUtil.newTimedCache(DEFAULT_TIMEOUT);

    static {
        // 定时清理
        CACHE.schedulePrune(1000 * 20);
    }

    /**
     * 获取缓存
     *
     * @param sseId
     * @return
     */
    public static SseEmitter get(String sseId) {
        return CACHE.get(sseId);
    }

    /**
     * 添加缓存
     *
     * @param sseId
     * @param sse
     */
    public static void add(String sseId, SseEmitter sse) {
        CACHE.put(sseId, sse);
    }

    public static void remove(String sseId) {
        OR.run(get(sseId),Objects::nonNull, ResponseBodyEmitter::complete);
        CACHE.remove(sseId);
    }

    /**
     * 刷新缓存
     *
     * @param sseId
     */
    public static void flushCache(String sseId) {
        OR.run(CACHE.get(sseId), Objects::nonNull, sse -> CACHE.put(sseId, sse, DEFAULT_TIMEOUT));
    }

    /**
     * 获取缓存池的key集合
     *
     * @return
     */
    public static Set<String> poolSet() {
        return CACHE.keySet();
    }

    /**
     * 获取缓存池key数量
     *
     * @return
     */
    public static int poolCacheSize() {
        return poolSet().size();
    }

}
