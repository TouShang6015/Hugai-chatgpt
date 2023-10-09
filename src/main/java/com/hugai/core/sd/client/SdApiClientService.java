package com.hugai.core.sd.client;

import com.hugai.common.utils.okhttp.OkhttpClientUtil;
import com.hugai.core.sd.client.api.Api;
import com.hugai.core.sd.entity.request.Img2ImgRequest;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import com.hugai.core.sd.entity.response.Img2ImgResponse;
import com.hugai.core.sd.entity.response.TxtImgResponse;
import lombok.extern.slf4j.Slf4j;

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

    /**
     * 文生图api
     *
     * @param request
     * @return
     */
    public TxtImgResponse txt2img(TxtImgRequest request) {
        return OkhttpClientUtil.execute(api.txt2img(request));
    }

    /**
     * 图生图api
     *
     * @param request
     * @return
     */
    public Img2ImgResponse img2img(Img2ImgRequest request) {
        return OkhttpClientUtil.execute(api.img2img(request));
    }
}
