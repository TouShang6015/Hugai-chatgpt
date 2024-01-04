package com.hugai.common.support.sms.enums;

import cn.hutool.core.lang.Assert;
import com.org.bebas.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

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

    public static SmsTypeEnum getByName(String name) {
        Assert.notEmpty(name, () -> new BusinessException("无法匹配的短信发送类型"));
        for (SmsTypeEnum value : values()) {
            if (value.name().toUpperCase(Locale.ROOT).equals(name.toUpperCase(Locale.ROOT))) {
                return value;
            }
        }
        throw new BusinessException("无法匹配的短信发送类型");
    }

}