package com.hugai.core.midjourney.common.entity;

import lombok.Data;
import net.dv8tion.jda.api.utils.data.DataObject;
import reactor.util.function.Tuple2;

import java.util.List;

/**
 * 账户信息
 *
 * @author WuHao
 * @since 2023/9/25 10:00
 */
@Data
public class DiscordAccount {

    private String userName;
    /**
     * token
     */
    private String userToken;
    /**
     * ua
     */
    private String userAgent;
    /**
     * dataObject
     */
    private DataObject autoData;

    private List<String> channelIds;

    private List<Tuple2<String, String>> channelConfigList;

    public DiscordAccount(String userName, String userToken, String userAgent) {
        this.userName = userName;
        this.userToken = userToken;
        this.userAgent = userAgent;
    }

}
