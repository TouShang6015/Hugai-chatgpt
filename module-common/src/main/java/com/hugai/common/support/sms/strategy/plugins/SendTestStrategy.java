package com.hugai.common.support.sms.strategy.plugins;

import com.hugai.common.support.sms.entity.SmsBaseParam;
import com.hugai.common.support.sms.enums.SmsStrategy;
import com.hugai.common.support.sms.strategy.SendServiceCommon;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.common.webApi.log.SysLogSmsWebApi;
import com.org.bebas.core.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

/**
 * @author WuHao
 * @since 2023/12/7 10:22
 */
@Component
public class SendTestStrategy extends SendServiceCommon {

    @Autowired
    public SendTestStrategy(RedisUtil redisUtils, HashOperations<String, Object, SmsBaseParam> hashOperations, SysLogSmsWebApi logSmsWebApi, BaseResourceWebApi resourceConfigWebApi) {
        super(redisUtils, hashOperations, logSmsWebApi, resourceConfigWebApi);
    }

    @Override
    protected void sendExecute(SmsBaseParam param) {
        log.info("[短信发送 - 测试短信] uniqueKey: {},text: {}", param.getUniqueKey(), this.buildSendCodeText(param.getCode()));
    }

    @Override
    public SmsStrategy type() {
        return SmsStrategy.test;
    }

}
