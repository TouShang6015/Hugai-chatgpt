package com.hugai.core.drawTask.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.openai.entity.request.OpenaiImg2ImgRequest;
import com.hugai.core.drawTask.manager.DrawTaskDataManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskOpenaiQueueManager;
import com.hugai.core.drawTask.manager.service.DrawOpenaiResponseService;
import com.hugai.core.drawTask.strategy.DrawAbstractStrategy;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.core.spring.SpringUtils;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.ImageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

/**
 * 策略实现类 openai 图生图
 *
 * @author WuHao
 * @since 2023/9/8 13:22
 */
@Slf4j
public class ApiStrategyOpenaiImg2img extends DrawAbstractStrategy<OpenaiImg2ImgRequest> {

    public ApiStrategyOpenaiImg2img(TaskDrawModel drawData) {
        super(drawData);
    }

    @Override
    protected Class<OpenaiImg2ImgRequest> getMappingCls() {
        return OpenaiImg2ImgRequest.class;
    }

    @Override
    public DrawType.ApiKey apiKey() {
        return DrawType.ApiKey.openai_txt2img;
    }

    @Override
    public void executeApiHandle() {
        String requestParam = this.drawData.getRequestParam();
        OpenaiImg2ImgRequest apiRequestParam = JSON.parseObject(requestParam, this.getMappingCls());
        apiRequestParam.setSize(apiRequestParam.getSizeWidth() + "x" + apiRequestParam.getSizeHeight());
        apiRequestParam.setBaseImg(apiRequestParam.getBaseImg());

        OpenAiService openAiService = AiServiceFactory.createService();

        String fileConfigPath = SpringUtils.getBean(SysFileConfigService.class).getFileConfigPath();

        CreateImageEditRequest apiParamBuildParam = CreateImageEditRequest.builder()
                .n(apiRequestParam.getN())
                .prompt(apiRequestParam.getPrompt())
                .size(apiRequestParam.getSize())
                .responseFormat(apiRequestParam.getResponseFormat())
                .build();
        CreateImageEditRequest apiParam = JSON.parseObject(JSON.toJSONString(apiParamBuildParam, JSONWriter.Feature.NullAsDefaultValue), CreateImageEditRequest.class);
        String imagePath = FilenameUtils.normalize(fileConfigPath + apiRequestParam.getImage());
        String maskPath = null;
        if (StrUtil.isNotEmpty(apiRequestParam.getMask())) {
            maskPath = FilenameUtils.normalize(fileConfigPath + apiRequestParam.getMask());
        }

        final Long taskId = this.drawData.getId();

        // 添加任务至队列管理器
        DrawTaskDataManager queueManager = SpringUtils.getBean(DrawTaskOpenaiQueueManager.class);
        String finalMaskPath = maskPath;
        queueManager.startSync(String.valueOf(taskId), () -> {
            ImageResult apiResponse;
            try {
                apiResponse = openAiService.createImageEdit(apiParam, imagePath, finalMaskPath);
                log.info("openai 图生图响应：{}", JSON.toJSONString(apiResponse));
            } catch (OpenAiHttpException e) {
                e.printStackTrace();
                int statusCode = e.statusCode;
                String code = e.code;
                if (HttpStatus.UNAUTHORIZED == statusCode || "insufficient_quota".equals(code)) {
                    SpringUtils.getBean(IOpenaiKeysService.class).removeByOpenaiKey(openAiService.getDecryptToken());
                }
                return;
            }
            DrawOpenaiResponseService responseService = SpringUtils.getBean(DrawOpenaiResponseService.class);
            responseService.handleImg2Img(String.valueOf(taskId), apiParam, apiResponse);
        });
    }

}
