package com.hugai.core.security.context.bean;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import com.hugai.common.constants.Constants;
import com.org.bebas.core.security.model.BaseSecurityUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * 登录用户身份权限
 *
 * @author WuHao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUserContextBean extends BaseSecurityUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 0 系统用户 1 普通用户
     */
    private String userType;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 角色列表
     */
    private Set<Long> roleIds;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    public LoginUserContextBean() {
    }

    public LoginUserContextBean(Long userId, String userName, String password, Set<String> permissions) {
        this.userId = userId;
        this.permissions = permissions;
        super.setUserId(userId);
        super.setUsername(userName);
        super.setPassword(password);
    }

    public LoginUserContextBean(Long userId, String userName, String password) {
        this.userId = userId;
        this.permissions = CollUtil.newHashSet(Constants.DEFAULT_IN_PARAM);
        super.setUserId(userId);
        super.setUsername(userName);
        super.setPassword(password);
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
