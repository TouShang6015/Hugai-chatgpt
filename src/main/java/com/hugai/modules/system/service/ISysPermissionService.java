package com.hugai.modules.system.service;

import com.hugai.modules.system.entity.dto.SysPermissionDTO;
import com.hugai.modules.system.entity.model.SysPermissionModel;
import com.hugai.modules.system.entity.vo.permission.SysPermissionTreeVo;
import com.org.bebas.mapper.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 权限管理 业务接口
 *
 * @author WuHao
 * @date 2022-09-25 12:01:06
 */
public interface ISysPermissionService extends IService<SysPermissionModel> {

    /**
     * 获取项目路由地址
     *
     * @return
     */
    List<SysPermissionModel> getProjectRequestMapping();

    /**
     * 接口路由同步
     *
     * @return
     */
    boolean handleMappingSync();

    /**
     * 分配路由模块
     *
     * @param parentId
     * @param permissionModelList
     * @return
     */
    boolean handleAllocationRouteModule(Long parentId, List<SysPermissionModel> permissionModelList);

    /**
     * 构建树结构列表
     *
     * @param dtoList
     * @return
     */
    List<SysPermissionTreeVo> buildTreePermissionList(List<SysPermissionDTO> dtoList);

    /**
     * 获取角色的路由列表
     *
     * @param roleId
     * @return
     */
    List<Long> rolePermissionByRoleId(Long roleId);

    /**
     * 获取角色路由权限通过角色唯一标识
     *
     * @param roleKey
     * @return
     */
    Set<String> getPermissionByRoleKey(String roleKey);

    /**
     * 刷新security动态权限
     */
    void flushPermissionConfig();

    /**
     * 刷新路由缓存
     */
    void flushRouteCache();


    /**
     * 获取所有控制器信息列表
     *
     * @return
     */
    List<SysPermissionModel> getRouteList();

    /**
     * 获取用户的权限标识
     *
     * @return
     */
    Set<String> getUserPermissionTag(Long userId);

}
