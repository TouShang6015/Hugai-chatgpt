package com.hugai.core.openai.api.impl;

import com.alibaba.fastjson2.JSON;
import com.hugai.core.openai.api.ImageOpenApi;
import com.hugai.core.openai.entity.response.api.ImageResponse;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.service.OpenAiService;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author WuHao
 * @since 2023/6/1 15:24
 */
@Slf4j
@Service
public class ImageOpenApiImpl implements ImageOpenApi {

    /**
     * 创建图片
     *
     * @param requestSupplier
     * @param sse
     * @return
     */
    @Override
    public ImageResponse createImage(Supplier<CreateImageRequest> requestSupplier, SseEmitter sse) {
        OpenAiService service = AiServiceFactory.createService();

        CreateImageRequest request = requestSupplier.get();

        log.info("[ImageOpenApi] createImage 请求参数：{}", JSON.toJSONString(request));

        ImageResult imageResult = Optional.ofNullable(service.createImage(request)).orElseGet(ImageResult::new);

        log.info("[ImageOpenApi] createImage 响应结果：{}", JSON.toJSONString(imageResult));

        try {
            sse.send(imageResult.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageResponse response = ImageResponse.builder()
                .created(imageResult.getCreated())
                .data(imageResult.getData())
                .build();
        response.setBody(JSON.toJSONString(imageResult));

        log.info("[ImageOpenApi] createImage 最终响应结果处理：{}", JSON.toJSONString(response));
        return response;
    }
}
