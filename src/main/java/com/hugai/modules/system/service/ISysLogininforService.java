package com.hugai.modules.system.service;

import com.hugai.common.enums.LoginTypeEnum;
import com.hugai.modules.system.entity.model.SysLogininforModel;
import com.org.bebas.mapper.service.IService;

/**
 * 系统访问记录 业务接口
 *
 * @author WuHao
 * @date 2022-05-25 08:51:34
 */
public interface ISysLogininforService extends IService<SysLogininforModel> {

    /**
     * 登录成功
     */
    String LOGIN_SUCCESS = "Success";
    /**
     * 注销
     */
    String LOGOUT = "Logout";
    /**
     * 注册
     */
    String REGISTER = "Register";
    /**
     * 登录失败
     */
    String LOGIN_FAIL = "Error";

    void insertLoginLog(final String loginType, final String username, final String status, final String message);

    default void insertLoginLog(final String username, final String status, final String message) {
        this.insertLoginLog(LoginTypeEnum.ADMIN.getType(), username, status, message);
    }

    void clean();
}
