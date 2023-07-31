package com.hugai.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * rsaConfig
 *
 * @author WuHao
 * @date 2022/5/31 18:58
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "project.security.rsa")
public class RsaConfig {

    private static String privateKey;

    private static String publicKey;

    public static String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        RsaConfig.privateKey = privateKey;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        RsaConfig.publicKey = publicKey;
    }
}
