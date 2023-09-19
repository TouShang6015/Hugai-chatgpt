package com.hugai.modules.chat.service;

import com.hugai.core.drawTask.entity.SessionDrawCreatedOpenaiCacheData;
import com.hugai.core.drawTask.entity.SessionDrawEditOpenaiCacheData;
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
    ImageResult sendDrawCreatedOpenAi(SessionDrawCreatedOpenaiCacheData param);

    /**
     * ai绘图 图像编辑
     *
     * @param param
     */
    ImageResult sendDrawEditOpenAi(SessionDrawEditOpenaiCacheData param);
}
