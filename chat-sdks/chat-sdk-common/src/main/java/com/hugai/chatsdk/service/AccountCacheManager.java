package com.hugai.chatsdk.service;

/**
 * @author WuHao
 * @since 2023/11/29 17:01
 */
public interface AccountCacheManager {

    /**
     * 清除缓存
     *
     * @param key
     */
    void cacheRemove(String key);

    void cacheAll();

}
