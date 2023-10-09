package com.hugai.common.utils.okhttp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.org.bebas.exception.BusinessException;
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
 * @since 2023/9/25 13:52
 */
@Slf4j
public class OkhttpClientUtil {

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper;
    }

    public static Retrofit defaultRetrofit(OkHttpClient client, String hostUrl) {
        return new Retrofit.Builder()
                .baseUrl(hostUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(defaultObjectMapper()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 执行器
     *
     * @param apiCall
     * @param <T>
     * @return
     */
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
                throw new RuntimeException("远程调用失败");
            } catch (IOException ex) {
                ex.printStackTrace();
                throw e;
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("远程调用失败");
        }
    }

}
