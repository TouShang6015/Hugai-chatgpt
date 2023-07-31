package com.hugai.core.openai.api;

import com.hugai.core.openai.entity.response.api.ImageResponse;
import com.theokanning.openai.image.CreateImageRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author WuHao
 * @since 2023/6/1 15:23
 */
public interface ImageOpenApi {

    /**
     * 创建图片
     *
     * @param requestSupplier
     * @param sse
     * @return
     */
    ImageResponse createImage(Supplier<CreateImageRequest> requestSupplier, SseEmitter sse);

    /**
     * 创建图片
     *
     * @param content
     * @param sse
     * @return
     */
    default ImageResponse createImage(String content, SseEmitter sse) {
        return this.createImage(() ->
                        CreateImageRequest.builder()
                                .prompt(content)
                                .build()
                , sse);
    }

    /**
     * 创建图片
     *
     * @param requestConsumer
     * @param sse
     * @return
     */
    default ImageResponse createImage(Consumer<CreateImageRequest> requestConsumer, SseEmitter sse) {
        CreateImageRequest request = new CreateImageRequest();
        requestConsumer.accept(request);
        return this.createImage(() -> request, sse);
    }

    /**
     * 创建图片（小气版）
     *
     * @param content
     * @param sse
     * @return
     */
    default ImageResponse createImageMini(String content, SseEmitter sse) {
        return this.createImage(() ->
                        CreateImageRequest.builder()
                                .prompt(content)
                                .n(1)
                                .size("256x256")
                                .build()
                , sse);
    }

}
