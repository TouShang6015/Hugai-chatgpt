package com.hugai.framework.file.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传响应键
 *
 * @author WuHao
 * @since 2023/5/30 15:32
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileResponse {

    /**
     * 文件相对路径
     */
    private String filePath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String fileSuffix;

}
