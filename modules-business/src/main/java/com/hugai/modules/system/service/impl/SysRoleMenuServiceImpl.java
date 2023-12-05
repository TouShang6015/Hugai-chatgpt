package com.hugai.modules.system.service.impl;

import com.hugai.modules.system.mapper.SysRoleMenuMapper;
import com.hugai.modules.system.service.ISysRoleMenuService;
import com.hugai.common.modules.entity.system.model.SysRoleMenuModel;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表 业务实现类
 *
 * @author WuHao
 * @date 2022-05-25 22:01:16
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuModel> implements ISysRoleMenuService {

}
