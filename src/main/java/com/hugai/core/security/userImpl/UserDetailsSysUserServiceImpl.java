package com.hugai.core.security.userImpl;

import com.hugai.common.constants.Constants;
import com.hugai.common.enums.UserTypeEnum;
import com.hugai.core.security.context.bean.LoginUserContextBean;
import com.hugai.modules.system.entity.model.SysUserModel;
import com.hugai.modules.system.entity.model.SysUserRoleModel;
import com.hugai.modules.system.service.ISysPermissionService;
import com.hugai.modules.system.service.ISysUserRoleService;
import com.hugai.modules.system.service.ISysUserService;
import com.org.bebas.exception.UserException;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户验证处理
 *
 * @author WhHao
 */
@Service("UserDetailsSysUserServiceImpl")
public class UserDetailsSysUserServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsSysUserServiceImpl.class);

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysPermissionService sysPermissionService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserModel user = sysUserService.selectUserByUserName(username);
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

    public UserDetails createLoginUser(SysUserModel user) {
        LoginUserContextBean loginUser = new LoginUserContextBean(
                user.getId()
                , user.getUserName()
                , user.getPassword()
                , user.getCreateTime()
                , sysPermissionService.getUserPermissionTag(user.getId())
        );
        loginUser.setUserType(UserTypeEnum.SYS.getKey());
        SysUserRoleModel queryParam = new SysUserRoleModel();
        queryParam.setUserId(user.getId());
        List<SysUserRoleModel> userRoleList = OptionalUtil.ofNullList(sysUserRoleService.listByParam(queryParam));
        loginUser.setRoleIds(userRoleList.stream().map(SysUserRoleModel::getRoleId).collect(Collectors.toSet()));
        return loginUser;
    }
}
