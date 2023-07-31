package com.hugai.core.security.handle;

import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.MessageCode;
import com.hugai.core.security.context.bean.LoginUserContextBean;
import com.hugai.core.security.service.TokenService;
import com.hugai.modules.system.service.ISysLogininforService;
import com.org.bebas.utils.MessageUtils;
import com.org.bebas.utils.ServletUtils;
import com.org.bebas.utils.StringUtils;
import com.org.bebas.utils.result.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author wuhao
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Resource
    private TokenService tokenService;
    @Resource
    private ISysLogininforService sysLogininforService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUserContextBean loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            sysLogininforService.insertLoginLog(userName, sysLogininforService.LOGOUT, MessageUtils.message(MessageCode.System.LOGOUT_SUCCESS));
        }
        ServletUtils.renderString(response, JSON.toJSONString(Result.success("退出成功")));
    }
}
