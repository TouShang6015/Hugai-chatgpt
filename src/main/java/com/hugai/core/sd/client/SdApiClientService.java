package com.hugai.core.sd.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.hugai.core.sd.client.api.Api;
import com.hugai.core.sd.entity.request.Img2ImgRequest;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import com.hugai.core.sd.entity.response.Img2ImgResponse;
import com.hugai.core.sd.entity.response.TxtImgResponse;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.spring.SpringUtils;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

/**
 * @author WuHao
 * @since 2023/9/11 10:37
 */
@Slf4j
public class SdApiClientService {

    private final Api api;

    public SdApiClientService(Api api) {
        this.api = api;
    }

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper;
    }

    public static Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper) {
        String sdHostUrl = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceDraw().getSdHostUrl();
        return new Retrofit.Builder()
                .baseUrl(sdHostUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static <T> T execute(Single<T> apiCall) {
        try {
            return apiCall.blockingGet();
        } catch (HttpException e) {
            e.printStackTrace();
            try {
                if (e.response() == null || e.response().errorBody() == null) {
                    throw e;
                }
                String errorBody = e.response().errorBody().string();
                log.error(errorBody, e, e.code());
                throw new RuntimeException("sd远程调用失败");
            } catch (IOException ex) {
                throw e;
            }
        }
    }

    // =========================================== Api ===========================================

    /**
     * 文生图api
     *
     * @param request
     * @return
     */
    public TxtImgResponse txt2img(TxtImgRequest request) {
        return execute(api.txt2img(request));
    }

    /**
     * 图生图api
     *
     * @param request
     * @return
     */
    public Img2ImgResponse img2img(Img2ImgRequest request) {
        return execute(api.img2img(request));
    }
}
