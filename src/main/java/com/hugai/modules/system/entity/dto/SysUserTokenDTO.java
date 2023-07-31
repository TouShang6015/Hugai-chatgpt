package com.hugai.modules.system.entity.dto;

import com.hugai.core.security.context.bean.LoginUserContextBean;
import com.hugai.modules.system.entity.model.SysUserModel;
import com.hugai.modules.system.entity.model.SysUserTokenModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录人token信息 Dto
 *
 * @author WuHao
 * @company Wuhao
 * @date 2022-06-01 11:19:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserTokenDTO extends SysUserTokenModel {

    private SysUserModel sysUserModel;

    private LoginUserContextBean loginUser;

}
