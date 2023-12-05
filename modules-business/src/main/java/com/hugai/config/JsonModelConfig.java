package com.hugai.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuhao
 * @date 2022/8/23 9:36
 */
@Configuration
public class JsonModelConfig extends SimpleModule {

    /**
     * 将响应数据的Long类型转换为String
     */
    public JsonModelConfig() {
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
    }

}
