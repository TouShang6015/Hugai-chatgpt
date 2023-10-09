package com.hugai.core.midjourney.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * mj 任务队列对象
 *
 * @author WuHao
 * @since 2023/9/27 11:47
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskObj implements Serializable {

    private String nonce;

    private String prompt;

    private String guildId;

    private String channelId;

    private int index;

    /**
     * 绘图接口类型
     * {@link com.hugai.common.enums.flow.DrawType.ApiKey}
     */
    private String drawApiKey;

    /**
     * ws 接受响应目标
     */
    private String targetHandler;

    /**
     * ws 响应类型
     */
    private String strategyType;

    private MessageResponseData responseData;

    public MessageResponseData getResponseData() {
        if (this.responseData == null) {
            this.responseData = new MessageResponseData();
        }
        return this.responseData;
    }
}
