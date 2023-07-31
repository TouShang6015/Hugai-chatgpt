package com.hugai.modules.system.service.impl;

import com.hugai.modules.system.mapper.SysRolePermissionMapper;
import com.hugai.modules.system.service.ISysRolePermissionService;
import com.hugai.modules.system.entity.model.SysRolePermissionModel;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色和权限关联表 业务实现类
 *
 * @author WuHao
 * @date 2022-09-25 12:01:06
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermissionModel> implements ISysRolePermissionService {

}
