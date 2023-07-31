package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 角色和菜单关联表 Model
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 * @tableName sys_role_menu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_role_menu")
public class SysRoleMenuModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private int delFlag;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", dataType = "Long")
    private Long roleId;
    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID", dataType = "Long")
    private Long menuId;

}
