package com.hugai.modules.system.entity.vo.baseResource;

import lombok.Data;

import java.io.Serializable;

/**
 * 绘图配置
 *
 * @author WuHao
 * @since 2023/9/6 13:39
 */
@Data
public class ResourceDrawVO implements Serializable {

    /**
     * 是否开启openai绘图接口
     */
    private Boolean openDrawOpenai;
    /**
     * 是否开启正向prompt前置内容
     */
    private Boolean openBeforePromptContent;
    /**
     * 正向prompt固定前置内容
     */
    private String beforePromptContent;
    /**
     * 是否开启反向prompt前置内容
     */
    private Boolean openBeforeNegativePromptContent;
    /**
     * 反向pprompt固定前置内容
     */
    private String beforeNegativePromptContent;

    /**
     * sd api域名地址
     */
    private String sdHostUrl;

    /**
     * sd默认请求参数
     */
    private String defaultRequestBean;

    /**
     * 默认反向prompt
     */
    private String defaultNegativePrompt;

    /**
     * 默认正向prompt
     */
    private String defaultPrompt;

}
