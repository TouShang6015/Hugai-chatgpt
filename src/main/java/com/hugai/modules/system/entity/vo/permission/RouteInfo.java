package com.hugai.modules.system.entity.vo.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuhao
 * @date 2022/10/3 15:23
 */
@Data
public class RouteInfo {

    private Long id;
    /**
     * 父id
     */
    @ApiModelProperty(value = "父id", dataType = "Long")
    private Long parentId;
    /**
     * 接口路由
     */
    @ApiModelProperty(value = "接口路由", dataType = "String")
    private String routePath;
    /**
     * 原始路由
     */
    @ApiModelProperty(value = "原始路由", dataType = "String")
    private String originalRoutePath;
    /**
     * 接口标识
     */
    @ApiModelProperty(value = "接口标识", dataType = "String")
    private String permissionTag;
    /**
     * 请求方式 GET POST...
     */
    @ApiModelProperty(value = "请求方式 GET POST...", dataType = "String")
    private String requestMethod;
    /**
     * 接口访问规则
     * 1 授权访问
     * 2 匿名访问（不携带token）
     * 3 完全放行
     * 4 不可访问
     */
    @ApiModelProperty(value = "接口访问规则", dataType = "Integer")
    private Integer routeVisitRule;

}
