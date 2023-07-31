package com.hugai.modules.system.entity.vo.auth;

import com.anji.captcha.model.vo.CaptchaVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 客户端登陆实体
 *
 * @author WuHao
 * @date 2022/5/31 14:19
 */
@Data
@ApiModel(value = "LoginBody", description = "用户登陆通用对象")
public class LoginBody {

    /**
     * 登录账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
    /**
     * 唯一标识
     */
    private String uuid;
    /**
     * 记住我
     */
    private Boolean rememberMe;

    private CaptchaVO captcha;

}
