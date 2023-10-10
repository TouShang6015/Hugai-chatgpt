package com.hugai.core.drawTask.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.drawTask.entity.SessionCacheDrawData;
import com.hugai.core.drawTask.manager.queue.DrawTaskSdQueueManager;
import com.hugai.core.drawTask.manager.service.DrawSdResponseService;
import com.hugai.core.drawTask.strategy.DrawSDAbstractStrategy;
import com.hugai.core.sd.client.SdApiClientService;
import com.hugai.core.sd.client.SdClientFactory;
import com.hugai.core.sd.entity.request.Img2ImgRequest;
import com.hugai.core.sd.entity.response.Img2ImgResponse;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.system.entity.model.SysAttachmentModel;
import com.hugai.modules.system.entity.vo.baseResource.ResourceDrawVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.system.service.ISysAttachmentService;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * 策略实现类 sd 文生图
 *
 * @author WuHao
 * @since 2023-09-11 11:12:31
 */
@Slf4j
public class ApiStrategySdImg2Img extends DrawSDAbstractStrategy<Img2ImgRequest> {

    private final ResourceDrawVO resourceDrawVO;

    public ApiStrategySdImg2Img(TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        super(drawData, cacheData);
        this.resourceDrawVO = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceDraw();
    }

    @Override
    protected Class<Img2ImgRequest> getMappingCls() {
        return Img2ImgRequest.class;
    }

    @Override
    public DrawType.ApiKey apiKey() {
        return DrawType.ApiKey.sd_img2img;
    }

    @Override
    public void executeApiHandle() {
        String requestParam = this.drawData.getRequestParam();

        Img2ImgRequest apiRequestParam = JSON.parseObject(requestParam, this.getMappingCls(), JSONReader.Feature.SupportSmartMatch);

        final Long taskId = this.drawData.getId();

        SdApiClientService service = SdClientFactory.createService();

        String baseImg = null;
        String attachmentId = apiRequestParam.getBaseImg();
        SysAttachmentModel attachmentModel = SpringUtils.getBean(ISysAttachmentService.class).getById(attachmentId);
        if (Objects.isNull(attachmentModel)) {
            throw new BusinessException(String.format("图片获取异常：attachmentId：%s", attachmentId));
        }
        // 设置垫图
        String fileAbsolutePath = attachmentModel.getFileAbsolutePath();
        try (FileInputStream inputStream = new FileInputStream(fileAbsolutePath);) {
            baseImg = Base64Utils.encodeToString(IoUtil.readBytes(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(String.format("图片本地读取失败：attachmentId：%s", attachmentId));
        }

        apiRequestParam.setInitImages(CollUtil.newArrayList(baseImg));
        // 优化prompt
        apiRequestParam.setPrompt(this.optimizePrompt());
        // 配置加载
        super.configLoading(resourceDrawVO).accept(apiRequestParam);

        log.info("[绘图 - sd] 图生图 - api请求 任务ID:{}", taskId);

        // 添加任务至队列管理器
        DrawTaskSdQueueManager queueManager = SpringUtils.getBean(DrawTaskSdQueueManager.class);
        queueManager.startSync(String.valueOf(taskId), () -> {
            Img2ImgResponse response;
            try {
                response = service.img2img(apiRequestParam);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("[绘图 - sd] 响应失败： {}", e.getMessage());
                return;
            }
            SpringUtils.getBean(DrawSdResponseService.class).handleImg2Img(String.valueOf(taskId), apiRequestParam, response);
        });
    }

}
