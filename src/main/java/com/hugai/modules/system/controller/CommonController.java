package com.hugai.modules.system.controller;

import cn.hutool.core.util.StrUtil;
import com.hugai.common.constants.ApiPrefixConstant;
import com.hugai.framework.file.FileUtil;
import com.hugai.framework.file.constants.FileStrategyEnum;
import com.hugai.framework.file.constants.FileTypeConstants;
import com.hugai.framework.file.constants.FileTypeRootEnum;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.framework.file.entity.FileResponse;
import com.hugai.framework.file.service.FileService;
import com.hugai.modules.system.entity.model.SysAttachmentModel;
import com.hugai.modules.system.entity.model.SysFileConfigModel;
import com.hugai.modules.system.service.ISysAttachmentService;
import com.hugai.modules.system.service.SysFileConfigService;
import com.org.bebas.core.flowenum.utils.FlowEnumUtils;
import com.org.bebas.core.label.LabelOption;
import com.org.bebas.exception.BusinessException;
import com.org.bebas.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author WuHao
 * @since 2023/6/26 17:02
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping(ApiPrefixConstant.Common.COMMON)
@RestController
@Api(value = "Common", tags = "通用控制器")
public class CommonController {

    @Resource
    private FileServiceContext fileUploadContext;
    @Resource
    private ISysAttachmentService attachmentService;
    @Resource
    private FileServiceContext fileServiceContext;
    @Resource
    private SysFileConfigService sysFileConfigService;

    @ApiOperation(value = "获取枚举下拉")
    @GetMapping("/getEnumLabel/{key}")
    public Result getEnumLabel(@PathVariable("key") String key) {
        String classPath = "com.hugai.common.enums.flow";
        List<LabelOption<String, String>> optionList = FlowEnumUtils.getOptionList(classPath + "." + key);
        return Result.success(optionList);
    }


    /**
     * 通用下载请求
     *
     * @param path         文件相对路径
     * @param downloadName 文件下载名称
     * @param delete       是否删除
     */
    @ApiOperation(value = "通用下载请求", notes = "通用下载请求", httpMethod = "GET")
    @GetMapping("/download")
    public void download(String path, String downloadName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        FileService fileService = fileUploadContext.getFileService();
        try {
            if (!FileUtil.checkAllowDownload(path)) {
                throw new Exception(StrUtil.format("文件名称({})非法，不允许下载。 ", path));
            }
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtil.setAttachmentResponseHeader(response, downloadName);
            String filePath = fileService.download(path, response.getOutputStream());
            if (delete) {
                FileUtil.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @ApiOperation(value = "通用上传请求（单个）", notes = "通用上传请求（单个）", httpMethod = "POST", response = Result.class)
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        FileService fileService = fileUploadContext.getFileService();
        try {
            String suffix = FileUtil.getFileSuffix(file.getOriginalFilename(), true);
            FileTypeRootEnum fileTypeRoot = FileTypeRootEnum.getTypeRootBySuffix(suffix);
            // 上传文件
            FileResponse fileResponse = fileService.upload(file, fileTypeRoot, FileTypeConstants.ALL_EXTENSION);

            String url = fileResponse.getFilePath();
            return Result.success()
                    .put("url", url)
                    .put("path", fileResponse.getFilePath())
                    .put("originalFilename", file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("文件上传失败");
        }
    }

    @ApiOperation(value = "图片上传")
    @PostMapping("/uploadImage")
    public Result uploadImage(MultipartFile file) throws Exception {
        FileService fileService = fileUploadContext.getFileService();
        try {
            String suffix = FileUtil.getFileSuffix(file.getOriginalFilename(), true);
            FileTypeRootEnum fileTypeRoot = FileTypeRootEnum.getTypeRootBySuffix(suffix);
            FileResponse fileResponse = fileService.upload(file, fileTypeRoot, FileTypeConstants.IMAGE_EXTENSION);
            return Result.success(fileResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("文件上传失败");
        }
    }

    @ApiOperation(value = "头像上传")
    @PostMapping("/uploadHeadImage")
    public Result uploadHeadImage(MultipartFile file) throws Exception {
        int maxSize = 1024 * 1024;
        long size = file.getSize();
        if (size > maxSize) {
            throw new BusinessException(StrUtil.format("请上传图片大小小于{}M的图片", 1));
        }
        FileService fileService = fileUploadContext.getFileService();
        try {
            String suffix = FileUtil.getFileSuffix(file.getOriginalFilename(), true);
            FileTypeRootEnum fileTypeRoot = FileTypeRootEnum.getTypeRootBySuffix(suffix);
            FileResponse fileResponse = fileService.upload(file, fileTypeRoot, FileTypeConstants.IMAGE_EXTENSION);
            return Result.success(fileResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("文件上传失败");
        }
    }

    @ApiOperation(value = "图片上传至附件管理")
    @PostMapping("/uploadImageAttachment")
    public Result uploadImageBase64(MultipartFile file) throws Exception {
        int maxSize = 1024 * 1024 * 4;
        long size = file.getSize();
        if (size > maxSize) {
            throw new BusinessException(StrUtil.format("请上传图片大小小于{}M的图片", 4));
        }

        FileService fileService;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            fileService = fileServiceContext.getFileService(FileStrategyEnum.local.name());
        } else {
            fileService = fileServiceContext.getFileService(FileStrategyEnum.server.name());
        }

        String suffix = FileUtil.getFileSuffix(file.getOriginalFilename(), true);
        FileTypeRootEnum fileTypeRoot = FileTypeRootEnum.getTypeRootBySuffix(suffix);
        FileResponse fileResponse = fileService.upload(file, fileTypeRoot, FileTypeConstants.IMAGE_EXTENSION);

        SysFileConfigModel fileConfigModel = sysFileConfigService.getByUniqueKye(fileService.strategy().name());

        SysAttachmentModel attachmentModel = SysAttachmentModel.builder()
                .originalFileName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .fileSuffix(fileResponse.getFileSuffix())
                .fileNameMd5(fileResponse.getFileName())
                .fileAbsolutePath(FilenameUtils.normalize(fileConfigModel.getSavePath() + fileResponse.getFilePath()))
                .build();
        attachmentService.save(attachmentModel);

        return Result.success(attachmentModel.getId());
    }

}
