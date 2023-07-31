package com.hugai.framework.file.plugins;


import com.hugai.framework.file.constants.FileStrategyEnum;

/**
 * 插件接口
 *
 * @author WuHao
 * @since 2023/5/29 16:04
 */
public interface Plugins {

    /**
     * 策略标识
     *
     * @return
     */
    FileStrategyEnum strategy();

}
