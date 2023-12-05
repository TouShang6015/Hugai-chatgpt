package com.hugai.core.security.userImpl;

import com.hugai.common.constants.Constants;
import com.hugai.common.enums.UserTypeEnum;
import com.hugai.common.enums.permission.RoleDefaultKeyEnum;
import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.modules.system.service.ISysPermissionService;
import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.exception.UserException;
import com.org.bebas.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户验证处理
 *
 * @author WhHao
 */
@Service("UserDetailsUserServiceImpl")
public class UserDetailsUserServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsUserServiceImpl.class);

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ISysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoModel user = null;
        user = userInfoService.selectUserByUserName(username);
        if (Objects.isNull(user)){
            user = userInfoService.selectUserByeEmail(username);
        }
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("登录用户：" + username + " 不存在");
        } else if (Constants.DelFlag.DEL.equals(String.valueOf(user.getDelFlag()))) {
            log.info("登录用户：{} 已被删除.", username);
            throw new UserException("对不起，您的账号：" + username + " 已被删除");
        } else if (Constants.Disable.DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("对不起，您的账号：" + username + " 已停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(UserInfoModel user) {
        LoginUserContextBean loginUserContextBean = new LoginUserContextBean(
                user.getId()
                , user.getUserName()
                , user.getPassword()
                , user.getCreateTime()
                , sysPermissionService.getPermissionByRoleKey(RoleDefaultKeyEnum.tourist.getKey())
        );
        loginUserContextBean.setUserType(UserTypeEnum.USER.getKey());
        return loginUserContextBean;
    }
}
