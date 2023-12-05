package com.hugai.framework.captcha.service;

import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.org.bebas.core.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author WuHao
 * @since 2023/7/24 11:32
 */
@Service
public class CaptchaCacheRedisServiceImpl implements CaptchaCacheService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void set(String key, String value, long expiresInSeconds) {
        redisUtil.setCacheObject(key, value, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        return redisUtil.redisTemplate.hasKey(key);
    }

    @Override
    public void delete(String key) {
        redisUtil.deleteObject(key);
    }

    @Override
    public String get(String key) {
        return redisUtil.getCacheObject(key);
    }

    /**
     * 缓存类型-local/redis/memcache/..
     * 通过java SPI机制，接入方可自定义实现类
     *
     * @return
     */
    @Override
    public String type() {
        return AjCaptchaProperties.StorageType.redis.name();
    }

    /***
     *
     * @param key
     * @param val
     * @return
     */
    @Override
    public Long increment(String key, long val) {
        return CaptchaCacheService.super.increment(key, val);
    }
}
