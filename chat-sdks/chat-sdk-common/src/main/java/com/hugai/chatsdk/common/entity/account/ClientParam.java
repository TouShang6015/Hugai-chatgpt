package com.hugai.chatsdk.common.entity.account;

import lombok.Data;

/**
 * 连接配置
 *
 * @author WuHao
 * @since 2023/11/29 14:34
 */
@Data
public class ClientParam {

    /**
     * 源地址
     */
    private String baseUrl;

    /**
     * 是否启用代理
     */
    private Boolean ifProxy;

    /**
     * 代理ip
     */
    private String proxyHost;
    /**
     * 代理端口
     */
    private Integer proxyPort;

    /**
     * 请求最大超时时间（秒）
     */
    private Integer timeoutValue;
    /**
     * 最大连接数
     */
    private Integer maxConnect;

}
