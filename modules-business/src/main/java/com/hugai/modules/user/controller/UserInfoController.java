package com.hugai.modules.user.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.common.modules.entity.system.vo.auth.LoginBody;
import com.hugai.common.modules.entity.user.convert.UserInfoConvert;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.hugai.common.modules.entity.user.vo.ClientRegisterBody;
import com.hugai.common.modules.entity.user.vo.UserInfoDetailVo;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.framework.log.annotation.Log;
import com.hugai.modules.session.service.SessionInfoService;
import com.hugai.modules.user.service.UserInfoService;
import com.hugai.modules.user.service.auth.UserLoginService;
import com.hugai.modules.user.service.auth.UserRegisterService;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.exception.UserException;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WuHao
 * @since 2023/6/5 10:25
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPrefixConstant.Modules.USER + "/userinfo")
@Api(value = "UserInfoModel", tags = "用户控制器")
public class UserInfoController {

    private final UserInfoService service;

    private final UserLoginService loginService;

    private final UserRegisterService registerService;

    private final SessionInfoService sessionInfoService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登陆", httpMethod = "POST", response = Result.class)
    public Result login(@RequestBody LoginBody param) {
        // 123456
        // aIpYMEwSEoTZSdktaVHCE2Bf8sKH+7pRtae5iOheeIVP+xtBwfOLn7xGqrc2ZMuPlic3lUWalmxmg7svnHEHU6Pe3Ag91+RSOzRKHTGFIqkbo3PBabVhOCg+P4Sn4Q/q+Uz5ArqQFeGk0KCFRVPs1vvya9qvV9voKRw2siMQzQQ=
        LoginUserContextBean loginUser = loginService.doLogin(param);
        if (ObjectUtil.isNull(loginUser))
            throw new UserException("登录失败，用户信息异常！");
        String token = loginService.tokenByContext(loginUser);
        return Result.success(token);
    }

    @GetMapping("/touristLogin")
    @ApiOperation(value = "游客登陆", response = Result.class)
    public Result touristLogin(HttpServletRequest request) {
        LoginUserContextBean loginUser = loginService.touristLogin();
        if (ObjectUtil.isNull(loginUser))
            throw new UserException("登录失败，用户信息异常！");
        String token = loginService.tokenByContext(loginUser);
        return Result.success(token);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", httpMethod = "POST", response = Result.class)
    public Result register(@RequestBody ClientRegisterBody param) {
        registerService.doRegisterByEmail(param);
        return Result.success();
    }

    @GetMapping("/getInfo")
    @ApiOperation(value = "获取用户信息")
    public Result getInfo() {
        Long userId = SecurityContextUtil.getUserId();
        UserInfoModel userInfoModel = service.getById(userId);

        UserInfoDetailVo detail = UserInfoConvert.INSTANCE.convertDetail(userInfoModel);
        return Result.success(detail);
    }

    @Log(title = "客户端用户修改信息")
    @PutMapping("/clientUpdateUser")
    @ApiOperation(value = "客户端用户修改信息")
    public Result clientUpdateUser(@RequestBody UserInfoModel param) {
        Long userId = SecurityContextUtil.getUserId();
        if (!userId.equals(param.getId())) {
            throw new BusinessException("请求参数非法，数据不匹配");
        }
        param.setIfTourist(null);
        param.setUserName(null);
        param.setEmail(null);
        param.setIpaddress(null);
        param.setIpLocation(null);
        param.setStatus(null);
        service.updateById(param);
        return Result.success();
    }

    @Log(title = "用户注册发送短信验证码")
    @GetMapping("/registerSendMail")
    @ApiOperation(value = "用户注册发送短信验证码")
    public Result registerSendMail(String email) {
        Assert.notEmpty(email, () -> new BusinessException("邮箱号不能为空"));
        Assert.isFalse(!Validator.isEmail(email), () -> new BusinessException("邮箱号格式不正确"));
        service.registerSendMail(email);
        return Result.successMsg(String.format("发送成功！请前往%s的邮箱中查看", email));
    }

}
