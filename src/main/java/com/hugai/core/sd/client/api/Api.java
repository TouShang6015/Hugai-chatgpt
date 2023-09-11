package com.hugai.core.sd.client.api;

import com.hugai.core.sd.entity.request.Img2ImgRequest;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import com.hugai.core.sd.entity.response.Img2ImgResponse;
import com.hugai.core.sd.entity.response.TxtImgResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author WuHao
 * @since 2023/9/11 10:38
 */
public interface Api {

    @POST("/sdapi/v1/txt2img")
    Single<TxtImgResponse> txt2img(@Body TxtImgRequest request);

    @POST("/sdapi/v1/img2img")
    Single<Img2ImgResponse> img2img(@Body Img2ImgRequest request);

}
