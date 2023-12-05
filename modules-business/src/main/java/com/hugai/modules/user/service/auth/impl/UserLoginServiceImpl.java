package com.hugai.modules.user.service.auth.impl;

import cn.hutool.core.lang.Assert;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.service.CaptchaService;
import com.hugai.common.enums.LoginTypeEnum;
import com.hugai.common.utils.RsaUtils;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.config.properties.RsaConfig;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.core.security.manager.UserAuthenticationToken;
import com.hugai.core.security.manager.UserTouristAuthenticationToken;
import com.hugai.core.security.service.TokenService;
import com.hugai.common.modules.entity.system.model.SysUserTokenModel;
import com.hugai.common.modules.entity.system.vo.auth.LoginBody;
import com.hugai.common.entity.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.ISysLogininforService;
import com.hugai.modules.system.service.ISysUserTokenService;
import com.hugai.modules.user.service.UserInfoService;
import com.hugai.modules.user.service.auth.UserLoginService;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.exception.UserException;
import com.org.bebas.utils.DateUtils;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.ServletUtils;
import com.org.bebas.utils.ip.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hugai.common.constants.MessageCode.User.*;


/**
 * @author WuHao
 * @date 2022/5/31 17:46
 */
@RequiredArgsConstructor
@Service
public class UserLoginServiceImpl implements UserLoginService {

    private final BaseResourceWebApi baseResourceWebApi;

    private final AuthenticationManager authenticationManager;

    private final ISysLogininforService sysLogininforService;

    private final ISysUserTokenService sysUserTokenService;

    private final UserInfoService userInfoService;

    private final TokenService tokenService;

    private final CaptchaService captchaService;

    /**
     * 获取最大登陆人数
     *
     * @return
     */
    @Override
    public Integer getMaxLogin() {
        return baseResourceWebApi.getResourceMain().getMaxUserLogin();
    }

    /**
     * 登陆逻辑
     *
     * @param loginBody
     * @return
     */
    @Override
    public LoginUserContextBean doLogin(LoginBody loginBody) {
        ResourceMainVO resourceMainVO = baseResourceWebApi.getResourceMain();
        if (resourceMainVO.getAuthCodeOpen()) {
            Assert.notNull(loginBody.getCaptcha(),() -> new BusinessException("验证码不能为空"));
            ResponseModel verification = captchaService.verification(loginBody.getCaptcha());
            Assert.isFalse(!verification.isSuccess(),() -> new BusinessException("校验码有误"));
        }
        String password = null;
        try {
            // 解密前端加密后的密码
            password = RsaUtils.decryptByPrivateKey(RsaConfig.getPrivateKey(), loginBody.getPassword());
        } catch (Exception e) {
            throw new UserException(MessageUtils.message(LOGIN_FAIL_PARAM_NO_MATCH));
        }
        // 用户验证
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UserAuthenticationToken(loginBody.getUserName(), password));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof BadCredentialsException) {
                sysLogininforService.insertLoginLog(LoginTypeEnum.CLIENT.getType(), loginBody.getUserName(), sysLogininforService.LOGIN_FAIL, MessageUtils.message(USER_NOT_FOUND_PASSWORD_NOT_MATCH));
            } else {
                sysLogininforService.insertLoginLog(LoginTypeEnum.CLIENT.getType(), loginBody.getUserName(), sysLogininforService.LOGIN_FAIL, e.getMessage());
            }
            throw new UserException(MessageUtils.message(USER_NOT_FOUND_PASSWORD_NOT_MATCH));
        }
        sysLogininforService.insertLoginLog(LoginTypeEnum.CLIENT.getType(), loginBody.getUserName(), sysLogininforService.LOGIN_SUCCESS, MessageUtils.message(LOGIN_SUCCESS));
        LoginUserContextBean loginUser = (LoginUserContextBean) authentication.getDetails();
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        return loginUser;
    }

    /**
     * 通过用户上下文对象获取token
     *
     * @param loginUserContextBean
     * @return
     */
    @Override
    public String tokenByContext(LoginUserContextBean loginUserContextBean) {
        String token = tokenService.createToken(loginUserContextBean);
        // 将token保存在数据库中，用于限制最大登录人数
        saveTokenInDataBase(loginUserContextBean);
        return token;
    }

    /**
     * 游客登陆
     *
     * @return
     */
    @Override
    public LoginUserContextBean touristLogin() {
        String ipAddr = IpUtils.getIpAddr(ServletUtils.getRequest());
        // 用户验证
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UserTouristAuthenticationToken(ipAddr));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                sysLogininforService.insertLoginLog(LoginTypeEnum.TOURIST.getType(), ipAddr, sysLogininforService.LOGIN_FAIL, MessageUtils.message(USER_NOT_FOUND_PASSWORD_NOT_MATCH));
                throw new UserException(MessageUtils.message(USER_NOT_FOUND_PASSWORD_NOT_MATCH));
            } else {
                sysLogininforService.insertLoginLog(LoginTypeEnum.TOURIST.getType(), ipAddr, sysLogininforService.LOGIN_FAIL, e.getMessage());
                throw new UserException(e.getMessage());
            }
        }
        sysLogininforService.insertLoginLog(LoginTypeEnum.TOURIST.getType(), ipAddr, sysLogininforService.LOGIN_SUCCESS, MessageUtils.message(LOGIN_SUCCESS));
        LoginUserContextBean loginUser = (LoginUserContextBean) authentication.getDetails();
        loginUser.setIpaddr(ipAddr);
        return loginUser;
    }

    /**
     * 存储token，限制最大登陆数
     *
     * @param loginUser
     * @return
     */
    private void saveTokenInDataBase(LoginUserContextBean loginUser) {
        SysUserTokenModel sysUserToken = SysUserTokenModel.builder()
                .userId(loginUser.getUserId())
                .token(loginUser.getToken())
                .lastTime(DateUtils.nowDateFormat())
                .build();
        sysUserTokenService.save(sysUserToken);
        // 删除redis存放的token，重新插入
        List<SysUserTokenModel> modelList = sysUserTokenService.lambdaQuery().eq(SysUserTokenModel::getUserId, sysUserToken.getUserId()).list();
        Optional.ofNullable(modelList).ifPresent(list -> {
            int maxLogin = this.getMaxLogin();
            int count = list.size();
            if (count > maxLogin) {
                int num = count - maxLogin;
                // 正序排列
                List<SysUserTokenModel> descList = modelList.parallelStream().sorted(Comparator.comparing(SysUserTokenModel::getLastTime)).collect(Collectors.toList());
                for (int i = 0; i < num; i++) {
                    tokenService.delLoginUser(descList.get(i).getToken());
                    sysUserTokenService.removeById(descList.get(i).getId());
                }
            }
        });
    }

}
