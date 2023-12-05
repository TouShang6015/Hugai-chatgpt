package com.hugai.modules.system.controller.auth;

import cn.hutool.core.util.ObjectUtil;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.common.modules.entity.system.vo.auth.LoginBody;
import com.hugai.common.modules.entity.system.vo.auth.RegisterBody;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.hugai.common.modules.entity.system.vo.sysUser.UserInfoDetailVo;
import com.hugai.modules.system.service.ISysUserService;
import com.hugai.modules.system.service.login.ILoginService;
import com.hugai.modules.system.service.login.IRegisterService;
import com.org.bebas.exception.UserException;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 登陆控制器
 *
 * @author WuHao
 * @date 2022/5/31 21:44
 */
@RestController
@RequestMapping(ApiPrefixConstant.Auth.SYSTEM)
@Api(value = "Login", tags = "登陆控制器")
public class LoginPcController {

    @Resource
    private IRegisterService registerService;
    @Resource
    private ILoginService loginService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private BaseResourceWebApi baseResourceWebApi;

    @PostMapping("/login")
    @ApiOperation(value = "用户登陆", httpMethod = "POST", response = Result.class)
    @ApiImplicitParam(name = "param", value = "参数", dataType = "param", required = true, paramType = "body")
    public Result login(@RequestBody LoginBody param) {
        // 123456
        // aIpYMEwSEoTZSdktaVHCE2Bf8sKH+7pRtae5iOheeIVP+xtBwfOLn7xGqrc2ZMuPlic3lUWalmxmg7svnHEHU6Pe3Ag91+RSOzRKHTGFIqkbo3PBabVhOCg+P4Sn4Q/q+Uz5ArqQFeGk0KCFRVPs1vvya9qvV9voKRw2siMQzQQ=
        LoginUserContextBean loginUser = loginService.doLogin(param);
        if (ObjectUtil.isNull(loginUser))
            throw new UserException("登录失败，用户信息异常！");
        String token = loginService.tokenByContext(loginUser);
        return Result.success(token);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", httpMethod = "POST", response = Result.class)
    @ApiImplicitParam(name = "param", value = "参数", dataType = "param", required = true, paramType = "body")
    public Result register(@RequestBody RegisterBody param) {
        return registerService.doRegister(param);
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取用户详细信息", httpMethod = "GET", response = Result.class)
    public Result info() {
        ResourceMainVO resourceMainVO = baseResourceWebApi.getResourceMain();
        UserInfoDetailVo userInfo = sysUserService.getUserInfoDetail(SecurityContextUtil.getUserId());
        Optional.ofNullable(userInfo.getUser()).ifPresent(item -> {
            item.setAvatar(resourceMainVO.getStaticWebsite() + item.getAvatar());
        });
        return Result.success(userInfo);
    }

}
