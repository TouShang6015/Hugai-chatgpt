package com.hugai.common.modules.entity.system.dto;

import com.hugai.common.modules.entity.system.model.SysRoleModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色信息表 Dto
 *
 * @author WuHao
 * @company Wuhao
 * @date 2022-05-25 19:02:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDTO extends SysRoleModel {

    private List<Long> menuIds;

    private List<Long> deptIds;

    private List<Long> permissionIds;

}
