package com.hugai.common.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuhao
 * @date 2022/9/28 15:24
 */
@Getter
@AllArgsConstructor
public enum VisitRuleEnum {

    /**
     * 接口访问规则
     * 1 授权访问
     * 2 匿名访问（不携带token）
     * 3 完全放行
     * 4 不可访问
     */

    AUTH(1),
    ANONYMITY(2),
    ALL_PERMIT(3),
    NO_ACCESS(4),
    ;

    private final Integer value;

}
