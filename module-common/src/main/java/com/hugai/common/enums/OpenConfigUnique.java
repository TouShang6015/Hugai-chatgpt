package com.hugai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// todo [配置] uniqueKey 对应 sys_open_config表中字段

/**
 * @author WuHao
 * @since 2023/12/21 14:21
 */
@Getter
@AllArgsConstructor
public enum OpenConfigUnique {

    qiniu("qiniu"),


    ;

    private final String uniqueKey;

}
