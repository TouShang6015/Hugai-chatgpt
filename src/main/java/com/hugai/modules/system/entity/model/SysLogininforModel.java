package com.hugai.modules.system.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * 系统访问记录 Model
 *
 * @author WuHao
 * @date 2022-05-25 08:51:34
 * @tableName sys_logininfor
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_logininfor")
public class SysLogininforModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    private static final Integer LOGIN_SUCCESS = 0;
    private static final Integer LOGIN_FAIL = 1;

    @TableField(exist = false)
    private int delFlag;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", dataType = "String")
    private String userName;
    /**
     * 登录IP地址
     */
    @ApiModelProperty(value = "登录IP地址", dataType = "String")
    private String ipaddr;
    /**
     * 登录地点
     */
    @ApiModelProperty(value = "登录地点", dataType = "String")
    private String loginLocation;
    /**
     * 浏览器类型
     */
    @ApiModelProperty(value = "浏览器类型", dataType = "String")
    private String browser;
    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统", dataType = "String")
    private String os;
    /**
     * 登录状态（0成功 1失败）
     */
    @ApiModelProperty(value = "登录状态（0成功 1失败）", dataType = "Integer")
    private Integer status;
    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息", dataType = "String")
    private String msg;
    /**
     * 访问时间
     */
    @ApiModelProperty(value = "访问时间", dataType = "Date")
    private Date loginTime;

    @ApiModelProperty(value = "登陆类型", dataType = "String")
    private String loginType;

}
