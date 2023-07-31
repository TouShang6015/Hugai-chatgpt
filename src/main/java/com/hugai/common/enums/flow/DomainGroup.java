package com.hugai.common.enums.flow;

import com.hugai.common.enums.ModulesToken;
import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 领域创作类型
 *
 * @author WuHao
 * @since 2023/6/19 10:08
 */
@Getter
@AllArgsConstructor
public enum DomainGroup implements FlowBaseEnum {

    COMMON("COMMON", "通用领域") {
        @Override
        public ModulesToken modulesToken() {
            return ModulesToken.DEFAULT;
        }
    },
    AI_WRITING("WRITING", "AI写作") {
        @Override
        public ModulesToken modulesToken() {
            return ModulesToken.DEFAULT;
        }
    },
    ;

    private final String key;

    private final String value;

    /**
     * 对应的modules的token配置
     *
     * @return
     */
    public abstract ModulesToken modulesToken();

}
