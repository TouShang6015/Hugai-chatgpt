package com.hugai.modules.system.service;

import com.hugai.common.constants.ResourceConfigConstant;
import com.hugai.modules.system.entity.model.BaseResourceConfigModel;
import com.hugai.modules.system.entity.vo.baseResource.ResourceDrawVO;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.entity.vo.baseResource.ResourceOpenaiVO;
import com.org.bebas.mapper.service.IService;

/**
 * 系统参数配置表 业务接口
 *
 * @author WuHao
 * @date 2022-05-21 09:02:14
 */
public interface IBaseResourceConfigService extends IService<BaseResourceConfigModel> {

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

    /**
     * ResourceOpenaiVO 配置
     *
     * @return
     */
    default ResourceOpenaiVO getResourceOpenai() {
        return queryValueByConfigKey(ResourceConfigConstant.OPEN_AI, ResourceOpenaiVO.class, true);
    }

    /**
     * ResourceDrawVO 配置
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
