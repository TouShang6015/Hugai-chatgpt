package com.hugai.core.apikey;

import com.hugai.core.apikey.rules.RuleRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WuHao
 * @since 2023/5/31 14:40
 */
@Slf4j
public class OpenAiKeyFactory {

    /**
     * 获取apiKey 根据自定义规则
     *
     * @param rule
     * @return
     */
    public static String getKey(OneKeyRule rule) {
        String key = rule.getKey();
        log.info("正在使用的OpenAi Key:{}", key);
        return key;
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
