package com.hugai.modules.system.entity.vo.baseResource;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WuHao
 * @since 2023/9/6 13:39
 */
@Data
public class ResourceSdVO implements Serializable {

    /**
     * sd api域名地址
     */
    private String hostUrl;

    /**
     * 默认反向prompt
     */
    private String defaultNegativePrompt;

}
