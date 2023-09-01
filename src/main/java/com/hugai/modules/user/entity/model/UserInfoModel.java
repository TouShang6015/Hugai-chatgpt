package com.hugai.modules.user.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 用户 Model
 *
 * @author WuHao
 * @date 2022-05-25 22:41:42
 * @tableName tb_user_info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_user_info")
@ApiModel(value = "UserInfoModel", description = "用户model")
public class UserInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录名", dataType = "String")
    private String userName;

    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;

    @ApiModelProperty(value = "昵称", dataType = "String")
    private String nickName;

    @ApiModelProperty(value = "性别", dataType = "String")
    private String sex;

    @ApiModelProperty(value = "头像", dataType = "String")
    private String imgUrl;

    @ApiModelProperty(value = "状态", dataType = "String")
    private String status;

    @ApiModelProperty(value = "是否为游客")
    private String ifTourist;

    @ApiModelProperty(value = "ip地址")
    private String ipaddress;

    @ApiModelProperty(value = "ip归属地")
    private String ipLocation;

    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "qq号")
    private String qqNumber;
    @ApiModelProperty(value = "桌面背景")
    private String deskImgUrl;

}
