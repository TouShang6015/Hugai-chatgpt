package com.hugai.framework.file.plugins.impl;

import com.hugai.framework.file.constants.FileStrategyEnum;
import com.hugai.framework.file.plugins.AbstractPluginsImpl;
import org.springframework.stereotype.Service;

/**
 * 插件 win 策略实现
 *
 * @author wuhao
 * @date 2022/9/23 10:21
 */
@Service
public class ServerPluginsImpl extends AbstractPluginsImpl {

    /**
     * 策略标识
     *
     * @return
     */
    @Override
    public FileStrategyEnum strategy() {
        return FileStrategyEnum.server;
    }

}
