package com.hugai.core.drawTask.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * @author WuHao
 * @since 2023/10/8 11:35
 */
@Data
public class CreateTaskParam {

    List<MultipartFile> fileList;

    HashMap<String, Object> paramMap;

}
