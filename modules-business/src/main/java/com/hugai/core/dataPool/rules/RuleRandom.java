package com.hugai.core.dataPool.rules;

import com.hugai.core.dataPool.model.DataElement;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author WuHao
 * @since 2023/11/29 15:22
 */
public class RuleRandom extends DataPoolRuleCommon {

    public RuleRandom(Supplier<List<DataElement>> getDataElementFunction) {
        super(getDataElementFunction);
    }

    @Override
    public void execute() {
        Random random = new Random();
        if (totalWeight == 0) {
            this.e = this.dataElements.get(random.nextInt(super.dataElements.size()));
        } else {
            int randomValue = random.nextInt(totalWeight);
            for (int i = 0; i < cumulativeWeights.size(); i++) {
                if (randomValue < cumulativeWeights.get(i)) {
                    this.e = super.dataElements.get(i);
                    return;
                }
            }
        }
    }

}
