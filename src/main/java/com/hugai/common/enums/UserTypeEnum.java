package com.hugai.common.enums;

import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WuHao
 * @since 2023/6/5 11:36
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum implements FlowBaseEnum {

    SYS("0", "系统用户"),

    USER("1", "普通用户"),

    ;

    private final String key;

    private final String value;

}
