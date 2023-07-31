package com.hugai.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hugai.modules.system.entity.model.SysPermissionModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * 权限管理 持久层接口
 *
 * @author WuHao
 * @date 2022-09-25 12:01:06
 */
public interface SysPermissionMapper extends BaseMapper<SysPermissionModel> {

    /**
     * 获取用户的已授权路由列表
     *
     * @param userId
     * @return
     */
    List<SysPermissionModel> selectListByUserId(Long userId);

    @Select("select * from sys_permission where find_in_set(#{id}, ancestors)" )
    List<SysPermissionModel> selectChildrenById(@Param("id") Long id);

    Set<String> selectPermissionByRoleKey(String roleKey);
}
