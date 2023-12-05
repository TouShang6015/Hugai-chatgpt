package com.hugai.core.mail.enums;

import com.org.bebas.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 验证码枚举
 */
@Getter
@AllArgsConstructor
public enum SmsTypeEnum {

    REGISTER("用户注册"),
    ;

    /**
     * 描述
     */
    private final String description;

    public String enumCacheKey() {
        return "SMS:" + this.name();
    }

    /**
     * 验证key
     *
     * @param key
     * @return
     */
    public static SmsTypeEnum verifyKey(String key) {
        return Arrays.stream(SmsTypeEnum.values()).filter(smsTypeEnums -> smsTypeEnums.name().equals(key)).findFirst().orElseThrow(() -> new BusinessException("短信类型不匹配！"));
    }
}