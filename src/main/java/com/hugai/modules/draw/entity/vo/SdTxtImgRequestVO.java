package com.hugai.modules.draw.entity.vo;

import com.hugai.core.drawTask.valid.CreateTask;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author WuHao
 * @since 2023/9/11 16:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SdTxtImgRequestVO extends TxtImgRequest {

    @NotEmpty(message = "prompt提示词不能为空", groups = {CreateTask.class})
    @Length(max = 800, message = "内容长度不能超过800个字符", groups = {CreateTask.class})
    private String prompt;

    @NotNull(message = "迭代步数不能为空", groups = {CreateTask.class})
    @Max(value = 35, message = "迭代步数只能在10-35之间", groups = {CreateTask.class})
    @Min(value = 10, message = "迭代步数只能在10-35之间", groups = {CreateTask.class})
    private Integer steps;

    @NotNull(message = "图片宽度不能为空", groups = {CreateTask.class})
    @Max(value = 1281, message = "图片高度与宽度在256-1280之间", groups = {CreateTask.class})
    @Min(value = 255, message = "图片高度与宽度在256-1280之间", groups = {CreateTask.class})
    private Integer width;

    @NotNull(message = "图片高度不能为空", groups = {CreateTask.class})
    @Max(value = 1281, message = "图片高度与宽度在256-1280之间", groups = {CreateTask.class})
    @Min(value = 255, message = "图片高度与宽度在256-1280之间", groups = {CreateTask.class})
    private Integer height;

    /**
     * 是否专业模式
     * <p>* 关闭正向prompt优化</>
     * <p>* 关闭反向prompt优化</>
     * <p>* 关闭默认参数填充</>
     */
    private String professionMode;
    /**
     * 是否需要通过gpt优化正向prompt
     */
    private String optimizePrompt;

}
