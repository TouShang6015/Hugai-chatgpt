package com.hugai.framework.file.plugins.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hugai.framework.file.constants.FileStrategyEnum;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.plugins.AbstractPluginsImpl;
import com.hugai.modules.system.entity.model.SysMinioSecretModel;
import com.hugai.modules.system.service.SysMinioSecretService;
import com.org.bebas.exception.BusinessException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 插件 七牛云 策略实现
 *
 * @author wuhao
 * @date 2022/9/23 10:21
 */
@Slf4j
@Service
public class QiniuPluginsImpl extends AbstractPluginsImpl {

    protected SysMinioSecretModel minioSecretConfig;

    private Configuration configuration;

    private Auth auth;

    private UploadManager uploadManager;

    @Autowired
    public QiniuPluginsImpl(SysMinioSecretService sysMinioSecretService) {
        this.minioSecretConfig = sysMinioSecretService.getStrategySecretConfig(FileStrategyEnum.qiniu.name());
        if (Objects.nonNull(this.minioSecretConfig)) {
            this.auth = Auth.create(this.minioSecretConfig.getAccessKey(), this.minioSecretConfig.getSecretKey());
            this.configuration = new Configuration(Region.autoRegion());
            this.uploadManager = new UploadManager(this.configuration);
        }else{
            log.warn("[七牛云]文件存储策略配置参数未找到！");
        }
    }

    /**
     * 策略标识
     *
     * @return
     */
    @Override
    public FileStrategyEnum strategy() {
        return FileStrategyEnum.qiniu;
    }

    @Override
    public FileResponse upload(FileTypeRootEnum fileTypeRoot, String suffix, InputStream inputStream) {
        String fileType = fileTypeRoot.name();
        try {
            if (StrUtil.isEmpty(suffix)) {
                throw new IOException("文件后缀为空！");
            }
            // 将文件流转为字符串
            String md5 = UUID.fastUUID().toString(true);
//            String md5 = super.getMd5(inputStream);
            // 文件名称
            String fileName = super.extractFilename(md5, suffix);
            // 获取文件路径
            String filePath = this.getFilePath(fileType, fileName);

            Response response = uploadManager.put(inputStream, this.fileSize, filePath, this.getToken(), null, null, true);
            String responseFilePath;
            if (response.isOK() && response.isJson()) {
                responseFilePath = (String) JSONObject.parseObject(response.bodyString()).get("key");
            } else {
                throw new BusinessException();
            }
            log.info("[七牛云]文件上传响应：{}",JSON.toJSONString(response));

            String finalFilePath = this.minioSecretConfig.getDataHandleDomain() + "/" + responseFilePath;

            String tokenFilePath = auth.privateDownloadUrl(finalFilePath,36000);

            String s = StrUtil.subAfter(tokenFilePath, this.minioSecretConfig.getDataHandleDomain(), true);
            FileResponse fileResponse = FileResponse.builder()
                    .fileName(md5 + suffix)
                    .filePath(s)
                    .fileSuffix(suffix)
                    .build();
            log.info("图片上传最终响应：{}",JSON.toJSONString(fileResponse));
            return fileResponse;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件上传失败！");
        }
    }

    private String getToken() {
        return auth.uploadToken(this.minioSecretConfig.getBucketName());
    }

    /**
     * 获取资源存储路径
     *
     * @return
     */
    @Override
    protected String getResourceSavePath() {
        return "";
    }
}
