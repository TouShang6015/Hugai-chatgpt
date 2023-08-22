package com.hugai.core.apikey;

/**
 * @author WuHao
 * @since 2023/5/31 14:42
 */
public interface OneKeyRule {

    String getKey();

    String getKey(Long userId);

    void execute();

}
