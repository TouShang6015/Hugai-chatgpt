package com.hugai.modules.system.entity.vo.permission;

import com.org.bebas.utils.tree.entity.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WuHao
 * @description:
 * @date 2023/4/1$ 14:06$
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPermissionTreeVo extends TreeEntity<SysPermissionTreeVo> {


    /**
     * 父id
     */
    private Long parentId;
    /**
     * 祖级列表
     */
    private String ancestors;
    /**
     * 是否为路由
     */
    private Integer ifRoute;
    /**
     * 标题
     */
    private String title;
    /**
     * 接口路由
     */
    private String routePath;
    /**
     * 原始路由
     */
    private String originalRoutePath;
    /**
     * 接口标识
     */
    private String permissionTag;
    /**
     * 请求方式 GET POST...
     */
    private String requestMethod;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 模块控制器对象
     */
    private String moduleController;
    /**
     * 接口访问规则
     * 1 授权访问
     * 2 匿名访问（不携带token）
     * 3 完全放行
     * 4 不可访问
     */
    private Integer routeVisitRule;

}
