package com.hugai.core.sd.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/9/11 10:48
 */
@NoArgsConstructor
@Data
public class Img2ImgResponse {
    @JsonProperty("images")
    private List<String> images;

    @JsonProperty("parameters")
    private TxtImgRequest parameters;

    @JsonProperty("info")
    private String info;
}
