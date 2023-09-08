package com.hugai.core.drawTask.strategy;

import cn.hutool.core.lang.Assert;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.security.context.UserThreadLocal;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.entity.vo.DrawPersistenceCollection;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.org.bebas.core.spring.SpringUtils;

/**
 * 绘图策略器抽象层
 *
 * @author WuHao
 * @since 2023/9/8 13:13
 */
public abstract class DrawAbstractStrategy<MappingCls> implements DrawApiService {

    protected DrawType drawType;

    protected DrawType.ApiKey apiKey;

    protected TaskDrawModel drawData;

    protected SessionInfoDrawService sessionInfoDrawService;

    protected FileServiceContext fileServiceContext;

    public DrawAbstractStrategy(TaskDrawModel drawData) {
        Assert.notNull(drawData);
        this.drawData = drawData;
        this.apiKey = DrawType.ApiKey.getByName(drawData.getDrawApiKey());
        this.drawType = DrawType.getByApiKey(this.apiKey);
        this.sessionInfoDrawService = SpringUtils.getBean(SessionInfoDrawService.class);
        this.fileServiceContext = SpringUtils.getBean(FileServiceContext.class);
    }

    /**
     * 请求外部接口返回持久化集合实体
     *
     * @return
     */
    @Override
    public DrawPersistenceCollection executeApi() {
        UserThreadLocal.set(this.drawData.getUserId());
        try {
            return this.executeApiHandle();
        } finally {
            UserThreadLocal.remove();
        }
    }

    /**
     * executeApi 策略器完整处理
     *
     * @return
     */
    protected abstract DrawPersistenceCollection executeApiHandle();

    /**
     * 获取策略器映射aip映射实体Class
     *
     * @return
     */
    protected abstract Class<MappingCls> getMappingCls();

    protected void close() {
        UserThreadLocal.remove();
    }

}
