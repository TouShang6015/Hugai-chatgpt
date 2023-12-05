package com.hugai.framework.asyncMessage.annotation;

import com.hugai.common.enums.ChannelEnum;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 消息监听注解
 * <br/> * 被修饰的方法 类中需要添加 {@link org.springframework.stereotype.Service} 注解才可生效
 *
 * @author wuhao
 * @date 2022/8/26 16:38
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface MessageListener {

    /**
     * 频道值 {@link ChannelEnum}
     *
     * @return
     */
    ChannelEnum value();

}
