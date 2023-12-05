package com.hugai.common.modules.entity.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * 用户信息表 Model
 *
 * @author WuHao
 * @date 2022-05-25 19:02:33
 * @tableName sys_user
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_user")
public class SysUserModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", dataType = "Long")
    private Long deptId;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", dataType = "String")
    private String userName;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", dataType = "String")
    private String nickName;
    /**
     * 用户类型（00系统用户）
     */
    @ApiModelProperty(value = "用户类型（00系统用户）", dataType = "String")
    private String userType;
    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱", dataType = "String")
    private String email;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty(value = "用户性别（0男 1女 2未知）", dataType = "String")
    private String sex;
    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址", dataType = "String")
    private String avatar;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;
    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "帐号状态（0正常 1停用）", dataType = "String")
    private String status;
    /**
     * 最后登录IP
     */
    @ApiModelProperty(value = "最后登录IP", dataType = "String")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间", dataType = "Date")
    private Date loginDate;

}
