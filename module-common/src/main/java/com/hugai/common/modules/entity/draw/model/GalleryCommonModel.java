package com.hugai.common.modules.entity.draw.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 公开画廊
 *
 * @author WuHao
 * @since 2023-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_gallery_common")
public class GalleryCommonModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "绘图会话ID")
    private Long sessionInfoDrawId;

    @ApiModelProperty(value = "绘图会话详情ID")
    private Long sessionRecordDrawId;

    @ApiModelProperty(value = "图片url")
    private String imgUrl;

    @ApiModelProperty(value = "是否展示会话下所有")
    private String ifShowAll;

    @ApiModelProperty(value = "提示词")
    private String prompt;

}
