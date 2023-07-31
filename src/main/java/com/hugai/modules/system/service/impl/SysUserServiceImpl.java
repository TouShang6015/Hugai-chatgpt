package com.hugai.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hugai.common.constants.MessageCode;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.system.entity.convert.SysUserConvert;
import com.hugai.modules.system.entity.dto.SysUserDTO;
import com.hugai.modules.system.entity.model.SysRoleModel;
import com.hugai.modules.system.entity.model.SysUserModel;
import com.hugai.modules.system.entity.model.SysUserRoleModel;
import com.hugai.modules.system.entity.vo.sysUser.UserInfoDetailVo;
import com.hugai.modules.system.mapper.SysUserMapper;
import com.hugai.modules.system.service.*;
import com.org.bebas.core.function.OpenRunnable;
import com.org.bebas.exception.UserException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户信息表 业务实现类
 *
 * @author WuHao
 * @date 2022-05-24 23:43:38
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserModel> implements ISysUserService {

    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private ISysRoleService sysRoleService;
    @Resource
    private ISysMenuService sysMenuService;
    @Resource
    private ISysPermissionService sysPermissionService;

    /**
     * 获取用户详细信息
     *
     * @param id
     * @return
     */
    @Override
    public UserInfoDetailVo getUserInfoDetail(Long id) {
        if (ObjectUtil.isNull(id))
            id = SecurityContextUtil.getUserId();
        UserInfoDetailVo userInfo = new UserInfoDetailVo();
        SysUserModel user = this.getById(id);
        userInfo.setUser(user);
        // 角色权限
        userInfo.setRoleList(sysRoleService.selectListByUserId(id));
        // 菜单权限
        userInfo.setPermissions(SecurityContextUtil.getLoginUser().getPermissions());
        return userInfo;
    }

    /**
     * 查询已分配用户角色列表
     *
     * @param page
     * @param user
     * @return
     */
    @Override
    public IPage<SysUserDTO> selectAllocatedList(IPage<SysUserDTO> page, SysUserDTO user) {
        return baseMapper.selectAllocatedList(page, user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表-分页
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public IPage<SysUserDTO> selectUnallocatedList(IPage<SysUserDTO> page, SysUserDTO user) {
        return baseMapper.selectUnallocatedList(page, user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUserModel selectUserByUserName(String userName) {
        return lambdaQuery().eq(SysUserModel::getUserName, userName).one();
    }

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRoleModel> list = sysUserRoleService.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRoleModel::getRoleName).collect(Collectors.joining(StringPool.COMMA));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param param 用户名称
     * @return 结果 true 唯一 false 不唯一
     */
    @Override
    public boolean checkUserNameUnique(SysUserModel param) {
        Long userId = Objects.isNull(param.getId()) ? -1L : param.getId();
        SysUserModel model = lambdaQuery().eq(SysUserModel::getUserName, param.getUserName()).ne(SysUserModel::getId, userId).one();
        return Objects.isNull(model);
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param param 用户信息
     * @return 结果
     */
    @Override
    public boolean checkPhoneUnique(SysUserModel param) {
        Long userId = Objects.isNull(param.getId()) ? -1L : param.getId();
        SysUserModel model = lambdaQuery().eq(SysUserModel::getPhonenumber, param.getPhonenumber()).ne(SysUserModel::getId, userId).one();
        return Objects.isNull(model);
    }

    /**
     * 校验email是否唯一
     *
     * @param param 用户信息
     * @return 结果
     */
    @Override
    public boolean checkEmailUnique(SysUserModel param) {
        Long userId = Objects.isNull(param.getId()) ? -1L : param.getId();
        SysUserModel model = lambdaQuery().eq(SysUserModel::getEmail, param.getEmail()).ne(SysUserModel::getId, userId).one();
        return Objects.isNull(model);
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUserModel user) {
        if (Objects.nonNull(user) && SecurityContextUtil.isAdmin(user.getId())) {
            throw new UserException(MessageUtils.message(MessageCode.User.NOT_HANDLE_ADMIN_USER));
        }
    }

    /**
     * 新增用户接口
     *
     * @param dtoList
     * @return
     */
    public boolean addUser(List<SysUserDTO> dtoList) {
        // 初始化id
        List<SysUserDTO> dtoInitList = dtoList.stream().filter(Objects::nonNull).peek(item -> {
            ModelUtil.initModel(item);
            OpenRunnable.run(StrUtil.isEmpty(item.getPassword()), () -> item.setPassword(SecurityContextUtil.encryptPassword(item.getPassword())));
        }).collect(Collectors.toList());
        List<SysUserModel> insertParam = SysUserConvert.INSTANCE.convertToModel(dtoInitList);
        // 新增主表
        boolean result = super.saveBatch(insertParam);
        // 新增子表
        insertSublistByDto(dtoInitList);
        return result;
    }

    /**
     * 更新用户
     *
     * @param dtoList
     * @return
     */
    public boolean editUser(List<SysUserDTO> dtoList) {
        List<SysUserModel> updateParam = SysUserConvert.INSTANCE.convertToModel(dtoList);
        List<Long> userIds = dtoList.stream().map(SysUserDTO::getId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        // 删除子表
        this.deleteSublistByMainId(userIds);
        // 修改主表
        boolean result = super.updateBatchById(updateParam);
        // 新增子表
        insertSublistByDto(dtoList);
        return result;
    }

    /**
     * @param list
     * @return
     */
    @Override
    public boolean removeByIds(Collection<?> list) {
        // 删除子表
        this.deleteSublistByMainId(list);
        // 删除主表
        return super.removeByIds(list);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public boolean resetUserPwd(String userName, String password) {
        String newPassword = SecurityContextUtil.encryptPassword(password);
        return lambdaUpdate().set(SysUserModel::getPassword, newPassword).eq(SysUserModel::getUserName, userName).update();
    }

    /**
     * 通过主键删除关联子表
     *
     * @param ids
     * @return
     */
    @Override
    public void deleteSublistByMainId(Collection<?> ids) {
        // 删除角色中间表
        sysUserRoleService.lambdaUpdate().in(SysUserRoleModel::getUserId, ids).remove();
    }

    /**
     * 添加子表根据dto
     *
     * @param dtoList
     * @return
     */
    public void insertSublistByDto(List<SysUserDTO> dtoList) {
        Function<SysUserDTO, List<SysUserRoleModel>> functionGetRoleModelList = i -> Optional.ofNullable(i.getRoleIds()).orElseGet(ArrayList::new).stream()
                .map(roleId -> SysUserRoleModel.builder().userId(i.getId()).roleId(roleId).build())
                .collect(Collectors.toList());
        List<SysUserRoleModel> insertRoleParam = dtoList.stream().filter(Objects::nonNull).map(functionGetRoleModelList).flatMap(Collection::stream).distinct().collect(Collectors.toList());

        sysUserRoleService.saveBatch(insertRoleParam);
    }

    // =================================

}
