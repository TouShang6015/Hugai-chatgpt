package com.hugai.framework.file.plugins;

import com.hugai.framework.file.service.AbstractFileHandle;
import com.org.bebas.exception.BusinessException;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

/**
 * @author WuHao
 * @since 2023/6/21 15:43
 */
public abstract class AbstractPluginsImpl extends AbstractFileHandle {


    /**
     * 文件上传操作
     *
     * <p> * 注意手动关闭流</p>
     *
     * @param prefix
     * @param fileName
     * @param inputStream
     * @return
     */
    @Override
    public void handleUpload(String prefix, String fileName, InputStream inputStream) throws IOException {
        String resourceSavePath = this.getResourceSavePath();
        String filePath = this.getFilePath(prefix, fileName);
        // 判断目录是否存在
        File directory = new File(resourceSavePath + prefix + "/" + super.getDatePath());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new BusinessException("创建目录失败" );
            }
        }
        // 写入文件
        File file = new File(filePath);
        String fileSuffix = fileName.split("\\." )[1];
        switch (Objects.requireNonNull(fileSuffix)) {
            case "md":
            case "txt":
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                while (reader.ready()) {
                    writer.write((char) reader.read());
                }
                writer.flush();
                writer.close();
                reader.close();
                break;
            default:
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
                byte[] bytes = new byte[1024];
                int length;
                while ((length = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, length);
                }
                bos.flush();
                bos.close();
                bis.close();
                break;
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@link Boolean}
     */
    @Override
    public Boolean exists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 获取文件访问url
     *
     * @param prefix   前缀
     * @param filePath 文件路径
     * @return {@link String}
     */
    @Override
    public String getFileAccessUrl(String prefix, String filePath) {
        return "/" + prefix + "/" + filePath;
    }


}
