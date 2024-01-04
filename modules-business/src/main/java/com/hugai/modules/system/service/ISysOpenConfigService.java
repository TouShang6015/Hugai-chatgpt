package com.hugai.modules.system.service;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.modules.entity.system.model.SysOpenConfigModel;
import com.hugai.common.webApi.openCnofig.OpenConfigWebApi;
import com.org.bebas.mapper.service.IService;

/**
 * 第三方配置 业务接口
 *
 * @author wuhao
 * @date 2023-12-20
 */
public interface ISysOpenConfigService extends IService<SysOpenConfigModel>, OpenConfigWebApi {

    @Override
    default SysOpenConfigModel queryOpenConfigByUniqueKey(String key) {
        if (StrUtil.isEmpty(key))
            return null;
        return this.lambdaQuery().eq(SysOpenConfigModel::getUniqueKey, key).one();
    }
}
