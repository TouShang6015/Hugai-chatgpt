package com.hugai.framework.sensitiveWord.strategy;

import com.hugai.framework.sensitiveWord.constants.SenWordFilterType;

/**
 * @author WuHao
 * @since 2023/7/28 15:42
 */
public interface SensitiveWordStrategy {

    String commonErrorText = "维护社区网络环境，请不要出现带有敏感政治、暴力倾向、不健康色彩的内容嗷~~";

    SenWordFilterType type();

    /**
     * 默认的替换方法
     *
     * @param value
     * @return
     */
    Object defaultReplaceValue(Object value,String replaceValue);

}
