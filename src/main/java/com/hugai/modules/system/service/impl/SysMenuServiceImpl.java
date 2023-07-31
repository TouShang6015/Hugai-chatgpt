package com.hugai.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.constants.Constants;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.system.entity.convert.SysMenuConvert;
import com.hugai.modules.system.entity.dto.SysMenuDTO;
import com.hugai.modules.system.entity.model.SysMenuModel;
import com.hugai.modules.system.entity.model.SysRoleMenuModel;
import com.hugai.modules.system.entity.model.SysRoleModel;
import com.hugai.modules.system.entity.vo.sysMenu.RouteMenuVo;
import com.hugai.modules.system.entity.vo.sysMenu.SysMenuTreeVo;
import com.hugai.modules.system.mapper.SysMenuMapper;
import com.hugai.modules.system.service.ISysMenuService;
import com.hugai.modules.system.service.ISysRoleMenuService;
import com.hugai.modules.system.service.ISysRoleService;
import com.hugai.modules.system.service.ISysUserRoleService;
import com.org.bebas.core.model.build.QueryFastLambda;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单权限表 业务实现类
 *
 * @author WuHao
 * @date 2022-05-25 22:01:16
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuModel> implements ISysMenuService {

    @Resource
    private ISysRoleMenuService sysRoleMenuService;
    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private ISysRoleService sysRoleService;

    @Override
    public List<SysMenuModel> listByParam(SysMenuModel param) {
        if (!SecurityContextUtil.isAdmin(SecurityContextUtil.getUserId())) {
            Set<Long> roleIds = CollUtil.isEmpty(SecurityContextUtil.getRoleIds()) ? CollUtil.newHashSet(-1L) : SecurityContextUtil.getRoleIds();
            List<Long> menuIds = sysRoleMenuService.lambdaQuery()
                    .in(SysRoleMenuModel::getRoleId, roleIds).list()
                    .stream().map(SysRoleMenuModel::getMenuId).distinct().collect(Collectors.toList());
            QueryFastLambda.build(param).queryConditionIn(SysMenuModel::getId, menuIds);
        }
        return super.listByParam(param);
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenuModel> selectMenuList(Long userId) {
        return selectMenuList(new SysMenuModel(), userId);
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu   菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenuModel> selectMenuList(SysMenuModel menu, Long userId) {
        List<SysMenuModel> menuList = null;
        menu.setVisible(Constants.VISIBLE.SHOW);
        menu.setStatus(Constants.Status.NORMAL);
        // 管理员显示所有菜单信息
        if (SecurityContextUtil.isAdmin(userId)) {
            menuList = super.listByParam(menu);
        } else {
            SysMenuDTO _queryParam = SysMenuConvert.INSTANCE.convertToDTO(menu);
            _queryParam.setUserId(userId);
            menuList = baseMapper.selectMenuListByUserId(_queryParam);
        }
        return menuList;
    }

    /**
     * 根据权限获取菜单信息
     *
     * @return
     */
    @Override
    public List<SysMenuDTO> selectPermissionList(SysMenuModel param) {
        return SysMenuConvert.INSTANCE.convertToDTO(getPermissionMenuList(param));
    }

    /**
     * 根据权限获取菜单信息，转换成树结构
     *
     * @param param
     * @return
     */
    @Override
    public List<SysMenuTreeVo> selectPermissionTree(SysMenuModel param) {
        List<SysMenuModel> list = getPermissionMenuList(param);
        return TreeUtil.<SysMenuModel, SysMenuTreeVo>build(list)
                .convert(SysMenuConvert.INSTANCE::convertTree)
                .builder();
    }

    /**
     * 根据权限获取菜单信息，转换成树结构模型
     *
     * @param param
     * @return
     */
    @Override
    public List<SysMenuTreeVo> selectPermissionTreeModel(SysMenuModel param) {
        return selectPermissionTree(param);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus
     * @return
     */
    @Override
    public List<SysMenuTreeVo> buildMenuTreeSelect(List<SysMenuDTO> menus) {
        return TreeUtil.<SysMenuDTO, SysMenuTreeVo>build(menus).convert(SysMenuConvert.INSTANCE::dtoConvertTree).builder();
    }

    /**
     * 获取路由（前端进入页面时左侧路由）
     *
     * @param param
     * @return
     */
    @Override
    public List<RouteMenuVo> selectPermissionRoute(SysMenuModel param) {
        return selectPermissionTree(param)
                .stream()
                .sorted(Comparator.comparing(SysMenuTreeVo::getSort))
                .map(RouteMenuVo::new)
                .collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        SysRoleModel role = sysRoleService.getById(roleId);
        return baseMapper.selectMenuListByRoleId(roleId, role.getMenuCheckStrictly());
    }

    /**
     * 获取菜单列表
     *
     * @param param
     * @return
     */
    private List<SysMenuModel> getPermissionMenuList(SysMenuModel param) {
        Long userId = SecurityContextUtil.getUserId();
        param.setStatus(Constants.Status.NORMAL);
        param.setVisible(Constants.VISIBLE.SHOW);
        List<SysMenuModel> menuList = super.listByParam(param);
        if (SecurityContextUtil.isAdmin(userId)) {
            return menuList;
        } else {
            Set<Long> roleIds = SecurityContextUtil.getRoleIds();
            if (CollUtil.isEmpty(roleIds)) {
                return CollUtil.newArrayList();
            } else {
                List<SysRoleMenuModel> list = sysRoleMenuService.lambdaQuery().in(SysRoleMenuModel::getRoleId, roleIds).list();
                if (CollUtil.isEmpty(list)) {
                    return CollUtil.newArrayList();
                }
                List<Long> menus = list.parallelStream().map(SysRoleMenuModel::getMenuId).distinct().collect(Collectors.toList());
                menuList = menuList.parallelStream().filter(item -> menus.contains(item.getId())).collect(Collectors.toList());
                return menuList;
            }
        }
    }

}
