package com.hugai.core.drawTask.context;

import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.drawTask.entity.SessionCacheDrawData;
import com.hugai.core.drawTask.strategy.DrawApiService;
import com.hugai.core.drawTask.strategy.impl.ApiStrategyOpenaiImg2img;
import com.hugai.core.drawTask.strategy.impl.ApiStrategyOpenaiTxtImg;
import com.hugai.core.drawTask.strategy.impl.ApiStrategySdImg2Img;
import com.hugai.core.drawTask.strategy.impl.ApiStrategySdTxtImg;
import com.hugai.core.drawTask.strategy.impl.mj.ApiStrategyMjTxt2Img;
import com.hugai.core.drawTask.strategy.impl.mj.ApiStrategyMjU;
import com.hugai.core.drawTask.strategy.impl.mj.ApiStrategyMjV;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
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

    public static DrawApiServiceContext init(TaskDrawModel drawData) {
        return init(drawData, null);
    }

    public static DrawApiServiceContext init(TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        DrawApiServiceContext context = new DrawApiServiceContext();
        context.drawData = drawData;
        context.cacheData = cacheData;
        return context;
    }

    public DrawApiService getDrawApiService() {

        DrawApiService[] drawApiServices = {
                new ApiStrategyOpenaiTxtImg(this.drawData),
                new ApiStrategyOpenaiImg2img(this.drawData),
                new ApiStrategySdTxtImg(this.drawData, this.cacheData),
                new ApiStrategySdImg2Img(this.drawData, this.cacheData),
                new ApiStrategyMjTxt2Img(this.drawData),
                new ApiStrategyMjU(this.drawData),
                new ApiStrategyMjV(this.drawData)
        };
        String drawApiKey = drawData.getDrawApiKey();
        for (DrawApiService drawApiService : drawApiServices) {
            DrawType.ApiKey apiKey = drawApiService.apiKey();
            if (apiKey.name().equals(drawApiKey)) {
                return drawApiService;
            }
        }
        throw new BusinessException("没有找到绘图策略服务");
    }

}
