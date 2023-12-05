package com.hugai.core.dataPool;

import cn.hutool.core.util.StrUtil;

import java.util.Locale;

/**
 * @author WuHao
 * @since 2023/11/29 16:01
 */
public enum DataRuleEnum {

    random;

    public static DataRuleEnum getByName(String value) {
        if (StrUtil.isEmpty(value))
            return DataRuleEnum.random;
        for (DataRuleEnum dataRuleEnum : values()) {
            if (dataRuleEnum.name().toUpperCase(Locale.ROOT).equals(value.toUpperCase(Locale.ROOT))) {
                return dataRuleEnum;
            }
        }
        return DataRuleEnum.random;
    }

}
