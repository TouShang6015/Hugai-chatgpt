package com.hugai.chatsdk.baidu.oauth;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hugai.chatsdk.common.manager.accessToken.AccessTokenManager;
import com.hugai.chatsdk.common.manager.accessToken.entity.AccessTokenBean;
import com.hugai.common.modules.entity.config.model.ChatKeysModel;
import com.hugai.common.webApi.configChat.ChatKeysWebApi;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * @author WuHao
 * @since 2024/1/12 9:55
 */
@Slf4j
@Component
public class AccessTokenOauthBaidu extends AccessTokenManager {

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    @Resource
    private ChatKeysWebApi chatKeysWebApi;

    @Override
    protected AccessTokenBean clientGetAccessToken(String uniqueKey) {
        ChatKeysModel chatKeysModel = chatKeysWebApi.queryByApiToken(uniqueKey);
        String clientId = chatKeysModel.getApiToken();
        String clientSecret = chatKeysModel.getApiSecret();

        String url = "https://aip.baidubce.com/oauth/2.0/token?client_id=%s&client_secret=%s&grant_type=client_credentials";

        MediaType mediaType = MediaType.get("application/json");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url(String.format(url, clientId, clientSecret))
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = null;
        try {
            response = HTTP_CLIENT.newCall(request).execute();

            JSONObject jsonObject = JSON.parseObject(response.body().string());
            if (jsonObject.containsKey("error")) {
                log.error("[百度AccessToken鉴权请求] 响应失败：{}", jsonObject.getString("error_description"));
                throw new BusinessException(jsonObject.getString("error_description"));
            }
            log.error("[百度AccessToken鉴权请求] 响应成功：{}", jsonObject.toString());

            String access_token = jsonObject.getString("access_token");
            String refresh_token = jsonObject.getString("refresh_token");
            Long expires_in = jsonObject.getLong("expires_in");

            long nowTime = new Date().getTime();
            long expiresTime = nowTime + (expires_in * 1000);
            long refreshTime = nowTime + ((expires_in - (60 * 60 * 24)) * 1000);

            AccessTokenBean accessTokenBean = new AccessTokenBean();
            accessTokenBean.setUniqueKey(uniqueKey);
            accessTokenBean.setAccessToken(access_token);
            accessTokenBean.setRefreshToken(refresh_token);
            accessTokenBean.setExpiresTime(expiresTime);
            accessTokenBean.setRefreshTime(refreshTime);

            redisUtil.setCacheObject(this.buildCacheKey(uniqueKey), accessTokenBean);

            return accessTokenBean;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
