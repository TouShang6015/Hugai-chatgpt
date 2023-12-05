package com.hugai.common.modules.entity.system.dto;

import com.hugai.common.entity.security.LoginUserContextBean;
import com.hugai.common.modules.entity.system.model.SysUserModel;
import com.hugai.common.modules.entity.system.model.SysUserTokenModel;
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
