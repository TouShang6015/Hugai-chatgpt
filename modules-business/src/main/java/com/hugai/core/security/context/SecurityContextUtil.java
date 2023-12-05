package com.hugai.core.security.context;

import cn.hutool.core.lang.Assert;
import com.hugai.common.constants.SecurityConstant;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.common.enums.UserTypeEnum;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.core.flowenum.utils.FlowEnumUtils;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.exception.UserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

/**
 * 安全服务工具类
 *
 * @author wuhao
 */
public class SecurityContextUtil {
    /**
     * 用户ID
     **/
    public static Long getUserId() {
        try {
            return getLoginUser().getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserException("获取用户ID异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取角色ID列表
     **/
    public static Set<Long> getRoleIds() {
        try {
            return getLoginUser().getRoleIds();
        } catch (Exception e) {
            throw new UserException("获取角色列表异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new UserException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUserContextBean getLoginUser() {
        try {
            return (LoginUserContextBean) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new UserException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户 不抛出异常的
     *
     * @return
     */
    public static LoginUserContextBean getLoginUserNotThrow() {
        try {
            return getLoginUser();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户类型
     **/
    public static UserTypeEnum getUserType() {
        return FlowEnumUtils.getEnumByKey(getLoginUser().getUserType(), UserTypeEnum.class);
    }

    /**
     * 校验是否为普通用户，否则抛出异常
     */
    public static UserTypeEnum verifyIsNormalUser() {
        UserTypeEnum userType = getUserType();
        Assert.isFalse(!userType.equals(UserTypeEnum.USER), () -> new BusinessException("用户类型异常"));
        return userType;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param mainId 用户ID、角色id
     * @return 结果
     */
    public static boolean isAdmin(Long mainId) {
        return SecurityConstant.SYSTEM_ID.equals(mainId);
    }

}
