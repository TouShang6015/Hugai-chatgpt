package com.hugai.core.midjourney;

import com.hugai.core.midjourney.client.DiscordSocketClient;
import com.hugai.core.midjourney.common.constants.SocketClientParamConstants;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.common.modules.entity.config.model.CmjChannelConfigModel;
import com.hugai.common.modules.entity.config.vo.CmjAccountDetailVO;
import com.hugai.modules.config.service.ICmjAccountService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuples;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
        List<CmjAccountDetailVO> accountAll = accountService.getAccountAll();

        for (CmjAccountDetailVO accountVo : accountAll) {
            DiscordAccount discordAccount = new DiscordAccount(accountVo.getUserName(), accountVo.getUserToken(), accountVo.getUserAgent());
            List<CmjChannelConfigModel> channelConfigList = accountVo.getChannelConfigList();
            discordAccount.setChannelConfigList(
                    channelConfigList.stream().map(channelConfig -> Tuples.of(channelConfig.getGuildId(), channelConfig.getChannelId())).collect(Collectors.toList())
            );
            discordAccount.setChannelIds(accountVo.getChannelIds());
            discordAccount.setAutoData(SocketClientParamConstants.getDefaultAutoData(discordAccount));
            // 建立discord ws连接
            DiscordSocketClient.connection(discordAccount);
            DiscordSocketClient.getDiscordAccountList().add(discordAccount);
        }

    }

}
