package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 角色信息表 Model
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 * @tableName sys_role
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_role")
public class SysRoleModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", dataType = "String")
    private String roleName;
    /**
     * 角色权限字符串
     */
    @ApiModelProperty(value = "角色权限字符串", dataType = "String")
    private String roleKey;
    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", dataType = "Integer")
    private Integer roleSort;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）", dataType = "String")
    private String dataScope;
    /**
     * 菜单树选择项是否关联显示
     */
    @ApiModelProperty(value = "菜单树选择项是否关联显示", dataType = "Integer")
    private Integer menuCheckStrictly;
    /**
     * 部门树选择项是否关联显示
     */
    @ApiModelProperty(value = "部门树选择项是否关联显示", dataType = "Integer")
    private Integer deptCheckStrictly;
    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value = "角色状态（0正常 1停用）", dataType = "String")
    private String status;

}
