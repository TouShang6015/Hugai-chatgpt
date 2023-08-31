package com.hugai.common.enums.flow;

import com.hugai.common.enums.ModulesToken;
import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会话类型
 *
 * @author WuHao
 * @since 2023/6/5 10:07
 */
@Getter
@AllArgsConstructor
public enum SessionType implements FlowBaseEnum {

    CHAT("CHAT", "聊天/多轮对话") {
        @Override
        public ModulesToken modulesToken() {
            return ModulesToken.CHAT;
        }
    },

    DOMAIN("DOMAIN", "领域会话") {
        @Override
        public ModulesToken modulesToken() {
            return ModulesToken.DEFAULT;
        }
    },

    DRAW("DRAW", "AI绘图") {
        @Override
        public ModulesToken modulesToken() {
            return ModulesToken.DRAW;
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
