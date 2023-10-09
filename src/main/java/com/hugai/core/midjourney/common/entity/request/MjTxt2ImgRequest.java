package com.hugai.core.midjourney.common.entity.request;

import com.hugai.core.drawTask.valid.CreateTask;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author WuHao
 * @since 2023/10/7 15:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MjTxt2ImgRequest extends MjBaseRequest {

    /**
     * 提示词
     */
    @NotEmpty(message = "提示词不能为空", groups = {CreateTask.class})
    @Length(max = 1000, message = "内容长度不能超过1000个字符", groups = {CreateTask.class})
    private String prompt;

}
