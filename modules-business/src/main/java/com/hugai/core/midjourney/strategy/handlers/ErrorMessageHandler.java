package com.hugai.core.midjourney.strategy.handlers;

import cn.hutool.core.text.CharSequenceUtil;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.core.midjourney.common.enums.MessageType;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.hugai.core.midjourney.strategy.MessageStrategyAbstract;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Component
public class ErrorMessageHandler extends MessageStrategyAbstract {

    @Resource
    private MidjourneyTaskEventListener taskRunner;

    @Override
    protected MjStrategyTypeEnum getType() {
        return MjStrategyTypeEnum.ErrorMessage;
    }

    @Override
    public void handle(MessageType messageType, DataObject message) {
        Optional<DataArray> embedsOptional = message.optArray("embeds");
        if (!MessageType.CREATE.equals(messageType) || !embedsOptional.isPresent() || embedsOptional.get().isEmpty()) {
            return;
        }
        DataObject embed = embedsOptional.get().getObject(0);
        String title = embed.getString("title", null);
        String description = embed.getString("description", null);
        String footerText = "";
        Optional<DataObject> footer = embed.optObject("footer");
        if (footer.isPresent()) {
            footerText = footer.get().getString("text", "");
        }
        String channelId = message.getString("channel_id", "");
        int color = embed.getInt("color", 0);
        if (color == 16239475) {
            log.warn("{} - MJ警告信息: {}\n{}\nfooter: {}", channelId, title, description, footerText);
        } else if (color == 16711680) {
            log.error("{} - MJ异常信息: {}\n{}\nfooter: {}", channelId, title, description, footerText);
            String nonce = getMessageNonce(message);
            taskRunner.errorTask(nonce, description + "/n" + footerText);
        } else if (CharSequenceUtil.contains(title, "Invalid link")) {
            // 兼容 Invalid link! 错误
            log.error("{} - MJ异常信息: {}\n{}\nfooter: {}", channelId, title, description, footerText);
            taskRunner.errorTask(getMessageNonce(message), description + "/n" + footerText);
            DataObject messageReference = message.optObject("message_reference").orElse(DataObject.empty());
            String referenceMessageId = messageReference.getString("message_id", "");
            if (CharSequenceUtil.isBlank(referenceMessageId)) {
                return;
            }
        }
    }

}
