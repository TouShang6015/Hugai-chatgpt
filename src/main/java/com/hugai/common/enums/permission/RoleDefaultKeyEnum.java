package com.hugai.common.enums.permission;

import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WuHao
 * @since 2023/6/30 9:25
 */
@Getter
@AllArgsConstructor
public enum RoleDefaultKeyEnum implements FlowBaseEnum {

    system("system","系统管理员"),

    admin("admin","系统人员"),

    tourist("tourist","游客"),
    ;

    private final String key;

    private final String value;
}
