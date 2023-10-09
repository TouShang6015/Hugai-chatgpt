package com.hugai.modules.chat.service;

import com.hugai.core.openai.entity.request.OpenaiTxt2ImgRequest;
import com.hugai.core.openai.entity.request.OpenaiImg2ImgRequest;
import com.theokanning.openai.image.ImageResult;

/**
 * @author WuHao
 * @since 2023/7/17 9:37
 */
public interface DrawOpenaiService {

    /**
     * ai绘图消息发送
     *
     * @param param
     */
    ImageResult sendDrawCreatedOpenAi(OpenaiTxt2ImgRequest param);

    /**
     * ai绘图 图像编辑
     *
     * @param param
     */
    ImageResult sendDrawEditOpenAi(OpenaiImg2ImgRequest param);
}
