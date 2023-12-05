package com.hugai.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hugai.common.modules.entity.system.model.SysMenuModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表 持久层接口
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 */
public interface SysMenuMapper extends BaseMapper<SysMenuModel> {

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenuModel> selectMenuListByUserId(SysMenuModel menu);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId            角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 选中菜单列表
     */
    List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") Integer menuCheckStrictly);

}
