package com.hugai.core.security.manager;

import com.hugai.core.security.manager.base.CustomAuthenticationTokenUsernameAndPassword;

/**
 * sysUser 认证token
 *
 * @author wuhao
 * @date 2022/12/6 16:57
 */
public class UserAuthenticationToken extends CustomAuthenticationTokenUsernameAndPassword {

    public UserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
