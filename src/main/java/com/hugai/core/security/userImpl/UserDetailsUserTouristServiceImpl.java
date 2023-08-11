package com.hugai.core.security.userImpl;

import com.hugai.common.constants.Constants;
import com.hugai.common.enums.UserTypeEnum;
import com.hugai.common.enums.permission.RoleDefaultKeyEnum;
import com.hugai.core.security.context.bean.LoginUserContextBean;
import com.hugai.modules.system.service.ISysPermissionService;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.hugai.modules.user.service.UserInfoService;
import com.org.bebas.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 游客登陆验证
 *
 * @author WhHao
 */
@Service("UserDetailsTouristServiceImpl")
public class UserDetailsUserTouristServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsUserTouristServiceImpl.class);

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ISysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String ipaddress) throws UsernameNotFoundException {
        UserInfoModel user = userInfoService.selectByIpaddress(ipaddress);
        if (Objects.isNull(user)) {
            user = UserInfoModel.builder().ipaddress(ipaddress).status(Constants.Disable.NORMAL).ifTourist(Constants.BOOLEAN.TRUE).build();
            userInfoService.save(user);
        } else if (Constants.DelFlag.DEL.equals(String.valueOf(user.getDelFlag()))) {
            log.info("登录用户：{} 已被删除.", ipaddress);
            throw new UserException("对不起，您的账号：" + ipaddress + " 已被删除");
        } else if (Constants.Disable.DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", ipaddress);
            throw new UserException("对不起，您的账号：" + ipaddress + " 已停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(UserInfoModel user) {
        LoginUserContextBean loginUserContextBean = new LoginUserContextBean(
                user.getId()
                , user.getIpaddress()
                , user.getPassword()
                , user.getCreateTime()
                , sysPermissionService.getPermissionByRoleKey(RoleDefaultKeyEnum.tourist.getKey())
        );
        loginUserContextBean.setUserType(UserTypeEnum.USER.getKey());
        return loginUserContextBean;
    }
}
