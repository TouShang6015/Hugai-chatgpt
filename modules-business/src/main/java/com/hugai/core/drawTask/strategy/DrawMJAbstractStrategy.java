package com.hugai.core.drawTask.strategy;

import com.hugai.common.webApi.mjParam.MjParamWebApi;
import com.hugai.common.websocket.constants.ResultCode;
import com.hugai.core.drawTask.entity.CacheService;
import com.hugai.core.midjourney.common.entity.TaskObj;
import com.hugai.core.midjourney.service.MidjourneyTaskEventListener;
import com.hugai.common.modules.entity.draw.model.TaskDrawModel;
import com.hugai.core.user.socket.UserMessagePushUtil;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;

/**
 * @author WuHao
 * @since 2023/9/26 14:15
 */
public abstract class DrawMJAbstractStrategy<MappingCls> extends DrawAbstractStrategy<MappingCls> {

    protected MjParamWebApi paramWebApi;

    protected TaskObj taskObj;

    protected SessionRecordDrawService sessionRecordDrawService;

    public DrawMJAbstractStrategy(CacheService cacheService, TaskDrawModel drawData) {
        super(cacheService,drawData);
        this.paramWebApi = SpringUtils.getBean(MjParamWebApi.class);
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
            if (e instanceof BusinessException){
                UserMessagePushUtil.pushMessageString(String.valueOf(this.drawData.getUserId()), ResultCode.S_MESSAGE_ERROR,e.getMessage());
            }
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
