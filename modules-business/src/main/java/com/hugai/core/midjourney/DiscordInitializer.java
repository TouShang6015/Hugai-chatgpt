package com.hugai.core.midjourney;

import cn.hutool.core.collection.CollUtil;
import com.hugai.common.enums.flow.AccountStatus;
import com.hugai.common.modules.entity.config.vo.CmjAccountDetailVO;
import com.hugai.core.midjourney.client.DiscordSocketClient;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.modules.config.service.ICmjAccountService;
import com.org.bebas.core.function.OR;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化连接
 *
 * @author WuHao
 * @since https://github.com/novicezk/midjourney-proxy
 */
@Component
public class DiscordInitializer implements ApplicationRunner {

    @Resource
    private ICmjAccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        accountService.flushCache();
        OR.run(accountService.getAccountAll(), CollUtil::isNotEmpty, accountAll -> {
            for (CmjAccountDetailVO accountVo : accountAll) {
                if (AccountStatus.NORMAL.getKey().equals(accountVo.getAccountStatus())){
                    DiscordAccount discordAccount = DiscordSocketClient.buildAccount(accountVo);
                    // 建立discord ws连接
                    DiscordSocketClient.connection(discordAccount);
                }
            }
        });
    }

}
