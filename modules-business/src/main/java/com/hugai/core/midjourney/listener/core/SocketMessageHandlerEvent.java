package com.hugai.core.midjourney.listener.core;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.core.midjourney.common.enums.MessageType;
import com.hugai.core.midjourney.strategy.MessageStrategyAbstract;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.util.Map;

/**
 * mj socket消息处理
 *
 * @author WuHao
 * @since 2023/9/25 17:19
 */
@Slf4j
public class SocketMessageHandlerEvent {

    private DataObject data;

    private DiscordAccount discordAccount;

    public static SocketMessageHandlerEvent init(DiscordAccount discordAccount, DataObject data) {
        SocketMessageHandlerEvent context = new SocketMessageHandlerEvent();
        context.discordAccount = discordAccount;
        context.data = data;
        return context;
    }

    public void execute() {
        MessageType messageType = MessageType.of(this.data.getString("t"));
        if (messageType == null || MessageType.DELETE == messageType) {
            return;
        }
        DataObject data = this.data.getObject("d");
        if (ignoreAndLogMessage(data, messageType)) {
            return;
        }
        ThreadUtil.sleep(50);
//        log.info("[Discord - execute] response messageType: {}, d: {}", messageType.name(), data.toString());

        log.debug("[MJ Socket] 响应：{} ", this.data.toString());
        Map<String, MessageStrategyAbstract> messageHandlerMap = SpringUtil.getBeansOfType(MessageStrategyAbstract.class);
        messageHandlerMap.forEach((k, strategyService) -> {
            strategyService.handle(messageType, data);
        });
    }

    private boolean ignoreAndLogMessage(DataObject data, MessageType messageType) {
        String channelId = data.getString("channel_id");
        if (!this.discordAccount.getChannelIds().contains(channelId)) {
            return true;
        }
        String authorName = data.optObject("author").map(a -> a.getString("username")).orElse("System");
        log.info("{} - {} - {}: {}", this.discordAccount.getUserName(), messageType.name(), authorName, data.opt("content").orElse(""));
        return false;
    }

}
