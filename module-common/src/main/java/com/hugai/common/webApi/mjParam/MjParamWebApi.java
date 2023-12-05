package com.hugai.common.webApi.mjParam;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.enums.MjParamUnique;
import com.hugai.common.modules.entity.config.model.CmjParamModel;

import java.util.List;
import java.util.Objects;

/**
 * mj 参数配置webApi
 *
 * @author WuHao
 * @since 2023/11/10 10:55
 */
public interface MjParamWebApi {

    String CACHE_KEY = "midjourney-params-models";

    /**
     * 缓存获取全部列表记录
     *
     * @return
     */
    List<CmjParamModel> cacheGetAll();

    /**
     * 根据唯一标识获取model
     *
     * @param key
     * @return
     */
    CmjParamModel cacheGetByKey(String key);

    default CmjParamModel cacheGetByKey(MjParamUnique paramUnique) {
        if (Objects.isNull(paramUnique))
            return null;
        return this.cacheGetByKey(paramUnique.name());
    }

    /**
     * 根据唯一标识获取value值
     *
     * @param key
     * @return
     */
    default String cacheGetValueByKey(String key) {
        if (StrUtil.isEmpty(key))
            return null;
        CmjParamModel model = this.cacheGetByKey(key);
        if (Objects.isNull(model))
            return null;
        return model.getParamValue();
    }

    default String cacheGetValueByKey(MjParamUnique paramUnique) {
        if (Objects.isNull(paramUnique))
            return null;
        CmjParamModel model = this.cacheGetByKey(paramUnique);
        if (Objects.isNull(model))
            return null;
        return model.getParamValue();
    }

    /**
     * 清楚缓存
     */
    void cacheClear();


}
