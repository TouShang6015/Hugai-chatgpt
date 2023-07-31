package com.hugai.modules.system.controller;

import cn.hutool.core.lang.Assert;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.MessageCode;
import com.org.bebas.web.BaseController;
import com.hugai.framework.log.annotation.Log;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.modules.system.entity.dto.SysUserDTO;
import com.hugai.modules.system.entity.model.SysRoleModel;
import com.hugai.modules.system.entity.model.SysUserModel;
import com.hugai.modules.system.entity.model.SysUserRoleModel;
import com.hugai.modules.system.service.ISysRoleService;
import com.hugai.modules.system.service.ISysUserRoleService;
import com.hugai.modules.system.service.ISysUserService;
import com.org.bebas.core.function.OR;
import com.org.bebas.enums.result.ResultEnum;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.MapperUtil;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.futures.FutureUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户信息表 控制器
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/sysuser")
@Api(value = "SysUserModel", tags = "用户信息")
public class SysUserController extends BaseController<ISysUserService, SysUserModel> {

    @Resource
    private ISysRoleService sysRoleService;
    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Override
    protected Result baseQueryById(@PathVariable(value = "id", required = false) Long id) {
        Result result = Result.success();
        FutureUtil.allOf(
                FutureUtil.runAsync(() -> {
                    SysUserModel userModel = service.getById(id);
                    result.put("user", userModel);
                }),
                FutureUtil.runAsync(() -> {
                    // 角色ids
                    List<SysUserRoleModel> sysUserRoleModelList = sysUserRoleService.lambdaQuery().select(SysUserRoleModel::getRoleId).eq(SysUserRoleModel::getUserId, id).list();
                    List<Long> roleIds = OptionalUtil.ofNullList(sysUserRoleModelList).stream().filter(Objects::nonNull).map(SysUserRoleModel::getRoleId).distinct().collect(Collectors.toList());
                    result.put("roleIds", roleIds);
                })
        ).join();
        return result;
    }

    @Log(title = "用户新增")
    @Override
    protected <DTO> Result baseAdd(@RequestBody DTO m) {
        SysUserDTO param = MapperUtil.convert(m, SysUserDTO.class);
        if (!service.checkUserNameUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.User.USER_NAME_EXISTS_HANDLE_FAIL));
        }
        if (!service.checkPhoneUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.User.PHONE_EXISTS_HANDLE_FAIL));
        }
        if (!service.checkEmailUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.User.EMAIL_EXISTS_HANDLE_FAIL));
        }
        OR.run(param.getPassword(), StringUtils::isNotEmpty, password -> {
            param.setPassword(SecurityContextUtil.encryptPassword(password));
        });
        service.addUser(param);
        return Result.success(ResultEnum.SUCCESS_INSERT);
    }

    @Log(title = "用户编辑")
    @Override
    protected <DTO> Result baseEdit(@RequestBody DTO m) {
        SysUserDTO param = MapperUtil.convert(m, SysUserDTO.class);
        if (SecurityContextUtil.isAdmin(param.getId())) {
            return Result.fail(MessageUtils.message(MessageCode.Role.SYSTEM_ROLE_NOT_HANDLE));
        }
        if (!service.checkUserNameUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.User.USER_NAME_EXISTS_HANDLE_FAIL));
        }
        if (!service.checkPhoneUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.User.PHONE_EXISTS_HANDLE_FAIL));
        }
        if (!service.checkEmailUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.User.EMAIL_EXISTS_HANDLE_FAIL));
        }
        param.setPassword(null);
        service.editUser(param);
        return Result.success(ResultEnum.SUCCESS_UPDATE);
    }

    @Log(title = "用户删除")
    @Override
    protected Result baseDeleteByIds(@PathVariable("ids") String ids) {
        List<Long> idList = StringUtils.splitToList(ids, Long::valueOf);
        Long adminUserId = idList.parallelStream().filter(SecurityContextUtil::isAdmin).findFirst().orElse(null);
        if (Objects.nonNull(adminUserId)) {
            return Result.fail(MessageUtils.message(MessageCode.Role.SYSTEM_ROLE_NOT_REMOVE));
        }
        return Result.successBoolean(service.removeByIds(idList));
    }

    @Log(title = "用户重置密码")
    @ApiOperation(value = "重置密码", httpMethod = "PUT", response = Result.class)
    @PutMapping("/resetPwd")
    public Result resetPwd(@RequestBody SysUserModel param) {
        service.checkUserAllowed(param);
        param.setPassword(SecurityContextUtil.encryptPassword(param.getPassword()));
        return Result.successBoolean(service.updateById(param));
    }

    @Log(title = "用户状态修改")
    @ApiOperation(value = "状态修改", httpMethod = "PUT", response = Result.class)
    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody SysUserModel param) {
        service.checkUserAllowed(param);
        return Result.successBoolean(service.updateById(param));
    }

    @ApiOperation(value = "根据用户编号获取授权角色", httpMethod = "GET", response = Result.class)
    @GetMapping("/authRole/{id}")
    public Result authRole(@PathVariable("id") Long id) {
        List<Long> roleIds = OptionalUtil.ofNullList(
                        sysUserRoleService.lambdaQuery().eq(SysUserRoleModel::getUserId, id).list()
                ).stream()
                .map(SysUserRoleModel::getRoleId)
                .distinct()
                .collect(Collectors.toList());
        List<SysRoleModel> roles = OptionalUtil.ofNullList(sysRoleService.listByParam(new SysRoleModel())).stream()
                .filter(item -> roleIds.contains(item.getId()))
                .collect(Collectors.toList());
        if (!SecurityContextUtil.isAdmin(id)) {
            roles = roles.stream().filter(item -> !SecurityContextUtil.isAdmin(item.getId())).collect(Collectors.toList());
        }
        return Result.success()
                .put("user", service.getById(id))
                .put("roles", roles);
    }

    @Log(title = "用户授权角色")
    @ApiOperation(value = "用户授权角色", httpMethod = "PUT", response = Result.class)
    @PutMapping("/insertAuthRole")
    public Result insertAuthRole(@RequestBody HashMap<String, String> param) {
        String userId = param.get("userId");
        String roleIds = param.get("roleIds");
        Assert.isFalse(StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleIds),
                () -> new BusinessException("必要参数不能为空"));
        SysUserDTO queryParam = new SysUserDTO();
        queryParam.setId(Long.valueOf(userId));
        service.checkUserAllowed(queryParam);
        List<Long> roleIdList = StringUtils.splitToList(roleIds, Long::valueOf);
        queryParam.setRoleIds(roleIdList);
        return Result.successBoolean(service.editUser(queryParam));
    }

}
