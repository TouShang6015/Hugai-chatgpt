package com.hugai.modules.user.service.login;


import com.hugai.core.security.context.bean.LoginUserContextBean;
import com.hugai.modules.system.entity.vo.auth.LoginBody;

/**
 * 登陆业务接口
 *
 * @author WuHao
 * @date 2022/5/31 17:46
 */
public interface UserLoginService {

    /**
     * 获取最大登陆人数
     *
     * @return
     */
    Integer getMaxLogin();

    /**
     * 登陆逻辑
     *
     * @param loginPcRequest
     * @return
     */
    LoginUserContextBean doLogin(LoginBody loginPcRequest);

    /**
     * 通过用户上下文对象获取token
     *
     * @param loginUserContextBean
     * @return
     */
    String tokenByContext(LoginUserContextBean loginUserContextBean);

    /**
     * 游客登陆
     *
     * @return
     */
    LoginUserContextBean touristLogin();
}
