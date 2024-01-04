package com.hugai.core.midjourney.strategy.handlers;


import com.hugai.core.midjourney.common.enums.MessageType;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.hugai.core.midjourney.common.utils.ContentParseData;
import com.hugai.core.midjourney.common.utils.ConvertUtils;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.core.midjourney.strategy.MessageStrategyAbstract;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Component
public class StartAndProgressHandler extends MessageStrategyAbstract {

    @Resource
    private MidjourneyTaskEventListener midjourneyTaskEventListener;

    @Override
    protected MjStrategyTypeEnum getType() {
        return MjStrategyTypeEnum.StartAndProgress;
    }

    @Override
    public void handle(MessageType messageType, DataObject message) {
        String nonce = getMessageNonce(message);
        String content = getMessageContent(message);
        String id = message.getString("id");
        ContentParseData parseData = ConvertUtils.parseContent(content);

        ContentParseData contentParseData = Optional.ofNullable(parseData).orElseGet(ContentParseData::new);

        if (MessageType.CREATE.equals(messageType)) {
            if (isError(message)) {
                return;
            }
            String applicationId = getApplicationId(message);
            String guildId = getGuildId(message);
            String channelId = getChannelId(message);

            midjourneyTaskEventListener.updateTask(nonce, id, applicationId, guildId, channelId);
            log.debug("[Discord Progress] - 任务创建 | status: {} , id: {} , taskId: {}, prompt：{}", contentParseData.getStatus(), id, nonce, contentParseData.getPrompt());

        } else if (MessageType.UPDATE.equals(messageType)) {
            log.info("[Discord Progress] - 任务进度 | status: {} , id: {} , prompt：{}", contentParseData.getStatus(), id, contentParseData.getPrompt());
            if ("0%".equals(contentParseData.getStatus())) {
                midjourneyTaskEventListener.startTask(id, contentParseData.getPrompt());
            }
        }
    }

    private boolean isError(DataObject message) {
        Optional<DataArray> embedsOptional = message.optArray("embeds");
        if (!embedsOptional.isPresent() || embedsOptional.get().isEmpty()) {
            return false;
        }
        DataObject embed = embedsOptional.get().getObject(0);
        return embed.getInt("color", 0) == 16711680;
    }

}
