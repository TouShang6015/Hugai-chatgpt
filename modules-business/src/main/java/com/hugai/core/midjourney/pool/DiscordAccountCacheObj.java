package com.hugai.core.midjourney.pool;

import cn.hutool.core.collection.CollUtil;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.org.bebas.core.function.OR;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * discord 账户缓存对象
 *
 * @author WuHao
 * @since 2023/9/26 13:31
 */
@Slf4j
@Data
public class DiscordAccountCacheObj {

    private DiscordAccount discordAccount;

    private WebSocket webSocket;

    private String sessionId;

    public DiscordAccountCacheObj(DiscordAccount discordAccount, WebSocket webSocket) {
        this.discordAccount = discordAccount;
        this.webSocket = webSocket;
    }

    public void cancel() {
        OR.run(this.webSocket, Objects::nonNull, WebSocket::cancel);
    }

    /**
     * 随机获取服务器与频道
     *
     * @return
     */
    public Tuple2<String, String> getOneChannelConfig() {
        List<Tuple2<String, String>> channelConfigList = this.discordAccount.getChannelConfigList();
        if (CollUtil.isEmpty(channelConfigList))
            return null;
        int size = channelConfigList.size();
        Random random = new Random();
        random.setSeed(size);
        Tuple2<String, String> tuples = channelConfigList.get(random.nextInt(size));
        log.debug("[MJ 随机获取服务器与频道] size: {},  guildId: {} , channelId: {}", channelConfigList.size(), tuples.getT1(), tuples.getT2());
        return tuples;
    }

}
