package com.hugai.core.security.manager;

import com.hugai.core.security.manager.base.CustomAuthenticationProviderUsernameAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * User 认证提供对象
 *
 * @author wuhao
 * @date 2022/12/6 17:29
 */
@Component
public class UserAuthenticationProvider extends CustomAuthenticationProviderUsernameAndPassword<UserAuthenticationToken> {

    @Autowired
    @Qualifier(value = "UserDetailsUserServiceImpl")
    @Override
    protected void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 创建新的AuthenticationToken对象
     *
     * @return
     */
    @Override
    protected Function<UserAuthenticationToken, UserAuthenticationToken> instanceAuthenticationToken() {
        return authenticationToken -> new UserAuthenticationToken(authenticationToken.getPrincipal(), authenticationToken.getCredentials());
    }

    @Override
    protected Class<UserAuthenticationToken> getAuthenticationTokenClass() {
        return UserAuthenticationToken.class;
    }
}
