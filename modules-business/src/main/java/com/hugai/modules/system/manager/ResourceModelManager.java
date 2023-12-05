package com.hugai.modules.system.manager;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.modules.entity.system.model.BaseResourceConfigModel;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统配置缓存管理
 *
 * @author WuHao
 * @since 2023/10/10 17:02
 */
public class ResourceModelManager {

    private static final ConcurrentHashMap<String, BaseResourceConfigModel> CACHE_MAP = new ConcurrentHashMap<>();

    public static void add(String key, BaseResourceConfigModel model) {
        if (StrUtil.isEmpty(key) || Objects.isNull(model))
            return;
        CACHE_MAP.put(key, model);
    }

    public static BaseResourceConfigModel get(String key) {
        if (StrUtil.isEmpty(key))
            return null;
        return CACHE_MAP.get(key);
    }

    public static void remove(String key) {
        if (StrUtil.isEmpty(key))
            return;
        CACHE_MAP.remove(key);
    }

    public static void removeAll() {
        CACHE_MAP.keySet().forEach(CACHE_MAP::remove);
    }

}
