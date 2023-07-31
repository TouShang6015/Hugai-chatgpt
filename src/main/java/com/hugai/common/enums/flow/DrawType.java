package com.hugai.common.enums.flow;

import com.hugai.common.enums.ModulesToken;
import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 绘图类型
 *
 * @author WuHao
 * @since 2023/6/19 10:08
 */
@Getter
@AllArgsConstructor
public enum DrawType implements FlowBaseEnum {

    OPENAI("OPENAI", "openAi接口类型") {
        @Override
        public ModulesToken modulesToken() {
            return ModulesToken.DRAW;
        }
    };

    private final String key;

    private final String value;

    /**
     * 对应的modules的token配置
     *
     * @return
     */
    public abstract ModulesToken modulesToken();

}
