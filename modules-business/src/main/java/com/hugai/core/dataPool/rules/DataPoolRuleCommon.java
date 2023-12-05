package com.hugai.core.dataPool.rules;

import cn.hutool.core.lang.Assert;
import com.hugai.core.dataPool.model.DataElement;
import com.hugai.core.dataPool.sign.DataExecute;
import com.org.bebas.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author WuHao
 * @since 2023/11/29 15:18
 */
public abstract class DataPoolRuleCommon implements DataExecute {

    protected List<DataElement> dataElements;

    protected DataElement e;

    protected List<Integer> cumulativeWeights = new ArrayList<>();

    protected int totalWeight = 0;

    public DataPoolRuleCommon(Supplier<List<DataElement>> getDataElementFunction) {
        this.dataElements = getDataElementFunction.get();

        for (DataElement item : this.dataElements) {
            this.totalWeight += item.getWeightValue();
            this.cumulativeWeights.add(totalWeight);
        }
    }

    @Override
    public DataElement getElement() {
        this.execute();
        Assert.notNull(this.e, () -> new BusinessException("数据池获取数据异常"));
        return this.e;
    }
}
