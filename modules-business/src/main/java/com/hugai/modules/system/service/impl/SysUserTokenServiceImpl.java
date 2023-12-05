package com.hugai.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.modules.entity.system.model.SysUserTokenModel;
import com.hugai.modules.system.mapper.SysUserTokenMapper;
import com.hugai.modules.system.service.ISysUserTokenService;
import com.org.bebas.mapper.cache.ServiceImpl;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录人token信息 业务实现类
 *
 * @author WuHao
 * @date 2022-06-01 11:19:47
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserTokenModel> implements ISysUserTokenService {

    /**
     * 根据tokenKey删除
     *
     * @param tokenKey
     * @return
     */
    @Override
    public boolean removeByTokenKey(String tokenKey) {
        List<SysUserTokenModel> list = this.listByParam(SysUserTokenModel.builder().token(tokenKey).build());
        if (CollUtil.isEmpty(list))
            return true;
        return this.removeById(list.get(NumberUtils.INTEGER_ZERO).getId());
    }

}
