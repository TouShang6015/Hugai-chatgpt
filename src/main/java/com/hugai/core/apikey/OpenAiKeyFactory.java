package com.hugai.core.apikey;

import com.hugai.core.apikey.rules.RuleRandom;

/**
 * @author WuHao
 * @since 2023/5/31 14:40
 */
public class OpenAiKeyFactory {

    /**
     * 获取apiKey 根据自定义规则
     *
     * @param rule
     * @return
     */
    public static String getKey(OneKeyRule rule) {
        return rule.getKey();
    }

    /**
     * 获取apiKey 默认规则 随机 {@link RuleRandom}
     *
     * @return
     */
    public static String getKey(Long userId) {
        return getKey(new RuleRandom(userId));
    }

}
