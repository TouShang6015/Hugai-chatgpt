package com.hugai.core.security.filter;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.constants.SecurityConstant;
import com.hugai.config.security.GlobalRouteConfig;
import com.hugai.core.security.context.bean.LoginUserContextBean;
import com.org.bebas.core.function.OpenRunnable;
import com.org.bebas.exception.UserException;
import com.org.bebas.utils.ServletUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Objects;

/**
 * 路由决策管理
 *
 * @author Wuhao
 * @date 2022/9/5 20:47
 */
public class AccessDecisionManagerImpl implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws UserException {
        if (CollUtil.isEmpty(configAttributes)) {
            return;
        }
        FilterInvocation filterInvocation = (FilterInvocation) object;
        // 白名单过滤请求
        String requestURI = filterInvocation.getRequest().getRequestURI();
        OpenRunnable.run(GlobalRouteConfig.getPassRoutePath(), CollUtil::isNotEmpty, arg -> {
            for (String passRoutePath : GlobalRouteConfig.getPassRoutePath()) {
                if (passRoutePath.equals(requestURI)) return;
            }
        });
        for (String whiteRoute : GlobalRouteConfig.white_route) {
            if (whiteRoute.equals(requestURI)) return;
            // *:*:* 表示所有授权接口都有权限
            if (whiteRoute.equals(SecurityConstant.PERMISSION_TAG)) return;
        }
        if (authentication.getPrincipal() instanceof String) {
            if (authentication.getPrincipal().equals("anonymousUser")) {
                String msg = "系统认证失败，请先登录后在操作";
                throw new AccessDeniedException(msg);
            }
        }
        LoginUserContextBean loginUser = (LoginUserContextBean) authentication.getPrincipal();
        if (Objects.isNull(loginUser)) {
            ServletUtils.redirectErrorRequest(filterInvocation.getRequest(), filterInvocation.getResponse(), "无法获取用户信息");
            throw new AccessDeniedException("无法获取用户信息");
        }
        for (ConfigAttribute configAttribute : configAttributes) {
            //将访问所需资源或用户拥有资源进行比对
            String needAuthority = configAttribute.getAttribute();
            for (String grantedAuthority : loginUser.getPermissions()) {
                // *:*:* 表示所有授权接口都有权限
                if (grantedAuthority.equals(SecurityConstant.PERMISSION_TAG)) {
                    return;
                }
                if (needAuthority.trim().equals(grantedAuthority)) {
                    return;
                }
            }
        }
        ServletUtils.redirectErrorRequest(filterInvocation.getRequest(), filterInvocation.getResponse(), "无权限访问");
        throw new AccessDeniedException("无权限访问");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
