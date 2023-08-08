package com.hugai.modules.chat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.core.session.entity.SessionDrawCreatedOpenaiCacheData;
import com.hugai.core.session.entity.SessionDrawEditOpenaiCacheData;
import com.hugai.core.session.valid.SendDrawOpenAi;
import com.hugai.framework.file.constants.FileHeaderImageEnum;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.modules.chat.convert.DrawOpenaiConvert;
import com.hugai.modules.chat.service.DrawOpenaiService;
import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.hugai.modules.session.service.SessionRecordDrawService;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.core.validator.ValidatorUtil;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.OptionalUtil;
import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/7/17 9:37
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DrawOpenaiServiceImpl implements DrawOpenaiService {

    public static final String CACHE_KEY = "DRAW-SESSION:%s:OPENAI";

    private final SessionRecordDrawService sessionRecordDrawService;

    private final SessionInfoDrawService sessionInfoDrawService;

    private final FileServiceContext fileServiceContext;

    private final SysFileConfigService fileConfigService;

    private final RedisUtil redisUtil;

    /**
     * ai绘图消息发送
     *
     * @param param
     */
    @Transactional
    @Override
    public ImageResult sendDrawCreatedOpenAi(SessionDrawCreatedOpenaiCacheData param) {
        ValidatorUtil.validateEntity(param, SendDrawOpenAi.class);

        String KEY = String.format(CACHE_KEY,param.getUserId());

        Integer count = redisUtil.getCacheObject(KEY);
        if (Objects.nonNull(count) && count > 2){
            throw new BusinessException("绘图失败，每位用户1小时可以请求2次绘图接口，正在努力寻找白嫖或节约openai key的方案，谅解下，😂😂");
        }

        OpenAiService openAiService = AiServiceFactory.createService();

        CreateImageRequest apiParam = DrawOpenaiConvert.INSTANCE.convertApiParam(param);

        final String prompt = apiParam.getPrompt();

        ImageResult apiResponse = openAiService.createImage(apiParam);

        OR.run(apiResponse, Objects::nonNull, response -> {
            log.info("openai图像生成响应：{}", JSON.toJSONString(response));

            long sessionNum = OptionalUtil.ofNullLong(sessionInfoDrawService.lambdaQuery().eq(SessionInfoDrawModel::getUserId, param.getUser()).count(), 0L);

            // 创建会话实体
            SessionInfoDrawModel sessionInfoSaveParam = SessionInfoDrawModel.builder()
                    .userId(param.getUserId())
                    .prompt(prompt)
                    .drawUniqueKey(DrawType.OPENAI.getKey())
                    .sessionNum(Math.toIntExact(sessionNum + 1))
                    .build();
            sessionInfoDrawService.save(sessionInfoSaveParam);


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
                        .userId(param.getUserId())
                        .sessionInfoDrawId(sessionInfoSaveParam.getId())
                        .prompt(prompt)
                        .drawUniqueKey(DrawType.OPENAI.getKey())
                        .drawBase64Img(image.getB64Json())
                        .drawImgUrl(imgUrl.get())
                        .build();
            }).collect(Collectors.toList());
            sessionRecordDrawService.saveBatch(sessionRecordSaveParamList);

        });

        redisUtil.incrBy(KEY,1);
        redisUtil.expire(KEY,1, TimeUnit.HOURS);
        return apiResponse;
    }

    /**
     * ai绘图 图像编辑
     *
     * @param param
     */
    @Transactional
    @Override
    public ImageResult sendDrawEditOpenAi(SessionDrawEditOpenaiCacheData param) {
        ValidatorUtil.validateEntity(param);

        String KEY = String.format(CACHE_KEY,param.getUserId());
        Integer count = redisUtil.getCacheObject(KEY);
        if (Objects.nonNull(count) && count > 2){
            throw new BusinessException("绘图失败，每位用户1小时可以请求2次绘图接口，正在努力寻找白嫖或节约openai key的方案，谅解下，😂😂");
        }

        final String prompt = param.getPrompt();

        OpenAiService openAiService = AiServiceFactory.createService();

        String fileConfigPath = fileConfigService.getFileConfigPath();

        CreateImageEditRequest apiParamBuildParam = CreateImageEditRequest.builder()
                .n(param.getN())
                .prompt(param.getPrompt())
                .size(param.getSize())
                .responseFormat(param.getResponseFormat())
                .build();
        CreateImageEditRequest apiParam = JSON.parseObject(JSON.toJSONString(apiParamBuildParam, JSONWriter.Feature.NullAsDefaultValue), CreateImageEditRequest.class);
        String imagePath = FilenameUtils.normalize(fileConfigPath + param.getImage());
        String maskPath = null;
        if (StrUtil.isNotEmpty(param.getMask())) {
            maskPath = FilenameUtils.normalize(fileConfigPath + param.getMask());
        }

        ImageResult apiResponse = openAiService.createImageEdit(apiParam, imagePath, maskPath);

        OR.run(apiResponse, Objects::nonNull, response -> {
            log.info("openai图像生成响应：{}", JSON.toJSONString(response));

            long sessionNum = OptionalUtil.ofNullLong(sessionInfoDrawService.lambdaQuery().eq(SessionInfoDrawModel::getUserId, param.getUser()).count(), 0L);

            // 创建会话实体
            SessionInfoDrawModel sessionInfoSaveParam = SessionInfoDrawModel.builder()
                    .userId(param.getUserId())
                    .prompt(prompt)
                    .drawUniqueKey(DrawType.OPENAI.getKey())
                    .sessionNum(Math.toIntExact(sessionNum + 1))
                    .build();
            sessionInfoDrawService.save(sessionInfoSaveParam);


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
                        .userId(param.getUserId())
                        .sessionInfoDrawId(sessionInfoSaveParam.getId())
                        .prompt(prompt)
                        .drawUniqueKey(DrawType.OPENAI.getKey())
                        .drawBase64Img(image.getB64Json())
                        .drawImgUrl(imgUrl.get())
                        .build();
            }).collect(Collectors.toList());
            sessionRecordDrawService.saveBatch(sessionRecordSaveParamList);

        });

        redisUtil.incrBy(KEY,1);
        redisUtil.expire(KEY,1, TimeUnit.HOURS);

        return apiResponse;
    }

}
