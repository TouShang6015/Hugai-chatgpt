package com.hugai.core.drawTask.strategy.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.drawTask.strategy.DrawAbstractStrategy;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.core.session.entity.SessionDrawCreatedOpenaiCacheData;
import com.hugai.framework.file.constants.FileHeaderImageEnum;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.modules.chat.convert.DrawOpenaiConvert;
import com.hugai.modules.config.service.IOpenaiKeysService;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.entity.vo.DrawPersistenceCollection;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import com.org.bebas.constants.HttpStatus;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.utils.OptionalUtil;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 策略实现类 openai 文生图
 *
 * @author WuHao
 * @since 2023/9/8 13:22
 */
@Slf4j
public class ApiStrategyOpenaiTxtImg extends DrawAbstractStrategy<SessionDrawCreatedOpenaiCacheData> {

    public ApiStrategyOpenaiTxtImg(TaskDrawModel drawData) {
        super(drawData);
    }

    @Override
    protected Class<SessionDrawCreatedOpenaiCacheData> getMappingCls() {
        return SessionDrawCreatedOpenaiCacheData.class;
    }

    @Override
    public DrawType.ApiKey apiKey() {
        return DrawType.ApiKey.openai_txt2img;
    }

    @Override
    public DrawPersistenceCollection executeApiHandle() {
        String requestParam = this.drawData.getRequestParam();

        SessionDrawCreatedOpenaiCacheData apiRequestParam = JSON.parseObject(requestParam, this.getMappingCls());
        apiRequestParam.setSize(apiRequestParam.getSizeWidth() + "x" + apiRequestParam.getSizeHeight());

        final Long userId = this.drawData.getUserId();

        OpenAiService openAiService = AiServiceFactory.createService();
        CreateImageRequest apiParam = DrawOpenaiConvert.INSTANCE.convertApiParam(apiRequestParam);

        final String prompt = apiParam.getPrompt();

        ImageResult apiResponse;
        try {
            apiResponse = openAiService.createImage(apiParam);
        } catch (OpenAiHttpException e) {
            e.printStackTrace();
            int statusCode = e.statusCode;
            String code = e.code;
            if (HttpStatus.UNAUTHORIZED == statusCode || "insufficient_quota".equals(code)) {
                SpringUtils.getBean(IOpenaiKeysService.class).removeByOpenaiKey(openAiService.getDecryptToken());
            }
            return null;
        }

        DrawPersistenceCollection COLLECTION = new DrawPersistenceCollection();

        OR.run(apiResponse, Objects::nonNull, response -> {
            log.info("openai图像生成响应：{}", JSON.toJSONString(response));

            long sessionNum = OptionalUtil.ofNullLong(sessionInfoDrawService.lambdaQuery().eq(SessionInfoDrawModel::getUserId, apiRequestParam.getUser()).count(), 0L);

            final long sessionInfoDrawId = IdWorker.getId();

            // 创建会话实体
            SessionInfoDrawModel sessionInfoSaveParam = SessionInfoDrawModel.builder()
                    .userId(userId)
                    .prompt(prompt)
                    .drawUniqueKey(DrawType.OPENAI.getKey())
                    .sessionNum(Math.toIntExact(sessionNum + 1))
                    .build();
            sessionInfoSaveParam.setId(sessionInfoDrawId);
            COLLECTION.setSessionInfoDrawModelInsert(sessionInfoSaveParam);

            // 响应记录
            List<Image> images = response.getData();
            FileService fileService = fileServiceContext.getFileService();

            // 创建会话记录
            List<SessionRecordDrawModel> sessionRecordSaveParamList = images.stream().filter(Objects::nonNull).map(image -> {
                // 图片存储至系统
                AtomicReference<String> imgUrl = new AtomicReference<>();
                OR.run(image.getUrl(), StrUtil::isNotEmpty, url -> {
                    try (InputStream inputStream = HttpUtil.createGet(url).execute().bodyStream();) {
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
                        .prompt(prompt)
                        .drawUniqueKey(DrawType.OPENAI.getKey())
                        .drawBase64Img(image.getB64Json())
                        .drawImgUrl(imgUrl.get())
                        .build();
            }).collect(Collectors.toList());

            COLLECTION.setSessionRecordDrawModelListInsert(sessionRecordSaveParamList);

        });

        return COLLECTION;

    }

}
