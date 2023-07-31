package com.hugai.modules.system.entity.vo.auth;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 客户端注册请求体
 *
 * @author WuHao
 * @since 2023/7/25 13:27
 */
@Data
public class ClientRegisterBody {

    @NotNull(message = "登陆帐号不能为空！")
    @Length(max = 20, message = "登录账号长度不能超过20位")
    private String userName;

    @Email(message = "请输入正确的邮箱格式")
    private String email;

    @NotNull(message = "验证码不能为空！")
    @Length(max = 6, message = "验证码有误")
    private String code;

    @Length(min = 6, max = 26, message = "登陆密码长度请设置在6-26之间")
    @NotNull(message = "登陆密码不能为空！")
    private String password;

    @NotNull(message = "确认登陆密码不能为空！")
    private String password2;

}
