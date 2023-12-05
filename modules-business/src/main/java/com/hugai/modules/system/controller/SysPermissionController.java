package com.hugai.modules.system.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.Constants;
import com.hugai.common.constants.MessageCode;
import com.org.bebas.web.BaseController;
import com.hugai.framework.log.annotation.Log;
import com.hugai.common.modules.entity.system.convert.SysPermissionConvert;
import com.hugai.common.modules.entity.system.dto.SysPermissionDTO;
import com.hugai.common.modules.entity.system.model.SysPermissionModel;
import com.hugai.common.modules.entity.system.vo.permission.AllocationModuleParamVO;
import com.hugai.common.modules.entity.system.vo.permission.SysPermissionTreeVo;
import com.hugai.modules.system.service.ISysPermissionService;
import com.org.bebas.core.label.LabelBuilder;
import com.org.bebas.core.label.LabelOption;
import com.org.bebas.core.validator.group.GroupUpdate;
import com.org.bebas.utils.MapperUtil;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.result.Result;
import com.org.bebas.utils.tree.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hugai.common.constants.MessageCode.Permission.NOT_ADD_ROUTE;


/**
 * @author Wuhao
 * @date 2022/9/25 17:21
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/syspermission")
@Api(value = "SysPermissionModel", tags = "权限管理")
public class SysPermissionController extends BaseController<ISysPermissionService, SysPermissionModel> {

    @Override
    protected Result baseQueryByParam(@RequestBody SysPermissionModel param) {
        List<SysPermissionDTO> list = SysPermissionConvert.INSTANCE.convertToDTO(service.listByParam(param));
        Map<String, List<SysPermissionDTO>> moduleControllerGroupMap = OptionalUtil.ofNullList(list).parallelStream().collect(Collectors.groupingBy(SysPermissionDTO::getModuleController));
        List<SysPermissionDTO> finalList = moduleControllerGroupMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        return Result.success(finalList);
    }

    @Log(title = "接口路由同步")
    @ApiOperation(value = "接口路由同步", httpMethod = "POST", response = Result.class)
    @PostMapping("/mappingSync")
    public Result mappingSync() {
        return Result.successBoolean(service.handleMappingSync());
    }

    @Log(title = "分配路由模块")
    @ApiOperation(value = "分配路由模块", httpMethod = "POST", response = Result.class)
    @PostMapping("/allocationRouteModule")
    public Result allocationRouteModule(@RequestBody @Valid AllocationModuleParamVO param) {
        Long parentId = param.getParentId();
        List<SysPermissionModel> permissionModelList = param.getPermissionModelList();
        return Result.successBoolean(service.handleAllocationRouteModule(parentId, permissionModelList));
    }

    @Log(title = "改变接口访问规则")
    @ApiOperation(value = "改变接口访问规则", httpMethod = "PUT", response = Result.class)
    @PutMapping("/changeRouteVisitRule")
    public Result changeRouteVisitRule(@RequestBody @Validated({GroupUpdate.class}) SysPermissionModel param) {
        if (service.updateById(param)) {
            return Result.success(param);
        }
        return Result.fail();
    }


    @Override
    protected <DTO> Result baseAdd(@RequestBody DTO m) {
        SysPermissionModel model = MapperUtil.convert(m, SysPermissionModel.class);
        if (model.getIfRoute().equals(Integer.valueOf(Constants.BOOLEAN.TRUE))) {
            return Result.fail(MessageUtils.message(NOT_ADD_ROUTE));
        }
        return super.baseAdd(model);
    }

    @Override
    protected <DTO> Result baseEdit(@RequestBody DTO m) {
        SysPermissionModel model = MapperUtil.convert(m, SysPermissionModel.class);
        model.setRoutePath(null);
        model.setOriginalRoutePath(null);
        model.setPermissionTag(null);
        model.setIfRoute(null);
        return super.baseEdit(model);
    }

    @Override
    protected Result baseDeleteByIds(@PathVariable String ids) {
        List<Long> idList = StringUtils.splitToList(ids, Long::valueOf);
        if (service.lambdaQuery().in(SysPermissionModel::getParentId, idList).count() > 0) {
            return Result.fail(MessageUtils.message(MessageCode.System.EXISTS_DOWN_TYPE_NOT_HANDLE));
        }
        return super.baseDeleteByIds(ids);
    }

    @GetMapping("/rolePermissionTreeList")
    @ApiOperation(value = "获取路由树列表（根据角色）", httpMethod = "GET", response = Result.class)
    public Result rolePermissionTreeList(SysPermissionModel param) {
        List<SysPermissionDTO> dtoList = SysPermissionConvert.INSTANCE.convertToDTO(OptionalUtil.ofNullList(service.listByParam(param)));
        List<SysPermissionTreeVo> treeList = service.buildTreePermissionList(dtoList);
        return Result.success(treeList);
    }

    @GetMapping("/rolePermissionByRoleId/{roleId}")
    @ApiOperation(value = "获取角色拥有的路由id", httpMethod = "GET", response = Result.class)
    public Result rolePermissionByRoleId(@PathVariable("roleId") Long roleId) {
        List<Long> permissionIds = service.rolePermissionByRoleId(roleId);
        return Result.success(permissionIds);
    }

    @Log(title = "刷新路由动态权限")
    @ApiOperation(value = "刷新路由动态权限", httpMethod = "GET", response = Result.class)
    @GetMapping("/flushPermissionConfig")
    public Result flushPermissionConfig() {

        service.flushPermissionConfig();
        return Result.success();
    }

    @GetMapping("/getPermissionModuleLabelOption")
    @ApiOperation(value = "获取权限模块下拉", httpMethod = "GET", response = Result.class)
    public Result getPermissionModuleLabelOption() {
        List<SysPermissionModel> list = service.lambdaQuery()
                .eq(SysPermissionModel::getParentId, SysPermissionModel.DEFAULT_ANCESTORS)
                .eq(SysPermissionModel::getIfRoute, Constants.BOOLEAN.FALSE).list();
        List<LabelOption<String, String>> labelOptionList = LabelBuilder.setValue(list).select(SysPermissionModel::getModuleName, SysPermissionModel::getId).build();
        return Result.success(labelOptionList);
    }

    @GetMapping("/getPermissionNotRouteLabelOption")
    @ApiOperation(value = "获取非路由下拉", httpMethod = "GET", response = Result.class)
    public Result getPermissionNotRouteLabelOption() {
        List<SysPermissionModel> list = service.lambdaQuery().eq(SysPermissionModel::getIfRoute, Constants.BOOLEAN.FALSE).list();
        List<SysPermissionTreeVo> treeList = TreeUtil.<SysPermissionModel, SysPermissionTreeVo>build(list).convert(SysPermissionConvert.INSTANCE::convertTree).builder();
        return Result.success(treeList);
    }

    @Log(title = "刷新路由缓存")
    @ApiOperation(value = "刷新路由缓存", httpMethod = "GET", response = Result.class)
    @GetMapping("/flushRouteCache")
    public Result flushRouteCache() {
        service.flushRouteCache();
        return Result.success();
    }

}
