package com.hugai.common.pool;

import com.org.bebas.core.function.OR;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WuHao
 * @since 2023/8/21 16:42
 */
public class ChatSocketPool {

    /**
     * ws连接池
     */
    public static final ConcurrentHashMap<String, Session> CACHE = new ConcurrentHashMap<>();

    public static Session get(String connectId) {
        return CACHE.get(connectId);
    }

    /**
     *
     * @param connectId     - {@link Session.getId()}
     * @param session
     */
    public static void add(String connectId, Session session) {
        CACHE.put(connectId, session);
    }

    public static void remove(String connectId) {
        OR.run(get(connectId), Objects::nonNull, session -> {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CACHE.remove(connectId);
    }

}
