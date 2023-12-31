package com.hugai.core.midjourney.common.entity.request;

import com.hugai.common.modules.entity.draw.valid.CreateTask;
import com.hugai.core.midjourney.valid.annotation.FilterSenWord;
import com.hugai.core.midjourney.valid.annotation.NotSocketConnect;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @author WuHao
 * @since 2023/10/7 15:26
 */
@NotSocketConnect(message = "Midjourney无法连接，请稍后重试或联系管理员", groups = {CreateTask.class})
@EqualsAndHashCode(callSuper = true)
@Data
public class MjTxt2ImgRequest extends MjBaseRequest {

    /**
     * 提示词
     */
    @FilterSenWord(message = "存在敏感词汇请重试", groups = {CreateTask.class})
    @NotEmpty(message = "提示词不能为空", groups = {CreateTask.class})
    private String prompt;

}
