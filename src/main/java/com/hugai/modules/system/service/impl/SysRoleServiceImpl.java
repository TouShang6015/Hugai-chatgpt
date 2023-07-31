package com.hugai.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.MessageCode;
import com.hugai.common.constants.SecurityConstant;
import com.hugai.common.enums.permission.RoleDefaultKeyEnum;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.system.entity.dto.SysRoleDTO;
import com.hugai.modules.system.entity.model.SysRoleMenuModel;
import com.hugai.modules.system.entity.model.SysRoleModel;
import com.hugai.modules.system.entity.model.SysRolePermissionModel;
import com.hugai.modules.system.entity.model.SysUserRoleModel;
import com.hugai.modules.system.mapper.SysRoleMapper;
import com.hugai.modules.system.service.ISysRoleMenuService;
import com.hugai.modules.system.service.ISysRolePermissionService;
import com.hugai.modules.system.service.ISysRoleService;
import com.hugai.modules.system.service.ISysUserRoleService;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.OptionalUtil;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色信息表 业务实现类
 *
 * @author WuHao
 * @date 2022-05-25 22:01:16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleModel> implements ISysRoleService {

    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private ISysRoleMenuService sysRoleMenuService;
    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    @Override
    public List<SysRoleModel> listByParam(SysRoleModel param) {
        return super.listByParam(param);
    }

    /**
     * 查询列表并且分页通过model
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SysRoleModel> listPageByParam(IPage<SysRoleModel> page, SysRoleModel param) {
        return super.listPageByParam(page, param);
    }

    /**
     * 获取角色权限字段
     *
     * @return
     */
    @Override
    public Tuple2<List<SysRoleModel>, List<String>> getRolePermissions() {
        List<SysRoleModel> roleModelList = this.listByIds(SecurityContextUtil.getRoleIds());
        if (CollUtil.isEmpty(roleModelList)) {
            if (SecurityContextUtil.isAdmin(SecurityContextUtil.getUserId())) {
                return Tuple.of(roleModelList, CollUtil.newArrayList("admin"));
            }
            return Tuple.of(CollUtil.newArrayList(), CollUtil.newArrayList());
        }
        List<String> roleKeyPermission = roleModelList.stream().map(SysRoleModel::getRoleKey).distinct().collect(Collectors.toList());
        return Tuple.of(roleModelList, roleKeyPermission);
    }

    /**
     * 修改保存角色信息
     *
     * @param param 角色信息
     * @return 结果
     */
    @Override
    public int updateRole(SysRoleDTO param) {
        super.updateById(param);
        // 删除角色与菜单关联
        sysRoleMenuService.lambdaUpdate().eq(SysRoleMenuModel::getRoleId, param.getId()).remove();
        Optional.ofNullable(param.getMenuIds()).ifPresent(menuIds -> {
            List<SysRoleMenuModel> roleMenuModelList = menuIds.parallelStream().distinct().map(item -> SysRoleMenuModel.builder().roleId(param.getId()).menuId(item).build()).collect(Collectors.toList());
            if (!CollUtil.isEmpty(roleMenuModelList)) {
                sysRoleMenuService.saveBatch(roleMenuModelList);
            }
        });
        return 1;
    }

    /**
     * 新增保存角色信息
     *
     * @param param 角色信息
     * @return 结果
     */
    @Override
    public boolean insertRole(SysRoleDTO param) {
        if (super.save(param)) {
            Optional.ofNullable(param.getMenuIds()).ifPresent(menuIds -> {
                List<SysRoleMenuModel> roleMenuModelList = menuIds.parallelStream().distinct().map(item -> SysRoleMenuModel.builder().roleId(param.getId()).menuId(item).build()).collect(Collectors.toList());
                if (!CollUtil.isEmpty(roleMenuModelList)) {
                    sysRoleMenuService.saveBatch(roleMenuModelList);
                }
            });
        }
        return true;
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果 true 唯一 false 不唯一
     */
    @Override
    public boolean checkRoleNameUnique(SysRoleModel role) {
        Long roleId = Objects.isNull(role.getId()) ? -1L : role.getId();
        SysRoleModel info = lambdaQuery().eq(SysRoleModel::getRoleName, role.getRoleName()).ne(SysRoleModel::getId, roleId).one();
        return Objects.isNull(info);
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果 true 唯一 false 不唯一
     */
    @Override
    public boolean checkRoleKeyUnique(SysRoleModel role) {
        Long roleId = Objects.isNull(role.getId()) ? -1L : role.getId();
        SysRoleModel info = lambdaQuery().eq(SysRoleModel::getRoleKey, role.getRoleKey()).ne(SysRoleModel::getId, roleId).one();
        return Objects.isNull(info);
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRoleModel role) {
        if (!Objects.isNull(role.getId()) && SecurityContextUtil.isAdmin(role.getId())) {
            throw new BusinessException("不允许操作超级管理员角色");
        }
    }

    /**
     * 角色删除
     *
     * @param idList
     * @return
     */
    @Override
    public boolean deleteByIds(List<Long> idList) {
        if (idList.contains(SecurityConstant.SYSTEM_ID)) {
            throw new BusinessException(MessageUtils.message(MessageCode.Role.SYSTEM_ROLE_NOT_REMOVE));
        }
        List<SysRoleModel> roleList = super.lambdaQuery().in(SysRoleModel::getId, idList).list();

        Assert.isFalse(
                roleList.stream().anyMatch(item -> Arrays.stream(RoleDefaultKeyEnum.values()).map(RoleDefaultKeyEnum::getKey).distinct().collect(Collectors.toList()).contains(item.getRoleKey())),
                () -> new BusinessException("系统预设角色无法操作")
        );

        if (sysUserRoleService.lambdaQuery().in(SysUserRoleModel::getRoleId, idList).count() > 0) {
            throw new BusinessException(MessageUtils.message(MessageCode.Role.ROLE_ALREADY_ALLOT_NOT_REMOVE));
        }
        // 删除角色菜单关联表
        sysRoleMenuService.lambdaUpdate().in(SysRoleMenuModel::getRoleId, idList).remove();
        // 删除角色权限关联表
        sysRolePermissionService.lambdaUpdate().in(SysRolePermissionModel::getRoleId, idList).remove();
        return lambdaUpdate().in(SysRoleModel::getId, idList).remove();
    }

    @Override
    public boolean updateById(SysRoleModel entity) {
        entity.setRoleKey(null);
        return super.updateById(entity);
    }

    /**
     * 角色分配路由
     *
     * @param roleId
     * @param permissionIdList
     * @return
     */
    @Override
    public boolean handleRoleAllocationRoute(Long roleId, List<Long> permissionIdList) {
        // 删除中间表
        sysRolePermissionService.lambdaUpdate().eq(SysRolePermissionModel::getRoleId, roleId).remove();
        // 重新添加中间表
        List<SysRolePermissionModel> _insertParam = permissionIdList.stream().distinct()
                .map(permissionId -> SysRolePermissionModel.builder().roleId(roleId).permissionId(permissionId).build())
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(_insertParam)) {
            sysRolePermissionService.saveBatch(_insertParam);
        }
        return true;
    }

    /**
     * 通过用户获取角色列表
     *
     * @param id
     * @return
     */
    @Override
    public List<SysRoleModel> selectListByUserId(Long id) {
        List<Long> roleIds = OptionalUtil.ofNullList(
                sysUserRoleService.lambdaQuery().eq(SysUserRoleModel::getUserId, id).select(SysUserRoleModel::getRoleId).list()
        ).stream().map(SysUserRoleModel::getRoleId).distinct().collect(Collectors.toList());
        return super.lambdaQuery().in(SysRoleModel::getId, CollUtil.isEmpty(roleIds) ? CollUtil.newArrayList(-1L) : roleIds).list();
    }

    @Override
    public boolean removeById(Serializable id) {
        return this.deleteByIds(CollUtil.newArrayList((Long) id));
    }

}
