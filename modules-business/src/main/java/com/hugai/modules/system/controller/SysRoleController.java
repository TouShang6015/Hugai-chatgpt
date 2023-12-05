package com.hugai.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.MessageCode;
import com.org.bebas.web.BaseController;
import com.hugai.framework.log.annotation.Log;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.core.security.service.TokenService;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.common.modules.entity.system.dto.SysRoleDTO;
import com.hugai.common.modules.entity.system.dto.SysUserDTO;
import com.hugai.common.modules.entity.system.model.SysRoleModel;
import com.hugai.common.modules.entity.system.model.SysUserRoleModel;
import com.hugai.modules.system.service.ISysPermissionService;
import com.hugai.modules.system.service.ISysRoleService;
import com.hugai.modules.system.service.ISysUserRoleService;
import com.hugai.modules.system.service.ISysUserService;
import com.org.bebas.core.label.LabelBuilder;
import com.org.bebas.core.label.LabelOption;
import com.org.bebas.enums.result.ResultEnum;
import com.org.bebas.utils.MapperUtil;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表 控制器
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/sysrole")
@Api(value = "SysRoleModel", tags = "角色信息")
public class SysRoleController extends BaseController<ISysRoleService, SysRoleModel> {

    @Resource
    private TokenService tokenService;
    @Resource
    private ISysPermissionService sysPermissionService;
    @Resource
    private ISysUserService userService;
    @Resource
    private ISysUserRoleService userRoleService;

    @Override
    protected Result baseDeleteByIds(@PathVariable String ids) {
        List<Long> idList = StringUtils.splitToList(ids, Long::valueOf);
        return Result.success(service.deleteByIds(idList));
    }

    @Log(title = "角色新增")
    @Override
    protected <DTO> Result baseAdd(@RequestBody DTO m) {
        SysRoleDTO param = MapperUtil.convert(m, SysRoleDTO.class);
        if (!service.checkRoleNameUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.Role.ROLE_NAME_EXISTS));
        }
        if (!service.checkRoleKeyUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.Role.ROLE_PERMISSION_EXISTS));
        }
        return Result.successBoolean(service.insertRole(param));
    }

    @Log(title = "角色编辑")
    @Override
    protected <DTO> Result baseEdit(@RequestBody DTO m) {
        SysRoleDTO param = MapperUtil.convert(m, SysRoleDTO.class);
        if (SecurityContextUtil.isAdmin(param.getId())) {
            return Result.fail(MessageUtils.message(MessageCode.Role.SYSTEM_ROLE_NOT_HANDLE));
        }
        if (!service.checkRoleNameUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.Role.ROLE_NAME_EXISTS));
        }
        if (!service.checkRoleKeyUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.Role.ROLE_PERMISSION_EXISTS));
        }
        if (service.updateRole(param) > 0) {
            // 刷新权限
            LoginUserContextBean loginUser = SecurityContextUtil.getLoginUser();
            loginUser.setPermissions(sysPermissionService.getUserPermissionTag(loginUser.getUserId()));
            tokenService.setLoginUser(loginUser);
            return Result.success(ResultEnum.SUCCESS_UPDATE);
        }
        return Result.fail(ResultEnum.FAIL_UPDATE);
    }

    @Log(title = "角色关联修改")
    @ApiOperation(value = "角色修改", httpMethod = "PUT", response = Result.class)
    @PutMapping("/editRole")
    public Result editRole(@RequestBody SysRoleDTO param) {
        if (SecurityContextUtil.isAdmin(param.getId())) {
            return Result.fail(MessageUtils.message(MessageCode.Role.SYSTEM_ROLE_NOT_HANDLE));
        }
        if (!service.checkRoleNameUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.Role.ROLE_NAME_EXISTS));
        }
        if (!service.checkRoleKeyUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.Role.ROLE_PERMISSION_EXISTS));
        }
        if (service.updateById(param)) {
            return Result.success(ResultEnum.SUCCESS_UPDATE);
        }
        return Result.fail(ResultEnum.FAIL_UPDATE);
    }

    @ApiOperation(value = "角色下拉列表", httpMethod = "GET", response = Result.class)
    @GetMapping("/getLabelOption")
    public Result getLabelOption() {
        List<SysRoleModel> list = service.list();
        List<LabelOption<String, String>> labelOptions = LabelBuilder.setValue(list).select(SysRoleModel::getRoleName, SysRoleModel::getId).build();
        return Result.success(labelOptions);
    }

    /**
     * 查询已分配用户角色列表
     */
    @GetMapping("/authUser/allocatedList")
    @ApiOperation(value = "查询未分配用户角色列表", httpMethod = "GET", response = Result.class)
    public Result allocatedList(SysUserDTO param) {
        IPage<SysUserDTO> result = userService.selectAllocatedList(PageUtil.pageBean(param), param);
        return Result.success(result);
    }

    /**
     * 查询未分配用户角色列表
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "查询未分配用户角色列表", httpMethod = "GET", response = Result.class)
    @GetMapping("/authUser/unallocatedList")
    public Result unallocatedList(SysUserDTO param) {
        IPage<SysUserDTO> result = userService.selectUnallocatedList(PageUtil.pageBean(param), param);
        return Result.success(result);
    }

    @ApiOperation(value = "取消授权用户", httpMethod = "PUT", response = Result.class)
    @PutMapping("/authUser/cancel")
    public Result cancelAuthUser(@RequestBody SysUserRoleModel param) {
        boolean result = userRoleService.lambdaUpdate()
                .eq(SysUserRoleModel::getUserId, param.getUserId())
                .eq(SysUserRoleModel::getRoleId, param.getRoleId())
                .remove();
        return Result.successBoolean(result);
    }

    @Log(title = "角色批量取消授权用户")
    @ApiOperation(value = "批量取消授权用户", httpMethod = "PUT", response = Result.class)
    @PutMapping("/authUser/cancelAll")
    public Result cancelAuthUserAll(Long roleId, Long[] userIds) {
        boolean result = userRoleService.lambdaUpdate()
                .eq(SysUserRoleModel::getRoleId, roleId)
                .in(SysUserRoleModel::getUserId, Arrays.asList(userIds))
                .remove();
        return Result.successBoolean(result);
    }

    @Log(title = "角色批量选择用户授权")
    @ApiOperation(value = "批量选择用户授权", httpMethod = "PUT", response = Result.class)
    @PutMapping("/authUser/selectAll")
    public Result selectAuthUserAll(Long roleId, Long[] userIds) {
        List<SysUserRoleModel> list = Arrays.asList(userIds).parallelStream().map(item -> SysUserRoleModel.builder().roleId(roleId).userId(item).build()).collect(Collectors.toList());
        return Result.successBoolean(userRoleService.saveBatch(list));
    }

    @Log(title = "角色分配路由权限")
    @ApiOperation(value = "角色分配路由权限", httpMethod = "PUT", response = Result.class)
    @PutMapping("/roleAllocationRoute")
    public Result roleAllocationRoute(@RequestBody SysRoleDTO param) {
        Long roleId = param.getId();
        List<Long> permissionIds = param.getPermissionIds();
        return Result.successBoolean(service.handleRoleAllocationRoute(roleId, permissionIds));
    }

}
