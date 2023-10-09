package com.hugai.common.enums.flow;

import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WuHao
 * @since 2023/10/4 17:01
 */
@Getter
@AllArgsConstructor
public enum AccountStatus implements FlowBaseEnum {

    NORMAL("NORMAL", "正常"),
    FREEZE("FREEZE", "冻结"),
    ;

    private final String key;

    private final String value;

}
