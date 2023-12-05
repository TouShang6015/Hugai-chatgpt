package com.hugai.modules.system.service;

import com.hugai.common.modules.entity.system.dto.SysRoleDTO;
import com.hugai.common.modules.entity.system.model.SysRoleModel;
import com.org.bebas.mapper.service.IService;
import io.vavr.Tuple2;

import java.util.List;

/**
 * 角色信息表 业务接口
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 */
public interface ISysRoleService extends IService<SysRoleModel> {

    /**
     * 根据用户id获取角色权限字段
     *
     * @return
     */
    Tuple2<List<SysRoleModel>, List<String>> getRolePermissions();

    /**
     * 修改保存角色信息
     *
     * @param param 角色信息
     * @return 结果
     */
    int updateRole(SysRoleDTO param);

    /**
     * 新增保存角色信息
     *
     * @param param 角色信息
     * @return 结果
     */
    boolean insertRole(SysRoleDTO param);

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果 true 唯一 false 不唯一
     */
    boolean checkRoleNameUnique(SysRoleModel role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果 true 唯一 false 不唯一
     */
    boolean checkRoleKeyUnique(SysRoleModel role);

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    void checkRoleAllowed(SysRoleModel role);

    /**
     * 角色删除
     *
     * @param idList
     * @return
     */
    boolean deleteByIds(List<Long> idList);

    /**
     * 角色分配路由
     *
     * @param roleId
     * @param permissionIdList
     * @return
     */
    boolean handleRoleAllocationRoute(Long roleId, List<Long> permissionIdList);

    /**
     * 通过用户获取角色列表
     *
     * @param id
     * @return
     */
    List<SysRoleModel> selectListByUserId(Long id);
}
