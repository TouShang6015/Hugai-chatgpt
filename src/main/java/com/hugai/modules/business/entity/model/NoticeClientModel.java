package com.hugai.modules.business.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 通告信息 Model
 *
 * @author WuHao
 * @date 2023-05-26
 * @tableName tb_openai_keys
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_notice_client")
public class NoticeClientModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知标题")
    private String title;

    @ApiModelProperty(value = "通知类型")
    private String noticeType;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否显示")
    private String ifShow;

}
