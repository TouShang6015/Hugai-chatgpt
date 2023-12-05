package com.hugai.common.websocket.entity;

import com.hugai.common.websocket.constants.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 用户socket连接响应结果集
 *
 * @author WuHao
 * @since 2023/10/16 10:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocketUserResult {

    private Integer code;

    private boolean status;

    private String message;

    private Object data;

    public static SocketUserResult i(Object data) {
        return SocketUserResult.builder()
                .code(ResultCode.S_INIT.getCode())
                .message("")
                .status(true)
                .data(data)
                .build();
    }

    public static SocketUserResult r(Object data) {
        return r(ResultCode.S_COMMON, data);
    }

    public static SocketUserResult r(ResultCode resultCode, Object data) {
        return SocketUserResult.builder()
                .code(resultCode.getCode())
                .message("")
                .status(true)
                .data(data)
                .build();
    }

    public static SocketUserResult err(String message, Object data) {
        return SocketUserResult.builder()
                .code(ResultCode.ERROR.getCode())
                .message(message)
                .status(true)
                .data(data)
                .build();
    }

    public static SocketUserResult err(String message) {
        return err(message, null);
    }

    public static boolean verify(SocketUserResult obj) {
        if (Objects.isNull(obj)) {
            return false;
        } else if (Objects.isNull(obj.getCode())) {
            return false;
        }
        return true;
    }

}
