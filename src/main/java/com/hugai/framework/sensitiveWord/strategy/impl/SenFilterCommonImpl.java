package com.hugai.framework.sensitiveWord.strategy.impl;

import com.hugai.framework.sensitiveWord.SenWordHolder;
import com.hugai.framework.sensitiveWord.strategy.SensitiveWordStrategy;
import com.org.bebas.core.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/7/28 15:55
 */
@Slf4j
public abstract class SenFilterCommonImpl implements SensitiveWordStrategy {

    protected SenWordHolder senWordHolder;

    protected String replaceValue;

    public SenFilterCommonImpl() {
        this.senWordHolder = SpringUtils.getBean(SenWordHolder.class);
    }

    /**
     * 默认的替换方法
     *
     * @param value
     * @return
     */
    @Override
    public String defaultReplaceValue(String value, String replaceValue) {
        if (Objects.isNull(value)) {
            return null;
        }
        this.replaceValue = replaceValue;
        Class<?> valueClass = value.getClass();
        if (
                Modifier.isInterface(valueClass.getModifiers()) ||
                        Modifier.isAbstract(valueClass.getModifiers())
        ) {
            log.warn("敏感词 参数已过滤: 参数：{},类型：Interface||Abstract", value);
            return value;
        }
        value = this.handleString((String) value);
        return value;
    }

    /**
     * 处理字符串类型值
     *
     * @param value
     * @return
     */
    protected abstract String handleString(String value);

    public String getReplaceValue() {
        return replaceValue;
    }
}
