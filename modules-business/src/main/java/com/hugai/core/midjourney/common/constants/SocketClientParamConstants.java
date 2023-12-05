package com.hugai.core.midjourney.common.constants;

import com.hugai.core.midjourney.common.entity.DiscordAccount;
import eu.bitwalker.useragentutils.UserAgent;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WuHao
 * @since https://github.com/novicezk/midjourney-proxy
 */
public class SocketClientParamConstants {

    /**
     * 默认请求头
     *
     * @param discordAccount
     * @return
     */
    public static Map<String, String> getDefaultBrowserHeaders(DiscordAccount discordAccount) {
        Map<String, String> map = new HashMap<>();
        map.put("Accept-Encoding" , "gzip, deflate, br");
        map.put("Accept-Language" , "en-US,en;q=0.9");
        map.put("Cache-Control" , "no-cache");
        map.put("Pragma" , "no-cache");
        map.put("User-Agent" , discordAccount.getUserAgent());
        return map;
    }

    /**
     * 默认认证信息
     * @return
     */
    public static DataObject getDefaultAutoData(DiscordAccount discordAccount){
        UserAgent agent = UserAgent.parseUserAgentString(discordAccount.getUserAgent());
        DataObject connectionProperties = DataObject.empty()
                .put("browser", agent.getBrowser().getGroup().getName())
                .put("browser_user_agent", discordAccount.getUserAgent())
                .put("browser_version", agent.getBrowserVersion().toString())
                .put("client_build_number", 222963)
                .put("client_event_source", null)
                .put("device", "")
                .put("os", agent.getOperatingSystem().getName())
                .put("referer", "https://www.midjourney.com")
                .put("referrer_current", "")
                .put("referring_domain", "www.midjourney.com")
                .put("referring_domain_current", "")
                .put("release_channel", "stable")
                .put("system_locale", "zh-CN");
        DataObject presence = DataObject.empty()
                .put("activities", DataArray.empty())
                .put("afk", false)
                .put("since", 0)
                .put("status", "online");
        DataObject clientState = DataObject.empty()
                .put("api_code_version", 0)
                .put("guild_versions", DataObject.empty())
                .put("highest_last_message_id", "0")
                .put("private_channels_version", "0")
                .put("read_state_version", 0)
                .put("user_guild_settings_version", -1)
                .put("user_settings_version", -1);
        return DataObject.empty()
                .put("capabilities", 16381)
                .put("client_state", clientState)
                .put("compress", false)
                .put("presence", presence)
                .put("properties", connectionProperties)
                .put("token", discordAccount.getUserToken());
    }

}
