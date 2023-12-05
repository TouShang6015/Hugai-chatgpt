package com.hugai.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean("sqlFilter")
    public FilterRegistrationBean sqlFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new SqlFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0);
        return bean;
    }

    @Bean("xssFilter")
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new XssFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }

}
