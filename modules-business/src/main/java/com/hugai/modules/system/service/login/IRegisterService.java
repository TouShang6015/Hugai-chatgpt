package com.hugai.modules.system.service.login;

import com.hugai.common.modules.entity.system.vo.auth.RegisterBody;
import com.org.bebas.utils.result.Result;

import javax.validation.Valid;

/**
 * @author WuHao
 * @date 2022/5/29 16:26
 */
public interface IRegisterService {

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
    Result doRegister(@Valid RegisterBody param);

}
