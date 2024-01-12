package com.hugai.chatsdk.common.manager.accessToken.entity;

import lombok.Data;

/**
 * @author WuHao
 * @since 2024/1/12 9:28
 */
@Data
public class AccessTokenBean {

    /**
     * 唯一键
     */
    private String uniqueKey;
    /**
     * accessToken
     */
    private String accessToken;
    /**
     * 过期时间
     */
    private long expiresTime;
    /**
     * 刷新token时间
     */
    private long refreshTime;
    /**
     * refresh Token
     */
    private String refreshToken;

}
