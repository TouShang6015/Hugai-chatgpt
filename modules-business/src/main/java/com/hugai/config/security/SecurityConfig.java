package com.hugai.config.security;

import com.hugai.core.security.filter.JwtAuthenticationTokenFilter;
import com.hugai.core.security.filter.PermissionSecurityFilter;
import com.hugai.core.security.handle.AuthenticationEntryPointImpl;
import com.hugai.core.security.handle.LogoutSuccessHandlerImpl;
import com.hugai.core.security.manager.base.CustomAuthenticationProvider;
import com.hugai.core.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.List;

/**
 * @author wuhao
 * @date 2022/9/5 17:45
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private List<CustomAuthenticationProvider> customAuthenticationProviderList;

    /**
     * 鉴权过滤器
     */
    @Autowired
    private PermissionSecurityFilter permissionSecurityFilter;
    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;
    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 设置多认证Provider
     *
     * @param builder
     */
    @Autowired
    public void setSecurityProvider(AuthenticationManagerBuilder builder) {
        customAuthenticationProviderList.forEach(builder::authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        securityService.flushHttpSecurityConfig(httpSecurity);
        httpSecurity
                .csrf().disable()   // CSRF禁用
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()    // 认证失败处理类
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()   // 基于token，不需要session
                .authorizeRequests()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.logout().logoutUrl("/auth/system/logout").logoutSuccessHandler(logoutSuccessHandler);
        // JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // CORS filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(authenticationTokenFilter, LogoutFilter.class);
        // 动态权限
        httpSecurity.addFilterBefore(permissionSecurityFilter, FilterSecurityInterceptor.class);
        return httpSecurity.build();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
