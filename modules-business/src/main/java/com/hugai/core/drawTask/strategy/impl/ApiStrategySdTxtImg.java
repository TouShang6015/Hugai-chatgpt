package com.hugai.core.drawTask.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.hugai.core.drawTask.entity.CacheService;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.common.webApi.baseResource.BaseResourceWebApi;
import com.hugai.core.drawTask.entity.SessionCacheDrawData;
import com.hugai.core.drawTask.manager.queue.DrawTaskSdQueueManager;
import com.hugai.core.drawTask.manager.service.DrawSdResponseService;
import com.hugai.core.drawTask.strategy.DrawSDAbstractStrategy;
import com.hugai.core.sd.client.SdApiClientService;
import com.hugai.core.sd.client.SdClientFactory;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import com.hugai.core.sd.entity.response.TxtImgResponse;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.common.entity.baseResource.ResourceDrawVO;
import com.org.bebas.core.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 策略实现类 sd 文生图
 *
 * @author WuHao
 * @since 2023-09-11 11:12:31
 */
@Slf4j
public class ApiStrategySdTxtImg extends DrawSDAbstractStrategy<TxtImgRequest> {

    private final ResourceDrawVO resourceDrawVO;

    public ApiStrategySdTxtImg(CacheService cacheService, TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        super(cacheService,drawData, cacheData);
        this.resourceDrawVO = SpringUtils.getBean(BaseResourceWebApi.class).getResourceDraw();
    }

    @Override
    protected Class<TxtImgRequest> getMappingCls() {
        return TxtImgRequest.class;
    }

    @Override
    public DrawType.ApiKey apiKey() {
        return DrawType.ApiKey.sd_txt2img;
    }

    @Override
    public void executeApiHandle() {
        String requestParam = this.drawData.getRequestParam();

        TxtImgRequest apiRequestParam = JSON.parseObject(requestParam, this.getMappingCls(), JSONReader.Feature.SupportSmartMatch);

        final Long taskId = this.drawData.getId();

        SdApiClientService service = SdClientFactory.createService();

        // 优化prompt
        apiRequestParam.setPrompt(this.optimizePrompt());
        // 配置加载
        super.configLoading(resourceDrawVO).accept(apiRequestParam);

        log.info("[绘图 - sd] 文生图 - api请求 任务ID:{}", taskId);
        // 添加任务至队列管理器
        DrawTaskSdQueueManager queueManager = SpringUtils.getBean(DrawTaskSdQueueManager.class);
        queueManager.startSync(String.valueOf(taskId), () -> {
            TxtImgResponse response;
            try {
                response = service.txt2img(apiRequestParam);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("[绘图 - sd] 响应失败： {}", e.getMessage());
                throw e;
            }
            SpringUtils.getBean(DrawSdResponseService.class).handleTxt2Img(String.valueOf(taskId), apiRequestParam, response);
        });
    }

}
