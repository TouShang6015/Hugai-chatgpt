package com.hugai.modules.user.service.login;

import com.hugai.modules.system.entity.vo.auth.ClientRegisterBody;
import com.hugai.modules.system.entity.vo.auth.RegisterBody;
import com.org.bebas.utils.result.Result;

import javax.validation.Valid;

/**
 * @author WuHao
 * @date 2022/5/29 16:26
 */
public interface UserRegisterService {

    /**
     * 注册接口
     * <p>
     * userName -- 必填
     * nickName -- 必填
     * password -- 密码
     * </p>
     *
     * @param param
     * @return
     */
    Result doRegister(ClientRegisterBody param);

}
