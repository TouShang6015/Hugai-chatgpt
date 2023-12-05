package com.hugai.common.modules.entity.draw.vo.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hugai.common.modules.entity.draw.valid.CreateTask;
import com.hugai.common.modules.entity.session.valid.SendDrawOpenAi;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author WuHao
 * @since 2023/7/17 9:31
 */
@Data
public class OpenaiImg2ImgRequest {

    /**
     * A text description of the desired image(s). The maximum length in 1000 characters.
     */
    @NotEmpty(message = "请输入内容", groups = {CreateTask.class})
    @Length(max = 1000, message = "内容长度不能超过1000个字符", groups = {CreateTask.class})
    String prompt;

    String mask;

    String image;

    @NotEmpty(message = "垫图不能为空", groups = {CreateTask.class})
    private String baseImg;

    /**
     * The number of images to generate. Must be between 1 and 10. Defaults to 1.
     */
    @Max(value = 1, message = "The number of images to generate. Must be between 1 and 1. Defaults to 1", groups = {CreateTask.class})
    @Min(value = 1, message = "The number of images to generate. Must be between 1 and 1. Defaults to 1", groups = {CreateTask.class})
    Integer n;

    /**
     * The size of the generated images. Must be one of "256x256", "512x512", or "1024x1024". Defaults to "1024x1024".
     */
    String size;

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json. Defaults to url.
     */
    @JsonProperty("response_format")
    String responseFormat;

    /**
     * A unique identifier representing your end-user, which will help OpenAI to monitor and detect abuse.
     */
    String user;

    @NotNull(message = "图像长度不能为空", groups = {SendDrawOpenAi.class})
    private Integer sizeWidth;
    @NotNull(message = "图像高度不能为空", groups = {SendDrawOpenAi.class})
    private Integer sizeHeight;

}
