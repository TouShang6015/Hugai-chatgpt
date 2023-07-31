package com.hugai.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hugai.modules.system.entity.model.SysRoleModel;
import com.hugai.modules.system.entity.model.SysUserRoleModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户和角色关联表 持久层接口
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleModel> {

    /**
     * 通过用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @Select("select sr.* from sys_role sr inner join sys_user_role sur on sur.role_id = sr.id where sur.user_id = #{userId} ")
    List<SysRoleModel> selectRoleByUserId(@Param("userId") Long userId);

    /**
     * 通过用户帐号查找角色信息
     *
     * @param userName
     * @return
     */
    List<SysRoleModel> selectRolesByUserName(@Param("userName") String userName);
}
