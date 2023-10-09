package com.hugai.core.midjourney.common.entity.request;

import com.hugai.core.drawTask.valid.CreateTask;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 通用mj绘图http api请求实体
 *
 * @author WuHao
 * @since 2023/9/26 13:57
 */
@Data
public class MjBaseRequest {

    /**
     * 提示词
     */
    @Length(max = 1000, message = "内容长度不能超过1000个字符", groups = {CreateTask.class})
    private String prompt;

    /**
     * 图1 base64
     */
    private String baseImg1;
    /**
     * 图2 base64
     */
    private String baseImg2;
    /**
     * u/v 下标
     */
    private int index;
    /**
     * 原始任务id
     */
    private String originalTaskId;
}
