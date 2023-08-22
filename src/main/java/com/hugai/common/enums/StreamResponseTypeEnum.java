package com.hugai.common.enums;

import cn.hutool.core.lang.Assert;

import java.util.Locale;

/**
 * 流式响应风格枚举
 *
 * @author WuHao
 * @since 2023/8/22 17:35
 */
public enum StreamResponseTypeEnum {

    Websocket,

    SSE;

    /**
     * 根据value获取默认的流式响应模式类型
     *
     * @param value
     * @return
     */
    public static StreamResponseTypeEnum getDefaultType(String value) {
        Assert.notNull(value);
        for (StreamResponseTypeEnum streamResponseTypeEnum : StreamResponseTypeEnum.values()) {
            String name = streamResponseTypeEnum.name().toLowerCase(Locale.ROOT);
            if (name.equals(value.toLowerCase(Locale.ROOT))) {
                return streamResponseTypeEnum;
            }
        }
        return SSE;
    }

}
