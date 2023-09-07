package com.hugai.core.sd.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/9/7 17:17
 */
@NoArgsConstructor
@Data
public class TxtImgRequest {

    /**
     * 提示词
     */
    @JsonProperty("prompt")
    private String prompt;
    /**
     * 反向提示词
     */
    @JsonProperty("negative_prompt")
    private String negativePrompt;

    @JsonProperty("styles")
    private List<String> styles;
    @JsonProperty("seed")
    private Integer seed;
    @JsonProperty("subseed")
    private Integer subseed;
    @JsonProperty("subseed_strength")
    private Integer subseedStrength;
    @JsonProperty("seed_resize_from_h")
    private Integer seedResizeFromH;
    @JsonProperty("seed_resize_from_w")
    private Integer seedResizeFromW;
    @JsonProperty("sampler_name")
    private String samplerName;
    @JsonProperty("batch_size")
    private Integer batchSize;
    @JsonProperty("n_iter")
    private Integer nIter;
    @JsonProperty("steps")
    private Integer steps;
    @JsonProperty("cfg_scale")
    private Integer cfgScale;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("restore_faces")
    private Boolean restoreFaces;
    @JsonProperty("tiling")
    private Boolean tiling;
    @JsonProperty("do_not_save_samples")
    private Boolean doNotSaveSamples;
    @JsonProperty("do_not_save_grid")
    private Boolean doNotSaveGrid;
    @JsonProperty("eta")
    private Integer eta;
    @JsonProperty("denoising_strength")
    private Double denoisingStrength;
    @JsonProperty("s_min_uncond")
    private Integer sMinUncond;
    @JsonProperty("s_churn")
    private Integer sChurn;
    @JsonProperty("s_tmax")
    private Integer sTmax;
    @JsonProperty("s_tmin")
    private Integer sTmin;
    @JsonProperty("s_noise")
    private Integer sNoise;
    @JsonProperty("override_settings")
    private OverrideSettings overrideSettings;
    @JsonProperty("override_settings_restore_afterwards")
    private Boolean overrideSettingsRestoreAfterwards;
    @JsonProperty("refiner_checkpoint")
    private String refinerCheckpoint;
    @JsonProperty("refiner_switch_at")
    private Integer refinerSwitchAt;
    @JsonProperty("disable_extra_networks")
    private Boolean disableExtraNetworks;
    @JsonProperty("comments")
    private Comments comments;
    @JsonProperty("enable_hr")
    private Boolean enableHr;
    @JsonProperty("firstphase_width")
    private Integer firstphaseWidth;
    @JsonProperty("firstphase_height")
    private Integer firstphaseHeight;
    @JsonProperty("hr_scale")
    private Integer hrScale;
    @JsonProperty("hr_upscaler")
    private String hrUpscaler;
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
    @JsonProperty("sampler_index")
    private String samplerIndex;
    @JsonProperty("script_name")
    private String scriptName;
    @JsonProperty("script_args")
    private List<?> scriptArgs;
    @JsonProperty("send_images")
    private Boolean sendImages;
    @JsonProperty("save_images")
    private Boolean saveImages;
    @JsonProperty("alwayson_scripts")
    private AlwaysonScripts alwaysonScripts;

    @NoArgsConstructor
    @Data
    public static class OverrideSettings {
    }

    @NoArgsConstructor
    @Data
    public static class Comments {
    }

    @NoArgsConstructor
    @Data
    public static class AlwaysonScripts {
    }
}
