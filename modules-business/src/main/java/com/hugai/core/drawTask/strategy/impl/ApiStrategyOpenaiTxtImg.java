package com.hugai.core.drawTask.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.hugai.chatsdk.entity.account.ChatSdkAccount;
import com.hugai.chatsdk.openai.client.OpenaiClientFactory;
import com.hugai.common.enums.flow.ChatSdkType;
import com.hugai.core.drawTask.entity.CacheService;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.common.modules.entity.draw.vo.openai.OpenaiTxt2ImgRequest;
import com.hugai.core.chat.account.service.SdkAccountBuildService;
import com.hugai.core.drawTask.entity.SessionCacheDrawData;
import com.hugai.core.drawTask.manager.DrawTaskDataManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskOpenaiQueueManager;
import com.hugai.core.drawTask.manager.service.DrawOpenaiResponseService;
import com.hugai.core.drawTask.strategy.DrawAbstractStrategy;
import com.hugai.modules.chat.convert.DrawOpenaiConvert;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;

/**
 * 策略实现类 openai 文生图
 *
 * @author WuHao
 * @since 2023/9/8 13:22
 */
@Slf4j
public class ApiStrategyOpenaiTxtImg extends DrawAbstractStrategy<OpenaiTxt2ImgRequest> {

    public ApiStrategyOpenaiTxtImg(CacheService cacheService, TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        super(cacheService,drawData, cacheData);
    }

    @Override
    protected Class<OpenaiTxt2ImgRequest> getMappingCls() {
        return OpenaiTxt2ImgRequest.class;
    }

    @Override
    public DrawType.ApiKey apiKey() {
        return DrawType.ApiKey.openai_txt2img;
    }

    @Override
    public void executeApiHandle() {
        String requestParam = this.drawData.getRequestParam();

        OpenaiTxt2ImgRequest apiRequestParam = JSON.parseObject(requestParam, this.getMappingCls());
        apiRequestParam.setSize(apiRequestParam.getSizeWidth() + "x" + apiRequestParam.getSizeHeight());

        final Long taskId = this.drawData.getId();

        SdkAccountBuildService accountBuildService = sdkAccountBuildContext.getService(ChatSdkType.openai.getKey(), BusinessException::new);
        ChatSdkAccount chatSdkAccount = accountBuildService.buildSdkAccountBySdkUnique(ChatSdkType.openai.getKey());

        OpenAiService openAiService = OpenaiClientFactory.getService(chatSdkAccount);

        CreateImageRequest apiParam = DrawOpenaiConvert.INSTANCE.convertApiParam(apiRequestParam);

        // 添加任务至队列管理器
        DrawTaskDataManager queueManager = SpringUtils.getBean(DrawTaskOpenaiQueueManager.class);
        queueManager.startSync(String.valueOf(taskId), () -> {
            ImageResult apiResponse;
            try {
                apiResponse = openAiService.createImage(apiParam);
                log.info("openai 文生图响应：{}", JSON.toJSONString(apiResponse));
            } catch (OpenAiHttpException e) {
                e.printStackTrace();
                int statusCode = e.statusCode;
                String code = e.code;
                if (HttpStatus.UNAUTHORIZED == statusCode || "insufficient_quota".equals(code)) {
                    // todo 停用账号
                }
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            DrawOpenaiResponseService responseService = SpringUtils.getBean(DrawOpenaiResponseService.class);
            responseService.handleTxt2img(String.valueOf(taskId), apiParam, apiResponse);
        });

    }

}
