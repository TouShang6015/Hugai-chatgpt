package com.hugai.framework.log.annotation;

import java.lang.annotation.*;

/**
 * 日志切面注解
 *
 * @author Wuhao
 * @date 2022/8/28 18:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String title() default "";

}
