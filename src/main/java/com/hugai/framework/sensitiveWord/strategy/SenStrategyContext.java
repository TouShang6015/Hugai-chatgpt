package com.hugai.framework.sensitiveWord.strategy;

import cn.hutool.core.collection.CollUtil;
import com.hugai.framework.sensitiveWord.constants.SenWordFilterType;
import com.hugai.framework.sensitiveWord.strategy.impl.SenFilterErrorImpl;
import com.hugai.framework.sensitiveWord.strategy.impl.SenFilterNonImpl;
import com.hugai.framework.sensitiveWord.strategy.impl.SenFilterReplaceImpl;
import com.org.bebas.exception.BusinessException;

import java.util.List;

/**
 * 策略器上下文处理
 *
 * @author WuHao
 * @since 2023/7/28 15:44
 */
public class SenStrategyContext {

    private List<SensitiveWordStrategy> strategyList;

    SensitiveWordStrategy strategy;

    private SenStrategyContext() {
        this.strategyList = CollUtil.newArrayList(
                new SenFilterErrorImpl(),
                new SenFilterNonImpl(),
                new SenFilterReplaceImpl()
        );
    }

    /**
     * 获取业务实现
     *
     * @param type
     * @return
     */
    public static SensitiveWordStrategy getStrategyService(SenWordFilterType type) {
        SenStrategyContext service = new SenStrategyContext();
        return service.strategy = service.strategyList.stream()
                .filter(item -> item.type().equals(type))
                .findFirst()
                .orElseThrow(() -> new BusinessException("敏感词过滤策略器上下文类处理失败，未找到匹配的过滤类型"));
    }

}
