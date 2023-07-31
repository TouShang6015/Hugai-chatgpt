package com.hugai.core.session.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author WuHao
 * @since 2023/7/17 9:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SessionDrawCreatedOpenaiCacheData extends SessionCacheData{


    /**
     * A text description of the desired image(s). The maximum length in 1000 characters.
     */
    @NotEmpty(message = "请输入内容")
    @Length(max = 1000, message = "内容长度不能超过1000个字符")
    String prompt;

    /**
     * The number of images to generate. Must be between 1 and 10. Defaults to 1.
     */
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

}
