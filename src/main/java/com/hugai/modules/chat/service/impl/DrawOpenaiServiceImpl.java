package com.hugai.modules.chat.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.hugai.common.constants.Constants;
import com.hugai.core.openai.enums.RoleEnum;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.core.session.entity.SessionDrawCreatedOpenaiCacheData;
import com.hugai.core.session.entity.SessionDrawEditOpenaiCacheData;
import com.hugai.framework.file.constants.FileHeaderImageEnum;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.modules.chat.convert.DrawOpenaiConvert;
import com.hugai.modules.chat.service.DrawOpenaiService;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.hugai.modules.session.service.SessionRecordService;
import com.hugai.modules.system.entity.vo.baseResource.ResourceMainVO;
import com.hugai.modules.system.service.IBaseResourceConfigService;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.validator.ValidatorUtil;
import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @since 2023/7/17 9:37
 */
@RequiredArgsConstructor
@Service
public class DrawOpenaiServiceImpl implements DrawOpenaiService {

    private final SessionRecordService sessionRecordService;

    private final FileServiceContext fileServiceContext;

    private final IBaseResourceConfigService baseResourceConfigService;

    private final SysFileConfigService fileConfigService;

    /**
     * ai绘图消息发送
     *
     * @param param
     */
    @Transactional
    @Override
    public ImageResult sendDrawCreatedOpenAi(SessionDrawCreatedOpenaiCacheData param) {
        ValidatorUtil.validateEntity(param);

        final Long sessionId = param.getSessionId();

        OpenAiService openAiService = AiServiceFactory.createService();

        CreateImageRequest apiParam = DrawOpenaiConvert.INSTANCE.convertApiParam(param);

        final String prompt = apiParam.getPrompt();

        ImageResult apiResponse = openAiService.createImage(apiParam);

        OR.run(apiResponse, Objects::nonNull, response -> {
            // 用户会话记录
            SessionRecordModel userRecordParam = SessionRecordModel.builder()
                    .sessionId(sessionId)
                    .userId(param.getUserId())
                    .role(RoleEnum.user.name())
                    .content(prompt)
                    .ifShow(Constants.BOOLEAN.TRUE)
                    .ifContext(Constants.BOOLEAN.TRUE)
                    .ifDomainTop(Constants.BOOLEAN.FALSE)
                    .consumerToken(0)
                    .build();
            // 响应记录
            List<Image> images = response.getData();
            FileService fileService = fileServiceContext.getFileService();
            // 图片存储至系统
            List<String> imgUrlList = images.stream().map(Image::getUrl).filter(StrUtil::isNotEmpty).map(url -> {
                try (
                        InputStream inputStream = HttpUtil.createGet(url).execute().bodyStream();
                ) {
                    FileResponse fileResponse = fileService.upload(FileTypeRootEnum.image, FileHeaderImageEnum.IMAGE_PNG.getValue(), inputStream);
                    return fileResponse.getFilePath();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            String base64Json = images.stream().map(Image::getB64Json).filter(Objects::nonNull).collect(Collectors.joining(","));
            String urls = String.join(",", imgUrlList);
            SessionRecordModel responseRecordParam = SessionRecordModel.builder()
                    .sessionId(sessionId)
                    .userId(param.getUserId())
                    .role(RoleEnum.assistant.name())
                    .content(urls)
                    .drawBase64Img(base64Json)
                    .ifShow(Constants.BOOLEAN.TRUE)
                    .ifContext(Constants.BOOLEAN.TRUE)
                    .ifDomainTop(Constants.BOOLEAN.FALSE)
                    .consumerToken(0)
                    .build();
            List<SessionRecordModel> insertParams = CollUtil.newArrayList(userRecordParam, responseRecordParam);
            sessionRecordService.responseInsertHandle(param, insertParams);
        });

        return apiResponse;
    }

    /**
     * ai绘图 图像编辑
     *
     * @param param
     */
    @Override
    public ImageResult sendDrawEditOpenAi(SessionDrawEditOpenaiCacheData param) {
        ValidatorUtil.validateEntity(param);

        final Long sessionId = param.getSessionId();

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
        if (StrUtil.isNotEmpty(param.getMask())){
            maskPath = FilenameUtils.normalize(fileConfigPath + param.getMask());
        }

        ImageResult imageResult = openAiService.createImageEdit(apiParam, imagePath, maskPath);

        OR.run(imageResult, Objects::nonNull, response -> {
            // 用户会话记录
            SessionRecordModel userRecordParam = SessionRecordModel.builder()
                    .sessionId(sessionId)
                    .userId(param.getUserId())
                    .role(RoleEnum.user.name())
                    .content(prompt)
                    .ifShow(Constants.BOOLEAN.TRUE)
                    .ifContext(Constants.BOOLEAN.TRUE)
                    .ifDomainTop(Constants.BOOLEAN.FALSE)
                    .consumerToken(0)
                    .build();
            // 响应记录
            List<Image> images = response.getData();
            FileService fileService = fileServiceContext.getFileService();
            // 图片存储至系统
            List<String> imgUrlList = images.stream().map(Image::getUrl).filter(StrUtil::isNotEmpty).map(url -> {
                try (
                        InputStream inputStream = HttpUtil.createGet(url).execute().bodyStream();
                ) {
                    FileResponse fileResponse = fileService.upload(FileTypeRootEnum.image, FileHeaderImageEnum.IMAGE_PNG.getValue(), inputStream);
                    return fileResponse.getFilePath();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            String base64Json = images.stream().map(Image::getB64Json).filter(Objects::nonNull).collect(Collectors.joining(","));
            String urls = String.join(",", imgUrlList);
            SessionRecordModel responseRecordParam = SessionRecordModel.builder()
                    .sessionId(sessionId)
                    .userId(param.getUserId())
                    .role(RoleEnum.assistant.name())
                    .content(urls)
                    .drawBase64Img(base64Json)
                    .ifShow(Constants.BOOLEAN.TRUE)
                    .ifContext(Constants.BOOLEAN.TRUE)
                    .ifDomainTop(Constants.BOOLEAN.FALSE)
                    .consumerToken(0)
                    .build();
            List<SessionRecordModel> insertParams = CollUtil.newArrayList(userRecordParam, responseRecordParam);
            sessionRecordService.responseInsertHandle(param, insertParams);
        });

        return imageResult;
    }

}
