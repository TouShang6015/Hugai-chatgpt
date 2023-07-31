package com.hugai.framework.file.constants;

/**
 * @author wuhao
 * @date 2022/9/23 15:10
 */
public interface FileTypeConstants {


    String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "docxf", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb",
            // pdf
            "pdf"};

    /**
     * 允许的图片后缀
     */
    String[] IMAGE_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png"};
    /**
     * 允许的flash
     */
    String[] FLASH_EXTENSION = {"swf", "flv"};
    /**
     * 允许的media
     */
    String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"};
    /**
     * 允许的视频
     */
    String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb"};

    /**
     * 允许的文档
     */
    String[] DOCUMENT_EXTENSION = {"doc", "docx", "docxf", "xls", "xlsx"};

    /**
     * 全部允许
     */
    String[] ALL_EXTENSION = {"*"};


}
