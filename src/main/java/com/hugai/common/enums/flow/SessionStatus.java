package com.hugai.common.enums.flow;

import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会话状态
 *
 * @author WuHao
 * @since 2023/6/5 10:07
 */
@Getter
@AllArgsConstructor
public enum SessionStatus implements FlowBaseEnum {

    PROGRESS("PROGRESS", "进行中"),

    END("END", "结束"),

    ;

    private final String key;

    private final String value;
}
