package com.hugai.core.drawTask.strategy;

import com.hugai.core.drawTask.enums.DrawType;

/**
 * 绘图策略器标识
 *
 * @author WuHao
 * @since 2023/9/8 13:12
 */
public interface DrawStrategySign {

    /**
     * 获取绘图api唯一标识
     *
     * @return
     */
    DrawType.ApiKey apiKey();

}
