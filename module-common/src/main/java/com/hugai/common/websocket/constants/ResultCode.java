package com.hugai.common.websocket.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WuHao
 * @since 2023/10/16 10:51
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    ERROR(-1, "服务器消息处理失败"),


    S_INIT(0, "服务器发送首条信息"),
    S_COMMON(1, "服务器发送信息"),
    S_MESSAGE_SUCCESS(2, "推送成功信息"),
    S_MESSAGE_WARNING(3, "推送警告信息"),
    S_MESSAGE_ERROR(4, "推送错误信息"),
    S_ONLINE_COUNT(11, "获取在线人数"),


    C_COMMON(100, "接收客户端消息"),
    C_FLUSH_CONNECT(101, "更新连接时间"),

    ;

    private final Integer code;

    private final String message;

}
