package com.hugai.core.drawTask.strategy;

import com.hugai.core.midjourney.common.entity.TaskObj;
import com.hugai.core.midjourney.common.utils.DefaultApiParamJSON;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.spring.SpringUtils;

import java.util.Map;

/**
 * @author WuHao
 * @since 2023/9/26 14:15
 */
public abstract class DrawMJAbstractStrategy<MappingCls> extends DrawAbstractStrategy<MappingCls> {

    protected Map<String, String> apiDefaultParamsMap;

    protected TaskObj taskObj;

    protected SessionRecordDrawService sessionRecordDrawService;

    public DrawMJAbstractStrategy(TaskDrawModel drawData) {
        super(drawData);
        this.apiDefaultParamsMap = SpringUtils.getBean(DefaultApiParamJSON.class).getApiDefaultParamMap();
        this.sessionRecordDrawService = SpringUtils.getBean(SessionRecordDrawService.class);
    }

    /**
     * executeApi 策略器完整处理
     *
     * @return
     */
    @Override
    protected void executeApiHandle() {
        try {
            this.mjApiExecute();
        } catch (Exception e) {
            e.printStackTrace();
            SpringUtils.getBean(MidjourneyTaskEventListener.class).errorTask(this.taskObj.getNonce());
        }
    }

    /**
     * mj api 策略完整处理
     *
     * @return
     */
    protected abstract void mjApiExecute();

}
