package com.hugai.core.drawTask.strategy;

import com.hugai.modules.draw.entity.vo.DrawPersistenceCollection;

/**
 * 绘图业务接口
 *
 * @author WuHao
 * @since 2023/9/8 13:24
 */
public interface DrawApiService extends DrawStrategySign {

    /**
     * 请求外部接口返回持久化集合实体
     *
     * @return
     */
    DrawPersistenceCollection executeApi();

}
