package com.hugai.modules.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.modules.entity.user.convert.UserInfoConvert;
import com.hugai.common.modules.entity.user.dto.UserInfoDTO;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.hugai.framework.log.annotation.Log;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.user.service.UserInfoAdminService;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author WuHao
 * @since 2023/10/10 16:29
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.USER + "/admin/userinfo")
@Api(value = "UserInfoModel", tags = "用户(管理端)控制器")
public class UserInfoAdminController {

    private final UserInfoAdminService adminService;

    private final UserInfoService service;

    private final SessionInfoService sessionInfoService;

    @PostMapping("/queryPageListByParam")
    @ApiOperation(value = "分页查询（后台）")
    public Result queryPageListByParam(@RequestBody UserInfoModel param) {
        IPage<UserInfoModel> page = service.listPageByParam(PageUtil.pageBean(param), param);
        IPage<UserInfoDTO> dtoPage = PageUtil.convert(page, UserInfoConvert.INSTANCE::convertToDTO);
        return Result.success(dtoPage);
    }

    @Log(title = "用户修改信息（后台）")
    @PutMapping("/updateUserInfo")
    @ApiOperation(value = "用户修改信息（后台）")
    public Result updateUserInfo(@RequestBody UserInfoModel param) {
        param.setIfTourist(null);
        param.setUserName(null);
        param.setEmail(null);
        param.setIpaddress(null);
        service.updateById(param);
        return Result.success();
    }


}
