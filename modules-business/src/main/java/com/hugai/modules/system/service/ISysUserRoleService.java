package com.hugai.modules.system.service;

import com.hugai.common.modules.entity.system.model.SysRoleModel;
import com.hugai.common.modules.entity.system.model.SysUserRoleModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * 用户和角色关联表 业务接口
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 */
public interface ISysUserRoleService extends IService<SysUserRoleModel> {

    /**
     * 查询角色通过用户名
     *
     * @param userName
     * @return
     */
    List<SysRoleModel> selectRolesByUserName(String userName);
}
