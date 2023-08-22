package com.hugai.core.apikey;

import cn.hutool.core.lang.Assert;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/5/31 14:44
 */
public abstract class RuleImplCommon implements OneKeyRule {

    protected Long userId;

    protected List<String> keys;

    protected String key;

    public RuleImplCommon(Long userId) {
        this.userId = userId;
        this.keys = SpringUtils.getBean(IOpenaiKeysService.class).getAbleKeys(userId);
        Assert.notEmpty(this.keys, () -> new BusinessException("openai key为空，请前往openAi官网获取openaiKey"));
    }

    @Override
    public String getKey() {
        this.execute();
        return this.key;
    }

    @Override
    public String getKey(Long userId) {
        return null;
    }
}
