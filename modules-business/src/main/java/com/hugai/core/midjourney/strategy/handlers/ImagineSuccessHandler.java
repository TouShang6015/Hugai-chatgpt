package com.hugai.core.midjourney.strategy.handlers;

import com.hugai.core.midjourney.common.enums.MessageType;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.hugai.core.midjourney.strategy.MessageStrategyAbstract;
import com.hugai.core.midjourney.common.utils.ContentParseData;
import com.hugai.core.midjourney.common.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.springframework.stereotype.Component;

/**
 * imagine消息处理.
 * 完成(create): **cat** - <@1012983546824114217> (relaxed)
 */
@Slf4j
@Component
public class ImagineSuccessHandler extends MessageStrategyAbstract {
    private static final String CONTENT_REGEX = "\\*\\*(.*?)\\*\\* - <@\\d+> \\((.*?)\\)";

    @Override
    protected MjStrategyTypeEnum getType() {
        return MjStrategyTypeEnum.Imagine;
    }

    @Override
    public void handle(MessageType messageType, DataObject message) {
        String content = getMessageContent(message);
        ContentParseData parseData = ConvertUtils.parseContent(content, CONTENT_REGEX);
        log.info("[MJ Image Success ] content: {} | response : {}", content, message.toString());
        if (MessageType.CREATE.equals(messageType) && parseData != null && hasImage(message)) {
            findAndFinishImageTask(parseData.getPrompt(), message);
            log.info("任务完成：imagine - {}, message: {}", parseData.getPrompt(), message.toString());
        }
    }

}
