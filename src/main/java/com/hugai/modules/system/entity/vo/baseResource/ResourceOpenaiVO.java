package com.hugai.modules.system.entity.vo.baseResource;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wuhao
 * @date 2022/9/23 9:21
 */
@Data
public class ResourceOpenaiVO implements Serializable {
    /**
     * chatModel
     */
    private String chatModel;
    /**
     * textModel
     */
    private String textModel;
    /**
     * 代理地址
     */
    private String proxyHost;
    /**
     * 代理端口
     */
    private Integer proxyPort;

}
