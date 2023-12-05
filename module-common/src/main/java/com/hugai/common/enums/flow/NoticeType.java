package com.hugai.common.enums.flow;

import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知类型
 *
 * @author WuHao
 * @since 2023/6/5 10:07
 */
@Getter
@AllArgsConstructor
public enum NoticeType implements FlowBaseEnum {

    NOTIFY("NOTIFY", "通知"),

    HELP("HELP", "常见问题/帮助"),

    ;

    private final String key;

    private final String value;
}
