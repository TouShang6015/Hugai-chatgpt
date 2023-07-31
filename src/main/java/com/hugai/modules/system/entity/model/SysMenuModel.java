package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 菜单权限表 Model
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 * @tableName sys_menu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_menu")
public class SysMenuModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private int delFlag;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", dataType = "String")
    private String menuName;
    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID", dataType = "Long")
    private Long parentId;
    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", dataType = "Integer")
    private Integer sort;
    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址", dataType = "String")
    private String path;
    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径", dataType = "String")
    private String component;
    /**
     * 路由参数
     */
    @ApiModelProperty(value = "路由参数", dataType = "String")
    private String query;
    /**
     * 是否为外链（0是 1否）
     */
    @ApiModelProperty(value = "是否为外链（0否 1是）", dataType = "Integer")
    private String stateFrame;
    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @ApiModelProperty(value = "是否缓存（0缓存 1不缓存）", dataType = "String")
    private String stateCache;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）", dataType = "String")
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）", dataType = "String")
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    @ApiModelProperty(value = "菜单状态（0正常 1停用）", dataType = "String")
    private String status;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标", dataType = "String")
    private String icon;
    /**
     * 别名
     */
    @ApiModelProperty(value = "别名", dataType = "String")
    private String name;

}
