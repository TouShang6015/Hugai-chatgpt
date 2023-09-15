package com.hugai.core.openai.handler.pool;

import cn.hutool.core.lang.Assert;
import com.hugai.core.openai.handler.MessageSendHandler;
import com.org.bebas.core.function.OR;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话流式消息发送池
 *
 * @author WuHao
 * @since 2023/9/15 13:44
 */
public class SessionMessageSendPool {

    public static final ConcurrentHashMap<String, MessageSendHandler> CACHE = new ConcurrentHashMap<>();

    public static MessageSendHandler get(String key) {
        return CACHE.get(key);
    }

    public static void add(String key, MessageSendHandler messageSendHandler) {
        Assert.notNull(key);
        Assert.notNull(messageSendHandler);
        CACHE.put(key, messageSendHandler);
    }

    public static void remove(String key) {
        OR.run(CACHE.get(key), Objects::nonNull, MessageSendHandler::stop);
        CACHE.remove(key);
    }

}
