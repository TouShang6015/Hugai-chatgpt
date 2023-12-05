package com.hugai.core.dataPool.factory;

import cn.hutool.core.lang.Assert;
import com.hugai.common.modules.entity.config.model.ChatKeysModel;
import com.hugai.core.dataPool.DataRuleEnum;
import com.hugai.core.dataPool.model.DataElement;
import com.hugai.core.dataPool.rules.RuleRandom;
import com.org.bebas.exception.BusinessException;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * chat key池获取key
 *
 * @author WuHao
 * @since 2023/11/29 15:31
 */
public class DataFactoryChatKey {

    /**
     * @param ruleKey
     * @param chatKeysModelList
     * @return
     */
    public static ChatKeysModel poolGetKey(String ruleKey, List<ChatKeysModel> chatKeysModelList) {
        Assert.notNull(ruleKey, () -> new BusinessException("获取SDK秘钥失败，请指定规则"));

        Supplier<List<DataElement>> convertDataElement = () -> chatKeysModelList.stream().map(item -> {
            DataElement dataElement = new DataElement();
            dataElement.setValue(item.getApiToken());
            dataElement.setWeightValue(item.getWeightValue());
            dataElement.setOriginalElement(item);
            return dataElement;
        }).collect(Collectors.toList());

        if (DataRuleEnum.random.name().toUpperCase(Locale.ROOT).equals(ruleKey.toUpperCase(Locale.ROOT))) {
            return (ChatKeysModel) new RuleRandom(convertDataElement).getElement().getOriginalElement();
        }

        return (ChatKeysModel) new RuleRandom(convertDataElement).getElement().getOriginalElement();
    }

}
