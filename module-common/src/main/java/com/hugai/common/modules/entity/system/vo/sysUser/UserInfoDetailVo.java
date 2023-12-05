package com.hugai.common.modules.entity.system.vo.sysUser;

import com.hugai.common.modules.entity.system.model.SysRoleModel;
import com.hugai.common.modules.entity.system.model.SysUserModel;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author WuHao
 * @date 2022/6/4 17:19
 */
@Data
public class UserInfoDetailVo {

    private SysUserModel user;

    private List<SysRoleModel> roleList;

    private String token;
    /**
     * 权限标识
     */
    private Set<String> permissions;

}
