package com.hugai.core.midjourney.valid;

import cn.hutool.core.collection.CollUtil;
import com.hugai.core.midjourney.client.DiscordSocketClient;
import com.hugai.core.midjourney.common.entity.DiscordAccount;
import com.hugai.core.midjourney.valid.annotation.NotSocketConnect;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * @author WuHao
 * @since 2023/10/10 15:39
 */
public class NotSocketContentValidator implements ConstraintValidator<NotSocketConnect, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Set<DiscordAccount> discordAccountList = DiscordSocketClient.getDiscordAccountList();
        if (CollUtil.isEmpty(discordAccountList) || discordAccountList.size() == 0){
            return false;
        }
        return true;
    }
}
