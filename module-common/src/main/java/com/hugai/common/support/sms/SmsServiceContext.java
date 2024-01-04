package com.hugai.common.support.sms;

import com.hugai.common.support.sms.service.SmsSendService;
import com.hugai.common.support.strategy.StrategyServiceContext;
import com.org.bebas.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @author WuHao
 * @since 2023/12/7 10:51
 */
@Component
public class SmsServiceContext extends StrategyServiceContext<SmsSendService> {

    @Autowired
    public SmsServiceContext(List<SmsSendService> smsSendServices) {
        super(smsSendServices);
    }

    public SmsSendService getService(String uniqueKey) {
        return super.getService(uniqueKey, () -> new BusinessException("没有找到短信服务策略"));
    }

    @Override
    protected Function<SmsSendService, String> serviceKey() {
        return service -> service.type().name();
    }

}
