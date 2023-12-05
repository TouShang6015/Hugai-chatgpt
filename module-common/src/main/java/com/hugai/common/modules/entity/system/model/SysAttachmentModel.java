package com.hugai.common.modules.entity.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 附件管理
 *
 * @author WuHao
 * @since 2023/10/8 9:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_attachment")
public class SysAttachmentModel extends BaseModel {

    @ApiModelProperty(value = "业务主键id")
    private Long businessId;

    @ApiModelProperty(value = "原始文件名")
    private String originalFileName;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "文件唯一名称")
    private String fileNameMd5;

    @ApiModelProperty(value = "文件绝对路径")
    private String fileAbsolutePath;

    @ApiModelProperty(value = "文件后缀")
    private String fileSuffix;

}
