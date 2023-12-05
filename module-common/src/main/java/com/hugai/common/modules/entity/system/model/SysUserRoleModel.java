package com.hugai.common.modules.entity.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 用户和角色关联表 Model
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 * @tableName sys_user_role
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_user_role")
public class SysUserRoleModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private int delFlag;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", dataType = "Long")
    private Long userId;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", dataType = "Long")
    private Long roleId;

}
