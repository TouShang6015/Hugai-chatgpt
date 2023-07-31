package com.hugai.modules.user.service;

import com.alibaba.fastjson2.JSONObject;
import com.hugai.core.openai.entity.response.UserAccountResponse;
import com.hugai.modules.user.entity.model.UserInfoModel;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/6/5 10:23
 */
public interface UserInfoService extends IService<UserInfoModel> {

    UserInfoModel selectUserByUserName(String username);

    UserInfoModel selectUserByeEmail(String email);

    UserInfoModel selectByIpaddress(String ipaddress);

    /**
     * 获取账户api信息
     *
     * @return
     */
    List<UserAccountResponse> getUserGrants();

    /**
     * 注册发送短信验证码
     *
     * @param email
     */
    void registerSendMail(String email);
}
