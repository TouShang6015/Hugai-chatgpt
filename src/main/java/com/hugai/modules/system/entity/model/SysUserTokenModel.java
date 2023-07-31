package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 登录人token信息 Model
 *
 * @author WuHao
 * @date 2022-06-01 11:19:47
 * @tableName sys_user_token
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_user_token")
public class SysUserTokenModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private int delFlag;

    /**
     * 登录人主键
     */
    @ApiModelProperty(value = "登录人主键", dataType = "Long")
    private Long userId;
    /**
     * token key
     */
    @ApiModelProperty(value = "token key", dataType = "String")
    private String token;
    /**
     * 最新更新时间
     */
    @ApiModelProperty(value = "最新更新时间", dataType = "String")
    private String lastTime;

}
