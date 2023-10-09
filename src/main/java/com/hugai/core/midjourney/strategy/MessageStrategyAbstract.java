package com.hugai.core.midjourney.strategy;

import cn.hutool.core.text.CharSequenceUtil;
import com.hugai.core.midjourney.manager.TaskQueueManager;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.core.midjourney.common.entity.MessageResponseData;
import com.hugai.core.midjourney.common.entity.TaskObj;
import com.hugai.core.midjourney.common.enums.MessageType;
import com.hugai.core.midjourney.common.enums.MjStrategyTypeEnum;
import com.org.bebas.core.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.util.Objects;

/**
 * socket消息处理类型策略抽象
 *
 * @author WuHao
 * @since 2023/9/26 9:19
 */
@Slf4j
public abstract class MessageStrategyAbstract {

    private final MidjourneyTaskEventListener taskRunner;

    public MessageStrategyAbstract() {
        this.taskRunner = SpringUtils.getBean(MidjourneyTaskEventListener.class);
    }

    public abstract void handle(MessageType messageType, DataObject message);

    protected String getMessageContent(DataObject message) {
        return message.hasKey("content") ? message.getString("content") : "";
    }

    protected String getMessageNonce(DataObject message) {
        return message.hasKey("nonce") ? message.getString("nonce") : "";
    }

    protected void findAndFinishImageTask(String finalPrompt, DataObject message) {
        String imageUrl = getImageUrl(message);
        String guildId = message.getString("guild_id");
        String channelId = message.getString("channel_id");
        String messageId = message.getString("id");
        log.debug("Finish Task - [{}] - imgurl: {} | finalPrompt:{}", this.getType().name(), imageUrl, finalPrompt);

        TaskObj taskQueueBean = TaskQueueManager.get(finalPrompt, guildId, channelId, this.getType().name());
        if (Objects.nonNull(taskQueueBean)) {
            taskQueueBean.setStrategyType(this.getType().name());
            taskQueueBean.setPrompt(finalPrompt);
            MessageResponseData responseData = taskQueueBean.getResponseData();
            responseData.setImgUrl(imageUrl);
            responseData.setMessageId(messageId);
            responseData.setMessageHash(getMessageHash(imageUrl));
            taskQueueBean.setResponseData(responseData);
        }
        taskRunner.taskRun(taskQueueBean);
    }

    protected boolean hasImage(DataObject message) {
        DataArray attachments = message.optArray("attachments").orElse(DataArray.empty());
        return !attachments.isEmpty();
    }

    protected String getImageUrl(DataObject message) {
        DataArray attachments = message.getArray("attachments");
        if (!attachments.isEmpty()) {
            return attachments.getObject(0).getString("url");
        }
        return null;
    }

    public String getMessageHash(String imageUrl) {
        if (CharSequenceUtil.isBlank(imageUrl)) {
            return null;
        }
        if (CharSequenceUtil.endWith(imageUrl, "_grid_0.webp")) {
            int hashStartIndex = imageUrl.lastIndexOf("/");
            if (hashStartIndex < 0) {
                return null;
            }
            return CharSequenceUtil.sub(imageUrl, hashStartIndex + 1, imageUrl.length() - "_grid_0.webp".length());
        }
        int hashStartIndex = imageUrl.lastIndexOf("_");
        if (hashStartIndex < 0) {
            return null;
        }
        return CharSequenceUtil.subBefore(imageUrl.substring(hashStartIndex + 1), ".", true);
    }

    protected abstract MjStrategyTypeEnum getType();

}
