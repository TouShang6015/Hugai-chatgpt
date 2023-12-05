package com.hugai.framework.file.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.hugai.framework.file.FileUtil;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.common.modules.entity.system.model.SysFileConfigModel;
import com.org.bebas.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author wuhao
 * @date 2022/9/23 10:28
 */
@Slf4j
public abstract class AbstractFileHandle extends AbstractFillUtil implements FileService {

    protected SysFileConfigModel fileConfig;

    protected long fileSize = 0L;

    /**
     * 文件上传
     *
     * @param file         文件
     * @param fileTypeRoot 文件保存类型
     * @return
     */
    @Override
    public FileResponse upload(MultipartFile file, FileTypeRootEnum fileTypeRoot, String[] allowedType) {
        // 文件校验
        super.assertAllowed(file, allowedType);
        this.fileSize = file.getSize();
        try (InputStream inputStream = file.getInputStream()) {
            // 文件后缀 eg： .jpg
            String fileSuffix = FileUtil.getFileSuffix(file.getOriginalFilename());
            return this.upload(fileTypeRoot, fileSuffix, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件上传失败！");
        }
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
            if (!exists(filePath)) {
                handleUpload(fileType, fileName, inputStream);
            }
            String fileAccessUrl = this.getFileAccessUrl(fileType, fileName);
            return FileResponse.builder()
                    .fileName(md5 + suffix)
                    .filePath(fileAccessUrl)
                    .fileSuffix(suffix)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件上传失败！");
        }
    }

    /**
     * 文件下载
     *
     * @param path
     * @param outputStream
     * @param absolutePath
     */
    @Override
    public String download(String path, OutputStream outputStream, boolean absolutePath) throws IOException {
        String filePath = absolutePath ? path : this.getResourceSavePath() + path;
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件下载失败！");
        } finally {
            IOUtils.close(outputStream);
            IOUtils.close(fis);
        }
    }

    /**
     * 获取资源存储路径
     *
     * @return
     */
    protected String getResourceSavePath() {
        return fileConfig.getSavePath();
    }

    /**
     * 获取文件的本地路径
     *
     * @param prefix
     * @param fileName
     * @return
     */
    protected String getFilePath(String prefix, String fileName) {
        return this.getResourceSavePath() + prefix + "/" + fileName;
    }

    /**
     * 文件上传操作
     *
     * @param prefix
     * @param fileName
     * @param inputStream
     * @return
     * @throws IOException
     */
    protected abstract void handleUpload(String prefix, String fileName, InputStream inputStream) throws IOException;

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@link Boolean}
     */
    protected abstract Boolean exists(String filePath);

    /**
     * 获取文件访问url
     *
     * @param filePath 文件路径
     * @return {@link String}
     */
    public abstract String getFileAccessUrl(String prefix, String filePath);

    /**
     * 设置sysFileConfigModel
     *
     * @param fileConfig
     */
    @Override
    public void setFileConfig(SysFileConfigModel fileConfig) {
        this.fileConfig = fileConfig;
    }

    /**
     * 获取配置
     *
     * @return
     */
    @Override
    public SysFileConfigModel getFileConfig() {
        return this.fileConfig;
    }
}
