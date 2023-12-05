package com.hugai.core.midjourney.common.entity.request;

import com.hugai.common.modules.entity.draw.valid.CreateTask;
import com.hugai.core.midjourney.valid.annotation.FilterSenWord;
import com.hugai.core.midjourney.valid.annotation.NotSocketConnect;
import lombok.Data;

/**
 * 通用mj绘图http api请求实体
 *
 * @author WuHao
 * @since 2023/9/26 13:57
 */
@NotSocketConnect(message = "Midjourney无法连接，请稍后重试或联系管理员", groups = {CreateTask.class})
@Data
public class MjBaseRequest {

    /**
     * 提示词
     */
    @FilterSenWord(message = "存在敏感词汇请重试", groups = {CreateTask.class})
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
    private String originalTaskDrawId;
}
