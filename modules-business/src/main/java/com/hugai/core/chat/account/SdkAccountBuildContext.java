package com.hugai.core.chat.account;

import com.hugai.common.support.strategy.StrategyServiceContext;
import com.hugai.core.chat.account.service.SdkAccountBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @author WuHao
 * @since 2023/11/30 18:38
 */
@Component
public class SdkAccountBuildContext extends StrategyServiceContext<SdkAccountBuildService> {

    @Autowired
    public SdkAccountBuildContext(List<SdkAccountBuildService> sdkAccountBuildServices) {
        super(sdkAccountBuildServices);
    }


    @Override
    protected Function<SdkAccountBuildService, String> serviceKey() {
        return s -> s.type().getKey();
    }
}
