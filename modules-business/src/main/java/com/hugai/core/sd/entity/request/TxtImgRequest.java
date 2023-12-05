package com.hugai.core.sd.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author WuHao
 * @since 2023/9/7 17:17
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class TxtImgRequest extends ImgBaseRequest {

    @JsonProperty("enable_hr")
    private Boolean enableHr;
    @JsonProperty("firstphase_width")
    private Integer firstphaseWidth;
    @JsonProperty("firstphase_height")
    private Integer firstphaseHeight;
    @JsonProperty("hr_upscaler")
    private String hrUpscaler;
    /**
     * 高清修复放大倍数
     */
    @JsonProperty("hr_scale")
    private Double hrScale;
    @JsonProperty("hr_second_pass_steps")
    private Integer hrSecondPassSteps;
    @JsonProperty("hr_resize_x")
    private Integer hrResizeX;
    @JsonProperty("hr_resize_y")
    private Integer hrResizeY;
    @JsonProperty("hr_checkpoint_name")
    private String hrCheckpointName;
    @JsonProperty("hr_sampler_name")
    private String hrSamplerName;
    @JsonProperty("hr_prompt")
    private String hrPrompt;
    @JsonProperty("hr_negative_prompt")
    private String hrNegativePrompt;

}
