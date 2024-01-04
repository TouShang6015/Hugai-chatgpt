package com.hugai.core.midjourney.strategy.handlers;


import com.hugai.core.midjourney.common.enums.MessageType;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.hugai.core.midjourney.strategy.MessageStrategyAbstract;
import com.hugai.core.midjourney.common.utils.ContentParseData;
import com.hugai.core.midjourney.common.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * blend消息处理.
 * 完成(create): **<https://s.mj.run/JWu6jaL1D-8> <https://s.mj.run/QhfnQY-l68o> --v 5.1** - <@1012983546824114217> (relaxed)
 */
@Slf4j
@Component
public class BlendSuccessHandler extends MessageStrategyAbstract {

    @Override
    public void handle(MessageType messageType, DataObject message) {
        String content = getMessageContent(message);
        ContentParseData parseData = ConvertUtils.parseContent(content);
        if (parseData == null || !MessageType.CREATE.equals(messageType)) {
            return;
        }
        Optional<DataObject> interaction = message.optObject("interaction");
        if (interaction.isPresent() && "blend".equals(interaction.get().getString("name"))) {
            log.info("messageHandler blend start : nonce:{}", getMessageNonce(message));
            if (hasImage(message)) {
                findAndFinishImageTask(parseData.getPrompt(), message);
            }
        }
    }

    @Override
    protected MjStrategyTypeEnum getType() {
        return MjStrategyTypeEnum.Blend;
    }

}
