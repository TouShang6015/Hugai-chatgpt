package com.hugai.common.support.init;

/**
 * 初始化缓存接口
 * <p>实现该接口会在启动时调用</p>
 *
 * @author WuHao
 * @since 2022/5/21 21:08
 */
public interface InitCache {

    /**
     * 初始化缓存
     */
    void runInitCache();

}
