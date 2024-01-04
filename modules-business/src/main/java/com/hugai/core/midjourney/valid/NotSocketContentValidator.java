package com.hugai.core.midjourney.valid;

import cn.hutool.core.collection.CollUtil;
import com.hugai.core.midjourney.pool.DiscordAccountCacheObj;
import com.hugai.core.midjourney.pool.DiscordSocketAccountPool;
import com.hugai.core.midjourney.valid.annotation.NotSocketConnect;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WuHao
 * @since 2023/10/10 15:39
 */
public class NotSocketContentValidator implements ConstraintValidator<NotSocketConnect, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        ConcurrentHashMap<String, DiscordAccountCacheObj> cache = DiscordSocketAccountPool.CACHE;
        if (CollUtil.isEmpty(cache) || cache.size() == 0) {
            return false;
        }
        return true;
    }
}
