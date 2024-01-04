package com.hugai.common.webApi.openCnofig;


import com.hugai.common.modules.entity.system.model.SysOpenConfigModel;

/**
 * @author WuHao
 * @since 2023/12/22 9:17
 */
public interface OpenConfigWebApi {

    /**
     * 通过key获取单个数据
     *
     * @param key
     * @return
     */
    SysOpenConfigModel queryOpenConfigByUniqueKey(String key);


}
