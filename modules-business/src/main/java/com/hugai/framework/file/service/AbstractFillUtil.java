package com.hugai.framework.file.service;

import cn.hutool.core.util.StrUtil;
import com.hugai.config.properties.FileConfig;
import com.hugai.framework.file.FileUtil;
import com.hugai.framework.file.constants.FileTypeConstants;
import com.org.bebas.exception.BusinessException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Objects;

/**
 * @author wuhao
 * @date 2022/9/23 10:40
 */
public abstract class AbstractFillUtil {

    /**
     * 获取文件md5值
     *
     * @param inputStream 文件输入流
     * @return {@link String} 文件md5值
     */
    protected String getMd5(InputStream inputStream) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(md5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     */
    public void assertAllowed(MultipartFile file, String[] allowedExtension) {
        int maxSize = FileConfig.getMaxUploadSize() * 1024 * 1024;
        long size = file.getSize();
        if (size > maxSize) {
            throw new BusinessException(StrUtil.format("文件大小不能大于{}M", maxSize));
        }
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileConfig.getMaxFileNameLength()) {
            throw new BusinessException("file异常，默认文件名长度不大于" + FileConfig.getMaxFileNameLength());
        }

        String fileName = file.getOriginalFilename();
        String suffix = FileUtil.getFileSuffix(file.getOriginalFilename(), true);
        if (allowedExtension != null && !isAllowedExtension(suffix, allowedExtension) && allowedExtension != FileTypeConstants.ALL_EXTENSION) {
            throw new BusinessException(StrUtil.format("file异常-文件名：[{}] 图片可用格式{}", fileName, allowedExtension));
        }

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param suffix
     * @param allowedExtension
     * @return
     */
    public boolean isAllowedExtension(String suffix, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据当前日期生成文件目录
     *
     * @return
     */
    protected String getDatePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 编码文件名
     */
    public String extractFilename(String fileName, String suffix) throws IOException {
        return StrUtil.format("{}/{}{}", getDatePath(), fileName, suffix);
    }

}
