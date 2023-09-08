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
     * sd api域名地址
     */
    private String sdHostUrl;

    /**
     * 默认反向prompt
     */
    private String defaultNegativePrompt;

}
