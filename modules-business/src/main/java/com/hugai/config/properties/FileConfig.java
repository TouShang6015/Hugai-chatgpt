package com.hugai.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 文件上传参数实体
 *
 * @author wuhao
 * @date 2022/5/7 21:13
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "project.file")
public class FileConfig {

    private static Integer maxUploadSize;

    private static Integer maxFileNameLength;

    public static Integer getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(Integer maxUploadSize) {
        FileConfig.maxUploadSize = maxUploadSize;
    }

    public static Integer getMaxFileNameLength() {
        return maxFileNameLength;
    }

    public void setMaxFileNameLength(Integer maxFileNameLength) {
        FileConfig.maxFileNameLength = maxFileNameLength;
    }

}
