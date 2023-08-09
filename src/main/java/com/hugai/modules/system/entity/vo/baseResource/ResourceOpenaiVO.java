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
    /**
     * 绘图接口缓存超时时间（小时）
     */
    private Integer drawApiCacheTime;
    /**
     * 绘图接口缓存内最大请求次数
     */
    private Integer drawApiSendMax;

}
