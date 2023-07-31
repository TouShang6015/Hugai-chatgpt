package com.hugai.modules.system.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.modules.system.entity.dto.SysUserDTO;
import com.hugai.modules.system.entity.model.SysUserModel;
import com.hugai.modules.system.entity.vo.sysUser.UserInfoDetailVo;
import com.org.bebas.mapper.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息表 业务接口
 *
 * @author WuHao
 * @date 2022-05-22 19:35:58
 */
public interface ISysUserService extends IService<SysUserModel> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUserModel selectUserByUserName(String userName);

    /**
     * 获取用户详细信息
     *
     * @return
     */
    UserInfoDetailVo getUserInfoDetail(Long id);

    /**
     * 查询已分配用户角色列表
     *
     * @param page
     * @param user
     * @return
     */
    IPage<SysUserDTO> selectAllocatedList(IPage<SysUserDTO> page, SysUserDTO user);

    /**
     * 根据条件分页查询未分配用户角色列表-分页
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    IPage<SysUserDTO> selectUnallocatedList(IPage<SysUserDTO> page, SysUserDTO user);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    String selectUserRoleGroup(String userName);

    /**
     * 校验用户名称是否唯一
     *
     * @param param
     * @return 结果
     */
    boolean checkUserNameUnique(SysUserModel param);

    /**
     * 校验手机号码是否唯一
     *
     * @param param 用户信息
     * @return 结果
     */
    boolean checkPhoneUnique(SysUserModel param);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkEmailUnique(SysUserModel user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(SysUserModel user);

    /**
     * 新增用户接口
     *
     * @param dtoList
     * @return
     */
    boolean addUser(List<SysUserDTO> dtoList);

    /**
     * 新增用户（单个用户）
     *
     * @param dto
     * @return
     */
    default boolean addUser(SysUserDTO dto) {
        return addUser(CollUtil.newArrayList(dto));
    }

    /**
     * 更新用户
     *
     * @param dtoList
     * @return
     */
    boolean editUser(List<SysUserDTO> dtoList);

    /**
     * 更新用户（单个）
     *
     * @param dto
     * @return
     */
    default boolean editUser(SysUserDTO dto) {
        return editUser(CollUtil.newArrayList(dto));
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    boolean resetUserPwd(String userName, String password);

    /**
     * 通过主键删除关联子表
     *
     * @param ids
     * @return
     */
    void deleteSublistByMainId(Collection<?> ids);

    /**
     * 添加子表根据dto
     *
     * @param dtoList
     * @return
     */
    void insertSublistByDto(List<SysUserDTO> dtoList);

}
