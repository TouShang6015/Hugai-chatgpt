package com.hugai.common.enums.flow;

import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务状态
 *
 * @author WuHao
 * @since 2023/9/7 16:04
 */
@Getter
@AllArgsConstructor
public enum TaskStatus implements FlowBaseEnum {

    WAIT("WAIT", "待执行"),
    RUNNING("RUNNING", "进行中"),
    SUCCESS("SUCCESS", "已完成"),
    FAIL("FAIL", "失败"),
    ;

    private final String key;

    private final String value;

}
