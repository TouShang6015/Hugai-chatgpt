package com.hugai.core.drawTask.context;

import com.hugai.chatsdk.common.context.ChatBusinessServiceContext;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.core.chat.account.SdkAccountBuildContext;
import com.hugai.core.drawTask.entity.CacheService;
import com.hugai.core.drawTask.entity.SessionCacheDrawData;
import com.hugai.core.drawTask.enums.DrawType;
import com.hugai.core.drawTask.strategy.DrawApiService;
import com.hugai.core.drawTask.strategy.impl.ApiStrategyOpenaiImg2img;
import com.hugai.core.drawTask.strategy.impl.ApiStrategyOpenaiTxtImg;
import com.hugai.core.drawTask.strategy.impl.ApiStrategySdImg2Img;
import com.hugai.core.drawTask.strategy.impl.ApiStrategySdTxtImg;
import com.hugai.core.drawTask.strategy.impl.mj.ApiStrategyMjTxt2Img;
import com.hugai.core.drawTask.strategy.impl.mj.ApiStrategyMjU;
import com.hugai.core.drawTask.strategy.impl.mj.ApiStrategyMjV;
import com.org.bebas.exception.BusinessException;

/**
 * 绘图业务上下文入口
 *
 * @author WuHao
 * @since 2023/9/8 13:32
 */
public class DrawApiServiceContext {

    private DrawApiServiceContext() {
    }

    private TaskDrawModel drawData;

    private SessionCacheDrawData cacheData;

    private CacheService cacheService;

    public static DrawApiServiceContext init(TaskDrawModel drawData) {
        return init(drawData, null);
    }

    public static DrawApiServiceContext init(TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        return init(drawData, cacheData, null, null);
    }

    public static DrawApiServiceContext init(TaskDrawModel drawData, SessionCacheDrawData cacheData, SdkAccountBuildContext sdkAccountBuildContext, ChatBusinessServiceContext chatBusinessServiceContext) {
        DrawApiServiceContext context = new DrawApiServiceContext();
        context.drawData = drawData;
        context.cacheData = cacheData;
        context.cacheService = CacheService.builder()
                .chatBusinessServiceContext(chatBusinessServiceContext)
                .sdkAccountBuildContext(sdkAccountBuildContext)
                .build();
        return context;
    }

    public DrawApiService getDrawApiService() {
        DrawType.ApiKey drawApiKey = DrawType.ApiKey.getByName(drawData.getDrawApiKey());

        DrawApiService drawApiService;

        switch (drawApiKey) {
            case openai_txt2img -> drawApiService = new ApiStrategyOpenaiTxtImg(this.cacheService,this.drawData, this.cacheData);
            case openai_img2img -> drawApiService = new ApiStrategyOpenaiImg2img(this.cacheService,this.drawData, this.cacheData);
            case sd_txt2img -> drawApiService = new ApiStrategySdTxtImg(this.cacheService,this.drawData, this.cacheData);
            case sd_img2img -> drawApiService = new ApiStrategySdImg2Img(this.cacheService,this.drawData, this.cacheData);
            case mj_txt2img -> drawApiService = new ApiStrategyMjTxt2Img(this.cacheService,this.drawData);
            case mj_u -> drawApiService = new ApiStrategyMjU(this.cacheService,this.drawData);
            case mj_v -> drawApiService = new ApiStrategyMjV(this.cacheService,this.drawData);
            default -> throw new BusinessException("没有找到绘图策略服务");
        }
        return drawApiService;
    }

}
