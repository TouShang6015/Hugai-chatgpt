package com.hugai.core.security.manager;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * 自定义ip地址认证
 *
 * @author WuHao
 * @since 2023/6/13 16:58
 */
public class UserTouristAuthenticationToken extends AbstractAuthenticationToken implements Authentication {

    private final Object ipaddress;

    public UserTouristAuthenticationToken(Object ipaddress) {
        super(null);
        this.ipaddress = ipaddress;
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return ipaddress;
    }

    @Override
    public Object getPrincipal() {
        return ipaddress;
    }
}
