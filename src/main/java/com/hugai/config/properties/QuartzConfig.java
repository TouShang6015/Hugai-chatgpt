package com.hugai.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Wuhao
 * @date 2022/9/24 12:10
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "project.quartz")
public class QuartzConfig {

    private static String jobWhite;

    public static String getJobWhite() {
        return jobWhite;
    }

    public void setJobWhite(String jobWhite) {
        QuartzConfig.jobWhite = jobWhite;
    }
}
