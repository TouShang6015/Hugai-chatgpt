package com.hugai.core.midjourney.strategy.handlers;


import cn.hutool.core.text.CharSequenceUtil;
import com.hugai.core.midjourney.common.enums.MessageType;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.hugai.core.midjourney.common.utils.ContentParseData;
import com.hugai.core.midjourney.common.utils.ConvertUtils;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.core.midjourney.strategy.MessageStrategyAbstract;
import com.org.bebas.core.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class StartAndProgressHandler extends MessageStrategyAbstract {

    @Override
    protected MjStrategyTypeEnum getType() {
        return MjStrategyTypeEnum.StartAndProgress;
    }

    @Override
    public void handle(MessageType messageType, DataObject message) {
        String nonce = getMessageNonce(message);
        String content = getMessageContent(message);
        ContentParseData parseData = ConvertUtils.parseContent(content);
        if (MessageType.CREATE.equals(messageType) && CharSequenceUtil.isNotBlank(nonce)) {
            if (isError(message)) {
                return;
            }
            SpringUtils.getBean(MidjourneyTaskEventListener.class).updateTask(nonce, parseData.getPrompt());
            log.debug("[Discord Progress] - 任务开始：{},status: {}", parseData.getPrompt(), parseData.getStatus());
        } else if (MessageType.UPDATE.equals(messageType) && parseData != null) {
            log.debug("[Discord Progress] - 任务进度：{},status: {}", parseData.getPrompt(), parseData.getStatus());
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
