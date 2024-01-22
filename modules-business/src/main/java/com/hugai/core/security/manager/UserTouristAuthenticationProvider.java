package com.hugai.core.security.manager;

import cn.hutool.core.util.StrUtil;
import com.hugai.core.security.manager.base.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 游客认证提供
 * <p> * 基于用户ip认证Token</p>
 *
 * @author wuhao
 * @date 2022/12/8 14:22
 */
@Component
public class UserTouristAuthenticationProvider implements CustomAuthenticationProvider {

    @Autowired
    @Qualifier(value = "UserDetailsTouristServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserTouristAuthenticationToken authenticationToken = (UserTouristAuthenticationToken) authentication;

        Object principal = authenticationToken.getPrincipal();

        UserDetails userDetails = userDetailsService.loadUserByUsername((String) principal);

        String ipaddress = userDetails.getUsername();
        if (StrUtil.isEmpty(ipaddress)) {
            throw new BadCredentialsException("游客登陆异常");
        }

        UserTouristAuthenticationToken sysUserAuthenticationToken = new UserTouristAuthenticationToken(authenticationToken.getPrincipal());
        sysUserAuthenticationToken.setDetails(userDetails);
        return sysUserAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserTouristAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
