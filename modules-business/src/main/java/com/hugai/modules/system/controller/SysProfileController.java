package com.hugai.modules.system.controller;

import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.constants.MessageCode;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.core.security.service.TokenService;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.framework.log.annotation.Log;
import com.hugai.common.modules.entity.system.convert.SysUserConvert;
import com.hugai.common.modules.entity.system.dto.SysUserDTO;
import com.hugai.common.modules.entity.system.model.SysUserModel;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.ISysUserService;
import com.org.bebas.enums.result.ResultEnum;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;

import static com.hugai.framework.file.constants.FileTypeConstants.IMAGE_EXTENSION;

/**
 * 个人业务处理
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 */
@RestController
@RequestMapping(ApiPrefixConstant.Modules.SYSTEM + "/profile")
@Api(tags = "个人业务")
public class SysProfileController {

    @Resource
    private ISysUserService userService;
    @Resource
    private TokenService tokenService;
    @Resource
    private BaseResourceWebApi baseResourceWebApi;
    @Resource
    private FileServiceContext fileServiceContext;

    @ApiOperation(value = "个人信息", httpMethod = "GET", response = Result.class)
    @GetMapping
    public Result profile() {
        Long userId = SecurityContextUtil.getUserId();
        SysUserDTO userInfo = SysUserConvert.INSTANCE.convertToDTO(userService.getById(userId));
        Optional.ofNullable(userInfo).ifPresent(item -> {
            ResourceMainVO resourceMainVO = baseResourceWebApi.getResourceMain();
            item.setAvatar(resourceMainVO.getStaticWebsite() + item.getAvatar());
        });
        return Result.success()
                .put("userInfo", userInfo)
                .put("roleGroup", userService.selectUserRoleGroup(userInfo.getUserName()));
    }

    @Log(title = "修改个人信息")
    @ApiOperation(value = "修改个人信息", httpMethod = "PUT", response = Result.class)
    @PutMapping("/update")
    public Result update(@RequestBody SysUserModel param) {
        LoginUserContextBean loginUser = SecurityContextUtil.getLoginUser();
        if (!userService.checkPhoneUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.User.PHONE_EXISTS_HANDLE_FAIL));
        }
        if (!userService.checkEmailUnique(param)) {
            return Result.fail(MessageUtils.message(MessageCode.User.EMAIL_EXISTS_HANDLE_FAIL));
        }
        SysUserModel userModel = userService.getById(loginUser.getUserId());
        userModel.setPassword(null);
        userModel.setUserName(null);
        if (!userService.updateById(param)) {
            return Result.fail(ResultEnum.FAIL_UPDATE);
        }
        // 更新缓存用户信息
        tokenService.setLoginUser(loginUser);
        return Result.success();
    }

    @Log(title = "修改密码")
    @PutMapping("/updatePwd")
    public Result resetPassword(String oldPassword, String newPassword) {
        LoginUserContextBean loginUser = SecurityContextUtil.getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityContextUtil.matchesPassword(oldPassword, password)) {
            return Result.fail("修改密码失败，旧密码错误");
        }
        if (SecurityContextUtil.matchesPassword(newPassword, password)) {
            return Result.fail("新密码不能与旧密码相同");
        }
        if (!userService.resetUserPwd(userName, newPassword)) {
            return Result.fail("修改密码异常，请联系管理员");
        }
        // 更新缓存用户密码
        tokenService.setLoginUser(loginUser);
        return Result.success();
    }

    @Log(title = "修改个人信息")
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody SysUserModel param) {
        param.setId(SecurityContextUtil.getUserId());
        param.setPassword(null);
        param.setUserName(null);
        param.setUserType(null);
        if (!userService.updateById(param)) {
            return Result.fail(ResultEnum.FAIL_UPDATE);
        }
        return Result.success(ResultEnum.SUCCESS_UPDATE);
    }


    /**
     * 头像上传
     */
    @Log(title = "用户头像上传")
    @PostMapping("/avatar")
    public Result avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
        FileService fileService = fileServiceContext.getFileService();
        if (!file.isEmpty()) {
            LoginUserContextBean loginUser = SecurityContextUtil.getLoginUser();

            FileResponse fileResponse = fileService.upload(file, FileTypeRootEnum.image, IMAGE_EXTENSION);

            String filePath = fileResponse.getFilePath();

            SysUserModel model = userService.getById(SecurityContextUtil.getUserId());
            model.setAvatar(filePath);
            if (userService.updateById(model)) {
                Result result = Result.success();
                result.put("imgUrl", filePath);
                // 更新缓存用户头像
                tokenService.setLoginUser(loginUser);
                return result;
            }
        }
        return Result.fail("上传图片异常，请联系管理员");
    }

}
