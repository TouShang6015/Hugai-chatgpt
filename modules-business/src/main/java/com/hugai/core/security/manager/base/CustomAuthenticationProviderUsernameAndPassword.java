package com.hugai.core.security.manager.base;

import com.hugai.core.security.context.SecurityContextUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.function.Function;

/**
 * 公共认证提供
 * <p> * 基于用户名与密码的认证Token</p>
 *
 * @author wuhao
 * @date 2022/12/8 14:22
 */
public abstract class CustomAuthenticationProviderUsernameAndPassword<AuthenticationToken extends CustomAuthenticationTokenUsernameAndPassword> implements CustomAuthenticationProvider {

    protected UserDetailsService userDetailsService;

    /**
     * 指定 UserDetailsService 实现
     * <p> * 使用spring set方法注入 </p>
     * <p> @Qualifier(value = "xxxxServiceImpl") </p>
     *
     * @param userDetailsService
     */
    protected abstract void setUserDetailsService(UserDetailsService userDetailsService);

    /**
     * 创建新的AuthenticationToken对象
     *
     * @return
     */
    protected abstract Function<AuthenticationToken, AuthenticationToken> instanceAuthenticationToken();

    protected abstract Class<AuthenticationToken> getAuthenticationTokenClass();

    protected abstract AuthenticationToken convertAuthenticate(Authentication authentication);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationToken authenticationToken = convertAuthenticate(authentication);

        Object principal = authenticationToken.getPrincipal();
        Object credentials = authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername((String) principal);

        String password = userDetails.getPassword();
        if (!SecurityContextUtil.matchesPassword(String.valueOf(credentials), password)) {
            throw new BadCredentialsException("密码不正确");
        }

        AuthenticationToken sysUserAuthenticationToken = instanceAuthenticationToken().apply(authenticationToken);
        sysUserAuthenticationToken.setDetails(userDetails);
        return sysUserAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return getAuthenticationTokenClass().isAssignableFrom(authentication);
    }

}
