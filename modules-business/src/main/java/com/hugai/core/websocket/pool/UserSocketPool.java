package com.hugai.core.websocket.pool;

import cn.hutool.core.collection.CollUtil;
import com.hugai.core.websocket.endpoint.SocketPointUser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WuHao
 * @since 2023/8/21 16:42
 */
public class UserSocketPool {

    public static final ConcurrentHashMap<String, Map<String, SocketPointUser>> CACHE = new ConcurrentHashMap<>();

    public static Map<String, SocketPointUser> get(String userId) {
        return CACHE.get(userId);
    }

    public static SocketPointUser get(String userId, String sessionId) {
        Map<String, SocketPointUser> map = CACHE.get(userId);
        if (CollUtil.isEmpty(map)) {
            return null;
        }
        return map.get(sessionId);
    }

    public static void add(String userId, SocketPointUser session) {
        if (!CACHE.containsKey(userId)) {
            CACHE.put(userId, new HashMap<>());
        }
        CACHE.get(userId).put(session.getSession().getId(), session);
    }

    public static void remove(String userId, String sessionId) {
        Map<String, SocketPointUser> map = CACHE.get(userId);
        if (CollUtil.isEmpty(map)) {
            CACHE.remove(userId);
        } else {
            map.remove(sessionId);
        }
    }

}
