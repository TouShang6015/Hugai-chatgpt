package com.hugai.modules.system.service;

import com.hugai.common.constants.ResourceConfigConstant;
import com.hugai.common.entity.baseResource.*;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.common.modules.entity.system.model.BaseResourceConfigModel;
import com.org.bebas.mapper.service.IService;

/**
 * 系统参数配置表 业务接口
 *
 * @author WuHao
 * @date 2022-05-21 09:02:14
 */
public interface IBaseResourceConfigService extends IService<BaseResourceConfigModel>, BaseResourceWebApi {

    /**
     * 根据configKey查询单个信息
     *
     * @param configKey
     * @return
     */
    BaseResourceConfigModel queryByConfigKey(String configKey);

    /**
     * 根据configKey获取configValue
     *
     * @param configKey
     * @return
     */
    String queryValueByConfigKey(String configKey);

    /**
     * 根据configKey获取configValue
     *
     * @param configKey
     * @param tClass
     * @param cache     是否使用缓存
     * @param <T>
     * @return
     */
    <T> T queryValueByConfigKey(String configKey, Class<T> tClass, Boolean cache);

    default <T> T queryValueByConfigKey(String configKey, Class<T> tClass) {
        return queryValueByConfigKey(configKey, tClass, true);
    }

    /**
     * ResourceMainVO 配置
     *
     * @return
     */
    default ResourceMainVO getResourceMain() {
        return queryValueByConfigKey(ResourceConfigConstant.MAIN_KEY, ResourceMainVO.class, true);
    }

    @Override
    default ResourceChatConfigVO getResourceChatConfigVO() {
        return queryValueByConfigKey(ResourceConfigConstant.CHAT_CONFIG, ResourceChatConfigVO.class, true);
    }

    /**
     * ResourceDrawVO 配置
     *
     * @return
     */
    default ResourceDrawVO getResourceDraw() {
        return queryValueByConfigKey(ResourceConfigConstant.DRAW, ResourceDrawVO.class, true);
    }

    /**
     * 根据configKey编辑configValue
     *
     * @param param
     * @return
     */
    boolean editByConfigKey(BaseResourceConfigModel param);

}
