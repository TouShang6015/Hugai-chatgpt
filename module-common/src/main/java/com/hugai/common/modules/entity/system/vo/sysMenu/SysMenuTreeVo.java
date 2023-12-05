package com.hugai.common.modules.entity.system.vo.sysMenu;

import com.org.bebas.utils.tree.entity.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WuHao
 * @description:
 * @date 2023/4/1$ 13:52$
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuTreeVo extends TreeEntity<SysMenuTreeVo> {

    // dto
    private Long userId;

    // -----------------------------
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 路由参数
     */
    private String query;
    /**
     * 是否为外链（0是 1否）
     */
    private String stateFrame;
    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private String stateCache;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 别名
     */
    private String name;

}
