package com.hugai.core.security.filter;

import com.hugai.config.security.GlobalRouteConfig;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 鉴权过滤器
 *
 * @author Wuhao
 * @date 2022/9/5 20:29
 */
public class PermissionSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private SecurityMetadataSource dynamicSecurityMetadataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Autowired
    public void setMyAccessDecisionManager(AccessDecisionManagerImpl dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        //OPTIONS请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单请求放行
        List<String> passRoutePath = OptionalUtil.ofNullList(GlobalRouteConfig.getPassRoutePath());
        for (String path : passRoutePath) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return;
            }
        }
        // 拒绝访问请求
        List<String> ignoreRoutePath = OptionalUtil.ofNullList(GlobalRouteConfig.getIgnoreRoutePath());
        for (String path : ignoreRoutePath) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                ServletUtils.redirectErrorRequest(fi.getRequest(), fi.getResponse(), "请求拒绝访问");
                return;
            }
        }
        //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public org.springframework.security.access.SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}
