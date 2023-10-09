package com.hugai.core.drawTask.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.openai.entity.request.OpenaiTxt2ImgRequest;
import com.hugai.core.drawTask.manager.DrawTaskDataManager;
import com.hugai.core.drawTask.manager.queue.DrawTaskOpenaiQueueManager;
import com.hugai.core.drawTask.manager.service.DrawOpenaiResponseService;
import com.hugai.core.drawTask.strategy.DrawAbstractStrategy;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.modules.chat.convert.DrawOpenaiConvert;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.core.spring.SpringUtils;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 策略实现类 openai 文生图
 *
 * @author WuHao
 * @since 2023/9/8 13:22
 */
@Slf4j
public class ApiStrategyOpenaiTxtImg extends DrawAbstractStrategy<OpenaiTxt2ImgRequest> {

    public ApiStrategyOpenaiTxtImg(TaskDrawModel drawData) {
        super(drawData);
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

        OpenAiService openAiService = AiServiceFactory.createService();
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
                    SpringUtils.getBean(IOpenaiKeysService.class).removeByOpenaiKey(openAiService.getDecryptToken());
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            DrawOpenaiResponseService responseService = SpringUtils.getBean(DrawOpenaiResponseService.class);
            responseService.handleTxt2img(String.valueOf(taskId), apiParam, apiResponse);
        });

    }

}
