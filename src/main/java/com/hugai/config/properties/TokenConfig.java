package com.hugai.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author WuHao
 * @date 2022/5/13 8:25
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "project.token")
public class TokenConfig {
    /**
     * 令牌自定义标识
     */
    private static String header;
    /**
     * 令牌密钥
     */
    private static String secret;
    /**
     * 过期时间
     */
    private static long expireTime;
    /**
     * token刷新时间
     */
    private static long flushToken;

    public static String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        TokenConfig.header = header;
    }

    public static String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        TokenConfig.secret = secret;
    }

    public static long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        TokenConfig.expireTime = expireTime;
    }

    public static long getFlushToken() {
        return flushToken;
    }

    public void setFlushToken(long flushToken) {
        TokenConfig.flushToken = flushToken;
    }
}
