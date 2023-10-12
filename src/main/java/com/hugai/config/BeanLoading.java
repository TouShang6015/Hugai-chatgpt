package com.hugai.config;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.core.midjourney.common.utils.DefaultApiParamJSON;
import com.hugai.core.midjourney.manager.MJSenWordHolder;
import com.hugai.core.security.filter.AccessDecisionManagerImpl;
import com.hugai.core.security.filter.PermissionSecurityFilter;
import com.hugai.core.security.filter.SecurityMetadataSource;
import com.hugai.framework.sensitiveWord.SenWordHolder;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.IOException;
import java.util.*;

/**
 * Bean加载配置类
 *
 * @author wuhao
 * @since 2022/9/6 11:29
 */
@Configuration
@EnableTransactionManagement        // 开启事务
public class BeanLoading {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public PermissionSecurityFilter dynamicSecurityFilter() {
        return new PermissionSecurityFilter();
    }

    @Bean
    public SecurityMetadataSource dynamicSecurityMetadataSource() {
        return new SecurityMetadataSource();
    }

    @Bean
    public AccessDecisionManagerImpl dynamicAccessDecisionManager() {
        return new AccessDecisionManagerImpl();
    }

    @Bean
    public SenWordHolder senWordHolder() {
        return new SenWordHolder();
    }
    @Bean
    public MJSenWordHolder mjSenWordHolder() {
        return new MJSenWordHolder();
    }

    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }

    /**
     * websocket支持
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 解决Swagger 3.0.0 不兼容问题
     **/
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    /**
     * redission无密码配置
     *
     * @return
     */
    @Bean
    public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer() {
        return configuration -> {
            if (StrUtil.isEmpty(configuration.useSingleServer().getPassword())) {
                configuration.useSingleServer().setPassword(null);
            }
        };
    }

    /**
     * mj api请求json
     *
     * @return
     */
    @Bean
    public DefaultApiParamJSON defaultApiParamJSON() {
        DefaultApiParamJSON defaultApiParamJSON = new DefaultApiParamJSON();
        try {
            Resource[] resources = this.applicationContext.getResources("classpath:json/mj/*.json");
            Map<String, String> paramsMap = new HashMap<>();
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                String params = IoUtil.readUtf8(resource.getInputStream());
                paramsMap.put(filename.substring(0, filename.length() - 5), params);
            }
            defaultApiParamJSON.setApiDefaultParamMap(paramsMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultApiParamJSON;
    }

}
