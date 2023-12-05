package com.hugai.modules.system.service;

import com.hugai.common.modules.entity.system.model.SysUserTokenModel;
import com.org.bebas.mapper.service.IService;

/**
 * 登录人token信息 业务接口
 *
 * @author WuHao
 * @date 2022-06-01 11:19:47
 */
public interface ISysUserTokenService extends IService<SysUserTokenModel> {

    /**
     * 根据tokenKey删除
     *
     * @param tokenKey
     * @return
     */
    boolean removeByTokenKey(String tokenKey);

}
