package com.hugai.chatsdk.common.manager.accessToken;

import cn.hutool.core.util.StrUtil;
import com.hugai.chatsdk.common.manager.accessToken.entity.AccessTokenBean;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;

import java.util.Date;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2024/1/12 9:24
 */
public abstract class AccessTokenManager {

    public final static String CACHE_KEY = "ACCESS_TOKEN:";

    protected RedisUtil redisUtil;

    public AccessTokenManager() {
        this.redisUtil = SpringUtils.getBean(RedisUtil.class);
    }

    public String buildCacheKey(String uniqueKey) {
        if (StrUtil.isEmpty(uniqueKey))
            throw new BusinessException("唯一键不能为空");
        return CACHE_KEY + uniqueKey;
    }

    public AccessTokenBean getAccessToken(String uniqueKey) {
        String key = this.buildCacheKey(uniqueKey);
        AccessTokenBean accessTokenBean = redisUtil.getCacheObject(key);
        if (Objects.isNull(accessTokenBean)) {
            accessTokenBean = this.clientGetAccessToken(uniqueKey);
        }
        if (Objects.nonNull(accessTokenBean)) {
            long nowTime = new Date().getTime();
            long expiresTime = accessTokenBean.getExpiresTime();
            long refreshTime = accessTokenBean.getRefreshTime();
            if (nowTime >= refreshTime || nowTime >= expiresTime) {
                accessTokenBean = this.clientGetAccessToken(uniqueKey);
            }
        }

        return accessTokenBean;
    }

    public String getAccessTokenString(String uniqueKey) {
        AccessTokenBean accessToken = this.getAccessToken(uniqueKey);
        if (Objects.isNull(accessToken))
            return null;
        return accessToken.getAccessToken();
    }

    protected abstract AccessTokenBean clientGetAccessToken(String uniqueKey);

}
