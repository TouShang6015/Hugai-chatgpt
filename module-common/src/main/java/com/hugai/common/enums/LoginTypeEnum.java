package com.hugai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登陆类型
 *
 * @author WuHao
 * @since 2023/6/13 16:40
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    // 管理端登陆
    ADMIN("1"),
    // 用户登陆
    CLIENT("2"),
    // 游客登陆
    TOURIST("3");

    private final String type;

}
