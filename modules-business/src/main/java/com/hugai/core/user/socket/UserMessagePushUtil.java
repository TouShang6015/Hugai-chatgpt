package com.hugai.core.user.socket;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.websocket.constants.ResultCode;
import com.hugai.core.websocket.endpoint.SocketPointUser;
import com.hugai.common.websocket.entity.SocketUserResult;
import com.hugai.core.websocket.pool.UserSocketPool;
import com.org.bebas.core.function.OR;

import java.util.Map;

/**
 * 用户消息推送
 *
 * @author WuHao
 * @since 2023/10/16 14:24
 */
public class UserMessagePushUtil {

    /**
     * 获取在线人数
     *
     * @return
     */
    public static long getOnlineAmount() {
        return UserSocketPool.CACHE.entrySet().parallelStream().map(Map.Entry::getValue).mapToLong(map -> map.keySet().size()).sum();
    }

    /**
     * 推送提示消息
     *
     * @param userId
     * @param resultCode
     * @param message
     */
    public static void pushMessageString(String userId, ResultCode resultCode, String message) {
        OR.run(UserSocketPool.get(userId), CollUtil::isNotEmpty, map -> {
            map.forEach((key, value) -> {
                value.sendMessage(SocketUserResult.builder()
                        .code(resultCode.getCode())
                        .message(message)
                        .status(true)
                        .build());
            });
        });
    }

    /**
     * 推送消息数据
     * @param userId
     * @param resultCode
     * @param data
     */
    public static void pushDataString(String userId, ResultCode resultCode, Object data) {
        OR.run(UserSocketPool.get(userId), CollUtil::isNotEmpty, map -> {
            map.forEach((key, value) -> {
                value.sendMessage(SocketUserResult.builder()
                        .code(resultCode.getCode())
                        .data(data)
                        .status(true)
                        .build());
            });
        });
    }

    /**
     * 广播消息
     */
    public static void broadcastMessage(ResultCode resultCode, String message, Object data) {
        SocketUserResult socketUserResult = SocketUserResult.builder()
                .status(true)
                .code(resultCode.getCode())
                .message(message)
                .data(data)
                .build();
        UserSocketPool.CACHE.entrySet().parallelStream().forEach(entry -> {
            Map<String, SocketPointUser> value = entry.getValue();
            if (CollUtil.isNotEmpty(value)) {
                value.forEach((k, v) -> {
                    v.sendMessage(socketUserResult);
                });
            }
        });
    }

}
