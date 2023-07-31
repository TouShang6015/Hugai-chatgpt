package com.hugai.core.security.service;

import cn.hutool.core.collection.CollUtil;
import com.hugai.config.security.GlobalRouteConfig;
import com.hugai.modules.system.entity.convert.SysPermissionConvert;
import com.hugai.modules.system.entity.model.SysPermissionModel;
import com.hugai.modules.system.entity.vo.permission.RouteInfo;
import com.hugai.modules.system.service.ISysPermissionService;
import com.org.bebas.core.function.OR;
import com.org.bebas.utils.OptionalUtil;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * springSecurity 业务类
 */
@Component
public class SecurityService {

    /**
     * 静态资源放行路由
     */
    private static final String[] permitStaticPath = {"/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js"};

    @Resource
    private ISysPermissionService sysPermissionService;

    /**
     * 刷新security配置
     *
     * @param httpSecurity
     */
    public void flushHttpSecurityConfig(HttpSecurity httpSecurity) throws Exception {
        this.syncConfigAttributeMap();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        // 匿名访问路由
        OR.run(GlobalRouteConfig.getAnonymousRoutePath(), CollUtil::isNotEmpty, arg -> {
            registry.antMatchers(arg.toArray(new String[0])).anonymous();
        });
        // 完全放行路由
        registry.antMatchers(HttpMethod.GET, permitStaticPath).permitAll().antMatchers(GlobalRouteConfig.white_route).permitAll();
        // 放行路由
        OR.run(GlobalRouteConfig.getAnonymousRoutePath(), CollUtil::isNotEmpty, arg -> {
            registry.antMatchers(GlobalRouteConfig.getPassRoutePath().toArray(new String[0])).permitAll();
        });
    }

    /**
     * 同步加载资源ANT通配符和资源对应MAP
     */
    public void syncConfigAttributeMap() {
        GlobalRouteConfig.initConfigAttributeMap();
        this.syncRouteList();
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<RouteInfo> routeList = GlobalRouteConfig.getAuthRoute();
        OptionalUtil.ofNullList(routeList).forEach(model -> {
            map.put(model.getRoutePath(), new SecurityConfig(model.getPermissionTag()));
        });
        GlobalRouteConfig.setConfigAttributeMap(map);
    }

    /**
     * 同步路由权限列表，存放缓存中
     */
    public void syncRouteList() {
        GlobalRouteConfig.initRouteList();
        Function<List<SysPermissionModel>, List<RouteInfo>> convert = SysPermissionConvert.INSTANCE::convertToRouteInfo;
        List<SysPermissionModel> routeList = sysPermissionService.getRouteList();
        Map<Integer, List<SysPermissionModel>> routeVisitRuleGroupMap = OptionalUtil.ofNullList(routeList).stream().collect(Collectors.groupingBy(SysPermissionModel::getRouteVisitRule));
        routeVisitRuleGroupMap.forEach((key, value) -> {
            if (Objects.nonNull(key)) {
                switch (key) {
                    case 1:
                        GlobalRouteConfig.setAuthRoute(convert.apply(value));
                        GlobalRouteConfig.setAuthRoutePath(OptionalUtil.ofNullList(GlobalRouteConfig.getAuthRoute()).stream().map(RouteInfo::getRoutePath).collect(Collectors.toList()));
                        break;
                    case 2:
                        GlobalRouteConfig.setAnonymousRoute(convert.apply(value));
                        GlobalRouteConfig.setAnonymousRoutePath(OptionalUtil.ofNullList(GlobalRouteConfig.getAnonymousRoute()).stream().map(RouteInfo::getRoutePath).collect(Collectors.toList()));
                        break;
                    case 3:
                        GlobalRouteConfig.setPassRoute(convert.apply(value));
                        GlobalRouteConfig.setPassRoutePath(OptionalUtil.ofNullList(GlobalRouteConfig.getPassRoute()).stream().map(RouteInfo::getRoutePath).collect(Collectors.toList()));
                        break;
                    case 4:
                        GlobalRouteConfig.setIgnoreRoute(convert.apply(value));
                        GlobalRouteConfig.setIgnoreRoutePath(OptionalUtil.ofNullList(GlobalRouteConfig.getIgnoreRoute()).stream().map(RouteInfo::getRoutePath).collect(Collectors.toList()));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}