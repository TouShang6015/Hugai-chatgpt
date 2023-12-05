package com.hugai.common.enums;

import cn.hutool.core.util.StrUtil;

import java.util.Locale;

/**
 * @author WuHao
 * @since 2023/11/29 11:30
 */
public enum SessionType {

    chat,

    domain,

    custom;

    public static SessionType get(String name) {
        if (StrUtil.isEmpty(name)) {
            return null;
        }
        for (SessionType value : values()) {
            if (value.name().toUpperCase(Locale.ROOT).equals(name.toUpperCase())) {
                return value;
            }
        }
        return null;
    }

}
