package com.hugai.modules.user.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.core.openai.entity.response.UserAccountResponse;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.core.security.context.bean.LoginUserContextBean;
import com.hugai.framework.log.annotation.Log;
import com.hugai.modules.system.entity.vo.auth.ClientRegisterBody;
import com.hugai.modules.system.entity.vo.auth.LoginBody;
import com.hugai.modules.user.entity.convert.UserInfoConvert;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.hugai.modules.user.entity.vo.UserInfoDetailVo;
import com.hugai.modules.user.service.UserInfoService;
import com.hugai.modules.user.service.login.UserLoginService;
import com.hugai.modules.user.service.login.UserRegisterService;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.exception.UserException;
import com.org.bebas.utils.page.PageUtil;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        return registerService.doRegister(param);
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
        service.updateById(param);
        return Result.success();
    }

    @Log(title = "用户背景修改")
    @GetMapping("/editUserBackground")
    @ApiOperation(value = "用户背景修改")
    public Result editUserBackground(String imgUrl) {
        Long userId = SecurityContextUtil.getUserId();
        service.lambdaUpdate().set(UserInfoModel::getDeskImgUrl, imgUrl).eq(UserInfoModel::getId, userId).update();
        return Result.success(imgUrl);
    }

    @GetMapping("/getUserGrants")
    @ApiOperation(value = "获取用户api账户信息")
    public Result getUserGrants() {
        List<UserAccountResponse> list = service.getUserGrants();
        return Result.success(list);
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

    @PostMapping("/queryPageListByParam")
    @ApiOperation(value = "分页查询（后台）")
    public Result queryPageListByParam(@RequestBody UserInfoModel param) {
        IPage<UserInfoModel> page = service.listPageByParam(PageUtil.pageBean(param), param);
        return Result.success(page);
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
