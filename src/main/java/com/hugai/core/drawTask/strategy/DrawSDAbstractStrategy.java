package com.hugai.core.drawTask.strategy;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.Constants;
import com.hugai.core.sd.entity.request.ImgBaseRequest;
import com.hugai.core.session.entity.SessionCacheDrawData;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.system.entity.vo.baseResource.ResourceDrawVO;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author WuHao
 * @since 2023/9/13 10:44
 */
public abstract class DrawSDAbstractStrategy<MappingCls extends ImgBaseRequest> extends DrawAbstractStrategy<MappingCls> {

    public DrawSDAbstractStrategy(TaskDrawModel drawData) {
        super(drawData);
    }

    public DrawSDAbstractStrategy(TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        super(drawData, cacheData);
    }

    /**
     * 加载配置
     *
     * @return
     */
    protected Consumer<MappingCls> configLoading(ResourceDrawVO resourceDrawVO) {
        return requestParam -> {
            SessionCacheDrawData cacheData = this.cacheData;

            final String finalNegativePrompt = requestParam.getNegativePrompt();

            // 固定提示词
            String prompt = Optional.ofNullable(requestParam.getPrompt()).orElse("");
            String negativePrompt = Optional.ofNullable(requestParam.getNegativePrompt()).orElse("");
            // 正向内容
            if (resourceDrawVO.getOpenBeforePromptContent()) {
                prompt = Optional.ofNullable(resourceDrawVO.getBeforePromptContent()).orElse("") + " " + prompt;
            }
            // 反向内容
            if (resourceDrawVO.getOpenBeforeNegativePromptContent()) {
                negativePrompt = Optional.ofNullable(resourceDrawVO.getBeforeNegativePromptContent()).orElse("") + " " + negativePrompt;
            }

            // 普通模式
            if (!Constants.BOOLEAN.TRUE.equals(cacheData.getProfessionMode())) {
                // 填充系统默认请求参数
                MappingCls mappingCls = JSON.parseObject(resourceDrawVO.getDefaultRequestBean(), this.getMappingCls());
                BeanUtil.copyProperties(mappingCls, requestParam, CopyOptions.create().setIgnoreNullValue(true));
                // 默认反向prompt填充
                if (StrUtil.isEmpty(finalNegativePrompt)) {
                    negativePrompt = negativePrompt + " " + Optional.ofNullable(resourceDrawVO.getDefaultNegativePrompt()).orElse("");
                }
                prompt = prompt + Optional.ofNullable(resourceDrawVO.getDefaultPrompt()).orElse("");
            }

            requestParam.setPrompt(prompt);
            requestParam.setNegativePrompt(negativePrompt);

        };
    }

}
