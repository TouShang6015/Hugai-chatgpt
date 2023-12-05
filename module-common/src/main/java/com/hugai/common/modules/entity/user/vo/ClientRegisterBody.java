package com.hugai.common.modules.entity.user.vo;

import com.hugai.common.modules.entity.user.valid.GroupRegisterByEmail;
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

    @Email(message = "请输入正确的邮箱格式", groups = {GroupRegisterByEmail.class})
    private String email;

    @NotNull(message = "验证码不能为空！", groups = {GroupRegisterByEmail.class})
    @Length(max = 6, message = "验证码有误", groups = {GroupRegisterByEmail.class})
    private String code;

    @Length(min = 6, max = 26, message = "登陆密码长度请设置在6-26之间", groups = {GroupRegisterByEmail.class})
    @NotNull(message = "登陆密码不能为空！", groups = {GroupRegisterByEmail.class})
    private String password;

    @NotNull(message = "确认登陆密码不能为空！", groups = {GroupRegisterByEmail.class})
    private String password2;

    /**
     * 推广码
     */
    private String promoCode;

}
