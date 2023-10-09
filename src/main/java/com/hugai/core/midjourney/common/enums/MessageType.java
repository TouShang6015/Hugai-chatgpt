package com.hugai.core.midjourney.common.enums;

import lombok.AllArgsConstructor;

/**
 * @author WuHao
 * @since https://github.com/novicezk/midjourney-proxy
 */
@AllArgsConstructor
public enum MessageType {
    /**
     * 创建.
     */
    CREATE("MESSAGE_CREATE"),
    /**
     * 修改.
     */
    UPDATE("MESSAGE_UPDATE"),
    /**
     * 删除.
     */
    DELETE("MESSAGE_DELETE");

    private final String key;

    public static MessageType of(String type) {
        for (MessageType value : MessageType.values()) {
            if (value.key.equals(type)) {
                return value;
            }
        }
        return null;
    }
}