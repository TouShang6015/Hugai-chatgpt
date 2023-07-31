package com.hugai.config.security;

import com.hugai.modules.system.entity.vo.permission.RouteInfo;
import org.springframework.security.access.ConfigAttribute;

import java.util.List;
import java.util.Map;

/**
 * 全局路由
 *
 * @author wuhao
 * @date 2022/10/3 15:21
 */
public class GlobalRouteConfig {

    /**
     * 固定白名单路由
     */
    public static final String[] white_route = {"/open/**"};

    /**
     * 需要授权的
     */
    private static List<RouteInfo> authRoute;
    private static List<String> authRoutePath;

    /**
     * 匿名
     */
    private static List<RouteInfo> anonymousRoute;
    private static List<String> anonymousRoutePath;

    /**
     * 完全放行
     */
    private static List<RouteInfo> passRoute;
    private static List<String> passRoutePath;

    /**
     * 不可访问
     */
    private static List<RouteInfo> ignoreRoute;
    private static List<String> ignoreRoutePath;

    private static Map<String, ConfigAttribute> configAttributeMap;

    public static List<RouteInfo> getAuthRoute() {
        return authRoute;
    }

    public static void setAuthRoute(List<RouteInfo> authRoute) {
        GlobalRouteConfig.authRoute = authRoute;
    }

    public static List<String> getAuthRoutePath() {
        return authRoutePath;
    }

    public static void setAuthRoutePath(List<String> authRoutePath) {
        GlobalRouteConfig.authRoutePath = authRoutePath;
    }

    public static List<RouteInfo> getAnonymousRoute() {
        return anonymousRoute;
    }

    public static void setAnonymousRoute(List<RouteInfo> anonymousRoute) {
        GlobalRouteConfig.anonymousRoute = anonymousRoute;
    }

    public static List<String> getAnonymousRoutePath() {
        return anonymousRoutePath;
    }

    public static void setAnonymousRoutePath(List<String> anonymousRoutePath) {
        GlobalRouteConfig.anonymousRoutePath = anonymousRoutePath;
    }

    public static List<RouteInfo> getPassRoute() {
        return passRoute;
    }

    public static void setPassRoute(List<RouteInfo> passRoute) {
        GlobalRouteConfig.passRoute = passRoute;
    }

    public static List<String> getPassRoutePath() {
        return passRoutePath;
    }

    public static void setPassRoutePath(List<String> passRoutePath) {
        GlobalRouteConfig.passRoutePath = passRoutePath;
    }

    public static List<RouteInfo> getIgnoreRoute() {
        return ignoreRoute;
    }

    public static void setIgnoreRoute(List<RouteInfo> ignoreRoute) {
        GlobalRouteConfig.ignoreRoute = ignoreRoute;
    }

    public static List<String> getIgnoreRoutePath() {
        return ignoreRoutePath;
    }

    public static void setIgnoreRoutePath(List<String> ignoreRoutePath) {
        GlobalRouteConfig.ignoreRoutePath = ignoreRoutePath;
    }

    public static Map<String, ConfigAttribute> getConfigAttributeMap() {
        return configAttributeMap;
    }

    public static void setConfigAttributeMap(Map<String, ConfigAttribute> configAttributeMap) {
        GlobalRouteConfig.configAttributeMap = configAttributeMap;
    }

    /**
     * 初始化路由列表
     */
    public static void initRouteList() {
        GlobalRouteConfig.setAuthRoute(null);
        GlobalRouteConfig.setAuthRoutePath(null);
        GlobalRouteConfig.setAnonymousRoute(null);
        GlobalRouteConfig.setAnonymousRoutePath(null);
        GlobalRouteConfig.setPassRoute(null);
        GlobalRouteConfig.setPassRoutePath(null);
        GlobalRouteConfig.setIgnoreRoute(null);
        GlobalRouteConfig.setIgnoreRoutePath(null);
    }

    /**
     * 初始化安全配置Map
     */
    public static void initConfigAttributeMap() {
        GlobalRouteConfig.setConfigAttributeMap(null);
    }

}
