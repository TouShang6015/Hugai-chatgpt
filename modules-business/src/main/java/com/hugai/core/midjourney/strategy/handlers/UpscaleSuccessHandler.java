package com.hugai.core.midjourney.strategy.handlers;

import com.hugai.core.midjourney.common.enums.MessageType;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.hugai.core.midjourney.strategy.MessageStrategyAbstract;
import com.hugai.core.midjourney.common.utils.ContentParseData;
import com.hugai.core.midjourney.common.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * upscale消息处理.
 * 完成(create): **cat** - Upscaled (Beta或Light) by <@1083152202048217169> (fast)
 * 完成(create): **cat** - Upscaled by <@1083152202048217169> (fast)
 * 完成(create): **cat** - Image #1 <@1012983546824114217>
 */
@Slf4j
@Component
public class UpscaleSuccessHandler extends MessageStrategyAbstract {
    private static final String CONTENT_REGEX_1 = "\\*\\*(.*?)\\*\\* - Upscaled \\(.*?\\) by <@\\d+> \\((.*?)\\)";
    private static final String CONTENT_REGEX_2 = "\\*\\*(.*?)\\*\\* - Upscaled by <@\\d+> \\((.*?)\\)";
    private static final String CONTENT_REGEX_3 = "\\*\\*(.*?)\\*\\* - Image #\\d <@\\d+>";

    @Override
    protected MjStrategyTypeEnum getType() {
        return MjStrategyTypeEnum.UpscaleSuccess;
    }

    @Override
    public void handle(MessageType messageType, DataObject message) {
        String content = getMessageContent(message);
        ContentParseData parseData = getParseData(content);
        if (MessageType.CREATE.equals(messageType) && parseData != null && hasImage(message)) {
            findAndFinishImageTask(parseData.getPrompt(), message);
            log.debug("UpscaleSuccessHandler u: prompt:{} | status:{}", parseData.getPrompt(), parseData.getStatus());
        }
    }

    private ContentParseData getParseData(String content) {
        ContentParseData parseData = ConvertUtils.parseContent(content, CONTENT_REGEX_1);
        if (parseData == null) {
            parseData = ConvertUtils.parseContent(content, CONTENT_REGEX_2);
        }
        if (parseData != null) {
            return parseData;
        }
        Matcher matcher = Pattern.compile(CONTENT_REGEX_3).matcher(content);
        if (!matcher.find()) {
            return null;
        }
        parseData = new ContentParseData();
        parseData.setPrompt(matcher.group(1));
        parseData.setStatus("done");
        return parseData;
    }

}
