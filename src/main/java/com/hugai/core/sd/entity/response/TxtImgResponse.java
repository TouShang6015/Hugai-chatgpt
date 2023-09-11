package com.hugai.core.sd.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * sd 文生图响应
 *
 * @author WuHao
 * @since 2023/9/10 16:19
 */
@NoArgsConstructor
@Data
public class TxtImgResponse {
    @JsonProperty("images")
    private List<String> images;

    @JsonProperty("parameters")
    private TxtImgRequest parameters;

    @JsonProperty("info")
    private String info;
}
