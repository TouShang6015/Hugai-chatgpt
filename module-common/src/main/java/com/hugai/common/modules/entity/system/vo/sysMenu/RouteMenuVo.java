package com.hugai.common.modules.entity.system.vo.sysMenu;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hugai.common.constants.Constants;
import com.org.bebas.utils.StringUtils;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @date 2022/6/4 14:56
 */
@Data
public class RouteMenuVo {

    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 路由参数：如 {"id": 1, "name": "ry"}
     */
    private String query;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 其他元素
     */
    private MetaVo meta;

    /**
     * 子菜单
     */
    private List<RouteMenuVo> children;


    /**
     * 构建菜单路由
     */
    public RouteMenuVo(SysMenuTreeVo menu) {
        this.hidden = !NumberUtils.INTEGER_ZERO.toString().equals(menu.getVisible());
        this.name = getRouteName(menu);
        this.path = getRouterPath(menu);
        this.component = getComponent(menu);
        this.query = menu.getQuery();
        this.meta = new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getStateCache()), menu.getPath());
        Optional.ofNullable(menu.getChildren()).ifPresent(menuChildren -> {
            this.children = menuChildren.stream().sorted(Comparator.comparing(SysMenuTreeVo::getSort)).map(RouteMenuVo::new).collect(Collectors.toList());
        });
        List<RouteMenuVo> children = this.getChildren();
        if (children != null && !children.isEmpty() && Constants.MENU_TYPE.M.equals(menu.getMenuType())) {
            this.alwaysShow = Boolean.TRUE;
            this.redirect = "noRedirect";
        } else if (isMenuFrame(menu)) {
            this.meta = null;
            this.path = menu.getPath();
            this.component = menu.getComponent();
            this.name = StringUtils.capitalize(menu.getPath());
            this.meta = new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getStateCache()), menu.getPath());
            this.query = menu.getQuery();
        } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
            this.meta = new MetaVo(menu.getMenuName(), menu.getIcon());
            this.path = StringPool.SLASH;
            String routerPath = innerLinkReplaceEach(menu.getPath());
            this.path = routerPath;
            this.component = Constants.MENU_INFO.INNER_LINK;
            this.name = StringUtils.capitalize(routerPath);
            this.meta = new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath());
        }
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenuTreeVo menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }


    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenuTreeVo menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && Constants.MENU_TYPE.M.equals(menu.getMenuType())
                && Constants.MENU_INFO.NO_FRAME.equals(menu.getStateFrame())) {
            routerPath = StringPool.SLASH + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = StringPool.SLASH;
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenuTreeVo menu) {
        String component = Constants.MENU_INFO.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = Constants.MENU_INFO.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = Constants.MENU_INFO.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenuTreeVo menu) {
        return menu.getParentId().intValue() == 0 && Constants.MENU_TYPE.C.equals(menu.getMenuType())
                && Constants.MENU_INFO.NO_FRAME.equals(menu.getStateFrame());
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenuTreeVo menu) {
        return Constants.MENU_INFO.NO_FRAME.equals(menu.getStateFrame()) && StringUtils.ishttp(menu.getPath());
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS},
                new String[]{"", ""});
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenuTreeVo menu) {
        return menu.getParentId().intValue() != 0 && Constants.MENU_TYPE.M.equals(menu.getMenuType());
    }

}
