package com.hugai.modules.system.service.impl;

import com.hugai.modules.system.entity.model.SysRoleModel;
import com.hugai.modules.system.entity.model.SysUserRoleModel;
import com.hugai.modules.system.mapper.SysUserRoleMapper;
import com.hugai.modules.system.service.ISysUserRoleService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户和角色关联表 业务实现类
 *
 * @author WuHao
 * @date 2022-05-25 22:01:16
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleModel> implements ISysUserRoleService {

    /**
     * 查询角色通过用户名
     *
     * @param userName
     * @return
     */
    @Override
    public List<SysRoleModel> selectRolesByUserName(String userName) {
        return baseMapper.selectRolesByUserName(userName);
    }
}
