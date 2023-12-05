package com.hugai.framework.sensitiveWord.strategy.impl;

import com.hugai.framework.sensitiveWord.constants.SenWordFilterType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WuHao
 * @since 2023/7/28 15:43
 */
@Slf4j
public class SenFilterNonImpl extends SenFilterCommonImpl {

    @Override
    public SenWordFilterType type() {
        return SenWordFilterType.non;
    }

    /**
     * 处理字符串类型值
     *
     * @param value
     * @return
     */
    @Override
    protected String handleString(String value) {
        return value;
    }

}
