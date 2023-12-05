package com.hugai.common.modules.entity.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 角色和权限关联表 Model
 *
 * @author WuHao
 * @date 2022-09-25 12:01:06
 * @tableName sys_role_permission
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_role_permission")
public class SysRolePermissionModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private int delFlag;

    /**
     * 权限ID
     */
    @ApiModelProperty(value = "权限ID", dataType = "Long")
    private Long permissionId;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", dataType = "Long")
    private Long roleId;

}
