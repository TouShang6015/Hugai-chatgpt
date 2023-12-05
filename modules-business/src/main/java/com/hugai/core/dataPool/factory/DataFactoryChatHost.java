package com.hugai.core.dataPool.factory;

import cn.hutool.core.lang.Assert;
import com.hugai.common.modules.entity.config.model.ChatSdkHostModel;
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
public class DataFactoryChatHost {

    /**
     * @param ruleKey
     * @param list
     * @return
     */
    public static ChatSdkHostModel poolGetKey(String ruleKey, List<ChatSdkHostModel> list) {
        Assert.notNull(ruleKey, () -> new BusinessException("获取SDK源失败，请指定规则"));

        Supplier<List<DataElement>> convertDataElement = () -> list.stream().map(item -> {
            DataElement dataElement = new DataElement();
            dataElement.setValue(item.getHostUrl());
            dataElement.setWeightValue(item.getWeightValue());
            dataElement.setOriginalElement(item);
            return dataElement;
        }).collect(Collectors.toList());

        if (DataRuleEnum.random.name().toUpperCase(Locale.ROOT).equals(ruleKey.toUpperCase(Locale.ROOT))) {
            return (ChatSdkHostModel) new RuleRandom(convertDataElement).getElement().getOriginalElement();
        }

        return (ChatSdkHostModel) new RuleRandom(convertDataElement).getElement().getOriginalElement();
    }

}
