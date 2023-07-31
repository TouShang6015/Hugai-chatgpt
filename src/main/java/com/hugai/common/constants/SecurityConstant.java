package com.hugai.common.constants;

/**
 * 安全常量类
 *
 * @author WuHao
 * @date 2022/5/22 22:44
 */
public interface SecurityConstant {

    /**
     * 管理员id
     */
    Long SYSTEM_ID = 1000000000L;
    /**
     * 管理员角色
     */
    String SYSTEM_ROLE = "system";
    /**
     * security管理员鉴权标识
     */
    String PERMISSION_TAG = "*:*:*";

    /**
     * 令牌前缀
     */
    String LOGIN_USER_KEY = "login_user_key";

    /**
     * 管理员标识
     */
    String ADMIN_TAG = "admin";

    /**
     * 令牌
     */
    String TOKEN = "token";

    /**
     * 令牌前缀
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录用户 redis key
     */
    String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 注销
     */
    String LOGOUT = "Logout";

}
