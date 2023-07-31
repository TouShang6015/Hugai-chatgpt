package com.hugai.core.security.manager;

import com.hugai.core.security.manager.base.CustomAuthenticationProviderUsernameAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * sysUser 认证提供对象
 * @author wuhao
 * @date 2022/12/6 17:29
 */
@Component
public class SysUserAuthenticationProvider extends CustomAuthenticationProviderUsernameAndPassword<SysUserAuthenticationToken> {

    @Autowired
    @Qualifier(value = "UserDetailsSysUserServiceImpl")
    @Override
    protected void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 创建新的AuthenticationToken对象
     * @return
     */
    @Override
    protected Function<SysUserAuthenticationToken, SysUserAuthenticationToken> instanceAuthenticationToken() {
        return authenticationToken -> new SysUserAuthenticationToken(authenticationToken.getPrincipal(), authenticationToken.getCredentials());
    }

    @Override
    protected Class<SysUserAuthenticationToken> getAuthenticationTokenClass() {
        return SysUserAuthenticationToken.class;
    }
}
