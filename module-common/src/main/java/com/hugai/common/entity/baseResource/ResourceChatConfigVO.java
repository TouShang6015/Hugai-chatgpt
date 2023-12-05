package com.hugai.common.entity.baseResource;

import lombok.Data;

import java.io.Serializable;

/**
 * 模型配置
 *
 * @author wuhao
 * @date 2022/9/23 9:21
 */
@Data
public class ResourceChatConfigVO implements Serializable {

    /**
     * 绘图优化默认使用配置
     */
    private Long drawPromptOptimizeChatModelId;
    /**
     * 绘图优化默认prompt
     */
    private String drawPromptOptimizeContent;

}
