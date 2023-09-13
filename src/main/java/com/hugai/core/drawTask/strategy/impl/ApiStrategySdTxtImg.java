package com.hugai.core.drawTask.strategy.impl;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.drawTask.strategy.DrawSDAbstractStrategy;
import com.hugai.core.sd.client.SdApiClientService;
import com.hugai.core.sd.client.SdClientFactory;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import com.hugai.core.sd.entity.response.TxtImgResponse;
import com.hugai.core.session.entity.SessionCacheDrawData;
import com.hugai.framework.file.constants.FileHeaderImageEnum;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.entity.vo.DrawPersistenceCollection;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import com.hugai.modules.system.entity.vo.baseResource.ResourceDrawVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.utils.OptionalUtil;
import com.theokanning.openai.OpenAiHttpException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 策略实现类 sd 文生图
 *
 * @author WuHao
 * @since 2023-09-11 11:12:31
 */
@Slf4j
public class ApiStrategySdTxtImg extends DrawSDAbstractStrategy<TxtImgRequest> {

    private final ResourceDrawVO resourceDrawVO;

    public ApiStrategySdTxtImg(TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        super(drawData, cacheData);
        this.resourceDrawVO = SpringUtils.getBean(IBaseResourceConfigService.class).getResourceDraw();
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
    public DrawPersistenceCollection executeApiHandle() {
        String requestParam = this.drawData.getRequestParam();

        TxtImgRequest apiRequestParam = JSON.parseObject(requestParam, this.getMappingCls(), JSONReader.Feature.SupportSmartMatch);

        final Long userId = this.drawData.getUserId();

        SdApiClientService service = SdClientFactory.createService();

        // 优化prompt
        apiRequestParam.setPrompt(this.optimizePrompt());
        // 配置加载
        super.configLoading(resourceDrawVO).accept(apiRequestParam);

        TxtImgResponse apiResponse;
        try {
            log.info("[绘图 - sd] api 请求参数：{}", JSON.toJSONString(apiRequestParam));
            apiResponse = service.txt2img(apiRequestParam);
        } catch (OpenAiHttpException e) {
            e.printStackTrace();
            log.error("[绘图 - sd] 响应失败： {}", e.getMessage());
            return null;
        }

        DrawPersistenceCollection COLLECTION = new DrawPersistenceCollection();

        OR.run(apiResponse, Objects::nonNull, response -> {
            log.info("[绘图 - sd] api 请求成功");

            long sessionNum = OptionalUtil.ofNullLong(sessionInfoDrawService.lambdaQuery().eq(SessionInfoDrawModel::getUserId, userId).count(), 0L);

            final long sessionInfoDrawId = IdWorker.getId();

            // 创建会话实体
            SessionInfoDrawModel sessionInfoSaveParam = SessionInfoDrawModel.builder()
                    .userId(userId)
                    .prompt(apiRequestParam.getPrompt())
                    .drawUniqueKey(DrawType.SD.getKey())
                    .sessionNum(Math.toIntExact(sessionNum + 1))
                    .sdResponseInfo(response.getInfo())
                    .build();
            sessionInfoSaveParam.setId(sessionInfoDrawId);
            COLLECTION.setSessionInfoDrawModelInsert(sessionInfoSaveParam);

            // 响应记录
            List<String> images = response.getImages();
            FileService fileService = fileServiceContext.getFileService();

            // 创建会话记录
            List<SessionRecordDrawModel> sessionRecordSaveParamList = images.stream().filter(Objects::nonNull).map(image -> {
                // 图片存储至系统
                AtomicReference<String> imgUrl = new AtomicReference<>();
                OR.run(image, StrUtil::isNotEmpty, url -> {
                    try (InputStream inputStream = new ByteArrayInputStream(Base64Decoder.decode(image));) {
                        FileResponse fileResponse = fileService.upload(FileTypeRootEnum.image, FileHeaderImageEnum.IMAGE_PNG.getValue(), inputStream);
                        imgUrl.set(fileResponse.getFilePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error("图片转存储失败：{}", JSON.toJSONString(image));
                    }
                });

                return SessionRecordDrawModel.builder()
                        .userId(userId)
                        .sessionInfoDrawId(sessionInfoDrawId)
                        .prompt(apiRequestParam.getPrompt())
                        .drawUniqueKey(DrawType.SD.getKey())
                        .drawImgUrl(imgUrl.get())
                        .build();
            }).collect(Collectors.toList());

            COLLECTION.setSessionRecordDrawModelListInsert(sessionRecordSaveParamList);

            String showImgUrl = sessionRecordSaveParamList.stream().findFirst().orElseGet(SessionRecordDrawModel::new).getDrawImgUrl();
            sessionInfoSaveParam.setShowImg(showImgUrl);

        });


        return COLLECTION;

    }

}
