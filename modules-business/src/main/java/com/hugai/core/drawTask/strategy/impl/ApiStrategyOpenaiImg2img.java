package com.hugai.core.drawTask.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.hugai.chatsdk.common.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.openai.client.OpenaiClientFactory;
import com.hugai.common.enums.flow.ChatSdkType;
import com.hugai.core.drawTask.entity.CacheService;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.common.modules.entity.draw.vo.openai.OpenaiImg2ImgRequest;
import com.hugai.core.chat.account.service.SdkAccountBuildService;
import com.hugai.core.drawTask.entity.SessionCacheDrawData;
import com.hugai.core.drawTask.manager.DrawTaskDataManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskOpenaiQueueManager;
import com.hugai.core.drawTask.manager.service.DrawOpenaiResponseService;
import com.hugai.core.drawTask.strategy.DrawAbstractStrategy;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.service.OpenAiService;
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

    public ApiStrategyOpenaiImg2img(CacheService cacheService, TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        super(cacheService, drawData, cacheData);
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

        SdkAccountBuildService accountBuildService = sdkAccountBuildContext.getService(ChatSdkType.openai.getKey(), BusinessException::new);
        ChatSdkAccount chatSdkAccount = accountBuildService.buildSdkAccountBySdkUnique(ChatSdkType.openai.getKey());

        OpenAiService openAiService = OpenaiClientFactory.getService(chatSdkAccount);

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
                    // todo 停用账号
                }
                throw e;
            }
            DrawOpenaiResponseService responseService = SpringUtils.getBean(DrawOpenaiResponseService.class);
            responseService.handleImg2Img(String.valueOf(taskId), apiParam, apiResponse);
        });
    }

}
