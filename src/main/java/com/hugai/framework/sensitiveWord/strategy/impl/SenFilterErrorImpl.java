package com.hugai.framework.sensitiveWord.strategy.impl;

import com.hugai.framework.sensitiveWord.constants.SenWordFilterType;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WuHao
 * @since 2023/7/28 15:43
 */
@Slf4j
public class SenFilterErrorImpl extends SenFilterCommonImpl {

    @Override
    public SenWordFilterType type() {
        return SenWordFilterType.error;
    }

    /**
     * 处理字符串类型值
     *
     * @param value
     * @return
     */
    @Override
    protected String handleString(String value) {
        if (super.senWordHolder.exists(value)) {
            throw new BusinessException(commonErrorText);
        }
        return value;
    }

}
