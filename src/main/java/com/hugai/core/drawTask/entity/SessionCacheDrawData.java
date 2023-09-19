package com.hugai.core.drawTask.entity;

import lombok.Data;

/**
 * @author WuHao
 * @since 2023/9/13 9:50
 */
@Data
public class SessionCacheDrawData {

    private String prompt;

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
