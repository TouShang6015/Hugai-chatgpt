package com.hugai.modules.user.service;

import com.hugai.common.modules.entity.user.model.UserInfoModel;
import com.org.bebas.mapper.service.IService;

/**
 * @author WuHao
 * @since 2023/6/5 10:23
 */
public interface UserInfoService extends IService<UserInfoModel> {

    UserInfoModel selectUserByUserName(String username);

    UserInfoModel selectUserByeEmail(String email);

    UserInfoModel selectByIpaddress(String ipaddress);

    /**
     * 注册发送短信验证码
     *
     * @param email
     */
    void registerSendMail(String email);
}
