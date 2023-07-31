package com.hugai.modules.system.entity.dto;

import com.hugai.modules.system.entity.model.SysRoleModel;
import com.hugai.modules.system.entity.model.SysUserModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户信息表 Dto
 *
 * @author WuHao
 * @company Wuhao
 * @date 2022-05-22 19:35:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserDTO extends SysUserModel {

    private String deptName;

    private String leader;

    private Long roleId;

    private List<SysRoleModel> roleList;

    private List<Long> roleIds;

    private List<Long> postIds;

}
