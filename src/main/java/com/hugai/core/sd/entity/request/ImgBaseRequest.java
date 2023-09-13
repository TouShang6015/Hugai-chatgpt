package com.hugai.core.sd.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hugai.core.drawTask.valid.CreateTask;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @author WuHao
 * @since 2023/9/13 10:47
 */
@Data
public class ImgBaseRequest implements Serializable {

    /**
     * 提示词
     */
    @JsonProperty("prompt")
    @NotEmpty(message = "prompt提示词不能为空", groups = {CreateTask.class})
    @Length(max = 800, message = "内容长度不能超过800个字符", groups = {CreateTask.class})
    private String prompt;

    /**
     * 反向提示词
     */
    @JsonProperty("negative_prompt")
    private String negativePrompt;

    @JsonProperty("styles")
    private List<String> styles;
    /**
     * 随机数种子
     */
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
    @Max(value = 4, message = "每次请求图片生成数量不超过4个", groups = {CreateTask.class})
    @Min(value = 1, message = "每次请求图片生成数量不超过4个", groups = {CreateTask.class})
    private Integer batchSize;

    @JsonProperty("n_iter")
    private Integer nIter;
    /**
     * 迭代步数
     */
    @JsonProperty("steps")
    @Max(value = 35, message = "迭代步数只能在10-35之间", groups = {CreateTask.class})
    @Min(value = 10, message = "迭代步数只能在10-35之间", groups = {CreateTask.class})
    private Integer steps;
    @JsonProperty("cfg_scale")
    private Integer cfgScale;

    /**
     * 宽度
     */
    @JsonProperty("width")
    @NotNull(message = "图片宽度不能为空", groups = {CreateTask.class})
    @Max(value = 1281, message = "图片高度与宽度在256-1280之间", groups = {CreateTask.class})
    @Min(value = 255, message = "图片高度与宽度在256-1280之间", groups = {CreateTask.class})
    private Integer width;
    /**
     * 高度
     */
    @JsonProperty("height")
    @NotNull(message = "图片高度不能为空", groups = {CreateTask.class})
    @Max(value = 1281, message = "图片高度与宽度在256-1280之间", groups = {CreateTask.class})
    @Min(value = 255, message = "图片高度与宽度在256-1280之间", groups = {CreateTask.class})
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

    /**
     * 重绘幅度
     */
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
    private HashMap<String, Object> overrideSettings;
    @JsonProperty("override_settings_restore_afterwards")
    private Boolean overrideSettingsRestoreAfterwards;
    @JsonProperty("refiner_checkpoint")
    private String refinerCheckpoint;
    @JsonProperty("refiner_switch_at")
    private Integer refinerSwitchAt;
    @JsonProperty("disable_extra_networks")
    private Boolean disableExtraNetworks;
    @JsonProperty("comments")
    private HashMap<String, Object> comments;

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
    private HashMap<String, Object> alwaysonScripts;

}
