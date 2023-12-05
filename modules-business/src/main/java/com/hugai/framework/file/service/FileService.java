package com.hugai.framework.file.service;

import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.plugins.Plugins;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件管理业务接口
 *
 * @author wuhao
 * @date 2022/9/23 10:14
 */
public interface FileService extends FileConfigCacheConfig, Plugins {

    /**
     * 文件上传
     *
     * @param file
     * @param fileTypeRoot
     * @return
     */
    FileResponse upload(MultipartFile file, FileTypeRootEnum fileTypeRoot, String[] allowedType);

    /**
     * 文件上传
     *
     * <p>* 请手动关闭流</p>
     *
     * @param fileTypeRoot
     * @param suffix
     * @param inputStream
     * @return
     */
    FileResponse upload(FileTypeRootEnum fileTypeRoot, String suffix, InputStream inputStream);

    /**
     * 文件下载
     *
     * @param path
     * @param outputStream
     */
    default String download(String path, OutputStream outputStream) throws IOException {
        return download(path, outputStream, true);
    }

    String download(String path, OutputStream outputStream, boolean absolutePath) throws IOException;


}
