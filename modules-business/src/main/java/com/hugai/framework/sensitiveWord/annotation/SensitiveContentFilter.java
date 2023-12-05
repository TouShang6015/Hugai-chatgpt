package com.hugai.framework.sensitiveWord.annotation;

import com.hugai.framework.sensitiveWord.constants.SenWordFilterType;

import java.lang.annotation.*;

/**
 * 敏感词过滤注解
 *
 * @author WuHao
 * @since 2023/7/28 14:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveContentFilter {

    /**
     * 入口过滤类型
     *
     * @return
     */
    SenWordFilterType type() default SenWordFilterType.error;

    /**
     * 出口过滤类型
     *
     * @return
     */
    SenWordFilterType resultType() default SenWordFilterType.replace;

    /**
     * 替换字符
     *
     * @return
     */
    String replaceVal() default "*";

    /**
     * 字段值
     *
     * @return
     */
    String attrName();

}
