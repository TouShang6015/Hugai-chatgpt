package com.hugai.core.sd.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/9/7 17:20
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Img2ImgRequest extends ImgBaseRequest{

    @JsonProperty("init_images")
    private List<String> initImages;
    @JsonProperty("resize_mode")
    private Integer resizeMode;
    @JsonProperty("image_cfg_scale")
    private Integer imageCfgScale;
    @JsonProperty("mask")
    private String mask;
    @JsonProperty("mask_blur_x")
    private Integer maskBlurX;
    @JsonProperty("mask_blur_y")
    private Integer maskBlurY;
    @JsonProperty("mask_blur")
    private Integer maskBlur;
    @JsonProperty("inpainting_fill")
    private Integer inpaintingFill;
    @JsonProperty("inpaint_full_res")
    private Boolean inpaintFullRes;
    @JsonProperty("inpaint_full_res_padding")
    private Integer inpaintFullResPadding;
    @JsonProperty("inpainting_mask_invert")
    private Integer inpaintingMaskInvert;
    @JsonProperty("initial_noise_multiplier")
    private Integer initialNoiseMultiplier;
    @JsonProperty("latent_mask")
    private String latentMask;
    @JsonProperty("include_init_images")
    private Boolean includeInitImages;
}

