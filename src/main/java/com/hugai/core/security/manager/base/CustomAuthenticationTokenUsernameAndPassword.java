package com.hugai.core.security.manager.base;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 公共认证Token
 * <p> * 基于用户名与密码的认证Token</p>
 * @author wuhao
 * @date 2022/12/8 14:56
 */
public abstract class CustomAuthenticationTokenUsernameAndPassword extends AbstractAuthenticationToken {

    protected Object username;

    protected Object password;

    public CustomAuthenticationTokenUsernameAndPassword(Object username, Object password) {
        super(null);
        this.username = username;
        this.password = password;
        setAuthenticated(false);
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        super.setAuthenticated(false);
    }

}
