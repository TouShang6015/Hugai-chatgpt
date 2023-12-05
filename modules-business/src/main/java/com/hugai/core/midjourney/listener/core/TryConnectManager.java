package com.hugai.core.midjourney.listener.core;

import com.hugai.core.midjourney.client.DiscordSocketClient;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ws重连管理
 *
 * @author WuHao
 * @since 2023/9/27 14:54
 */
@Slf4j
public class TryConnectManager {

    /**
     * 尝试重连次数
     */
    private static final int tryCount = 500;
    /**
     * 计时器间隔 毫秒
     */
    private static final long timeInterval = 1000 * 20;

    private static final ConcurrentHashMap<String, Boolean> socketDataState = new ConcurrentHashMap<>();

    public static void tryConnect(String userName) {
        Boolean socketState = socketDataState.get(userName);
        if (Objects.nonNull(socketState) && socketState) {
            return;
        }
        log.debug("[Discord - connect] 尝试重新连接 - 账户：{}", userName);
        socketDataState.put(userName, true);
        for (int i = 0; i < tryCount; i++) {
            if (Objects.isNull(socketDataState.get(userName)) || !socketDataState.get(userName)) {
                return;
            }
            log.debug("[Discord - connect] 重新连接 - 次数：{} | 账户：{}", i + 1, userName);
            DiscordAccount discordAccount = DiscordSocketClient.getDiscordAccountList().stream().filter(item -> item.getUserName().equals(userName)).findFirst().orElse(null);
            DiscordSocketClient.connection(discordAccount);
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeState(String userName) {
        socketDataState.remove(userName);
    }

}
