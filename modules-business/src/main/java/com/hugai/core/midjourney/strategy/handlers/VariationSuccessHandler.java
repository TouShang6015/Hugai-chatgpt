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
 * variation消息处理.
 * 完成(create): **cat** - Variations (Strong或Subtle) by <@1012983546824114217> (relaxed)
 * 完成(create): **cat** - Variations by <@1012983546824114217> (relaxed)
 */
@Slf4j
@Component
public class VariationSuccessHandler extends MessageStrategyAbstract {
    private static final String CONTENT_REGEX_1 = "\\*\\*(.*?)\\*\\* - Variations by <@\\d+> \\((.*?)\\)";
    private static final String CONTENT_REGEX_2 = "\\*\\*(.*?)\\*\\* - Variations \\(.*?\\) by <@\\d+> \\((.*?)\\)";

    @Override
    protected MjStrategyTypeEnum getType() {
        return MjStrategyTypeEnum.VariationSuccess;
    }

    @Override
    public void handle(MessageType messageType, DataObject message) {
        String content = getMessageContent(message);
        ContentParseData parseData = getParseData(content);
        if (MessageType.CREATE.equals(messageType) && parseData != null && hasImage(message)) {
            findAndFinishImageTask(parseData.getPrompt(), message);
            log.debug("VariationSuccessHandler v prompt:{},status:{}", parseData.getPrompt(), parseData.getStatus());
        }
    }

    private ContentParseData getParseData(String content) {
        ContentParseData parseData = ConvertUtils.parseContent(content, CONTENT_REGEX_1);
        if (parseData == null) {
            parseData = ConvertUtils.parseContent(content, CONTENT_REGEX_2);
        }
        return parseData;
    }

}
