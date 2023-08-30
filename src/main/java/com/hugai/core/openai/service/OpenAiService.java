package com.hugai.core.openai.service;

import com.hugai.core.openai.entity.response.UserAccountResponse;
import com.hugai.core.openai.model.account.Usage;
import com.hugai.core.openai.model.account.UserGrants;
import com.org.bebas.utils.futures.FutureUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * @author WuHao
 * @since 2023/6/1 14:23
 */
@Slf4j
public class OpenAiService extends com.theokanning.openai.service.OpenAiService {

    private String token;

    private String decryptToken;

    private final OpenAiApi customApi;

    public OpenAiService(OpenAiApi api, ExecutorService executorService) {
        super(api, executorService);
        this.customApi = api;
    }

    public OpenAiService(OpenAiApi api, ExecutorService executorService, String token) {
        super(api, executorService);
        this.customApi = api;
        this.token = token;
    }

    public OpenAiService(OpenAiApi api, ExecutorService executorService, String token,String decryptToken) {
        super(api, executorService);
        this.customApi = api;
        this.token = token;
        this.decryptToken = decryptToken;
    }

    /**
     * 获取用户账户信息
     *
     * @return
     */
    public UserAccountResponse getUserAccountInfo() {
        Tuple2<CompletableFuture<UserGrants>, CompletableFuture<Usage>> taskData = Tuples.of(
                FutureUtil.supplyAsync(() -> {
                    return new UserGrants();
//                    UserGrants userGrants = execute(customApi.getUserGrants());
//                    log.info("获取用户账户信息响应： {}", JSON.toJSONString(userGrants));
//                    return userGrants;
                }),
                FutureUtil.supplyAsync(() -> {
                    // todo 接口失效
                    return new Usage();
//                    Date nowDate = new Date();
//                    String startDate = DateUtil.format(DateUtils.addDays(nowDate, -90), DatePattern.NORM_DATE_PATTERN);
//                    String endDate = DateUtil.format(DateUtils.addDays(nowDate, 1), DatePattern.NORM_DATE_PATTERN);
//                    Usage userUsage = execute(customApi.getUserUsage(startDate, endDate));
//                    log.info("获取用户账户明细响应： {}", JSON.toJSONString(userUsage));
//                    return userUsage;
                })
        );

        FutureUtil.allOf(taskData.getT1(), taskData.getT2()).join();
        UserGrants userGrants = null;
        Usage usage = null;
        try {
            userGrants = taskData.getT1().get();
            usage = taskData.getT2().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        UserAccountResponse response = UserAccountResponse.builder()
                .usage(usage)
                .userGrants(userGrants)
                .build();

        return response;
    }

    public String getToken() {
        return token;
    }
    public String getDecryptToken(){
        return decryptToken;
    }
}
