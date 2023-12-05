package com.hugai.common.modules.entity.session.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import com.org.bebas.core.validator.group.GroupInsert;
import com.org.bebas.core.validator.group.GroupUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * 领域会话
 *
 * @author WuHao
 * @since 2023-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_domain")
public class DomainModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "唯一标识不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "唯一标识")
    private String uniqueKey;

    @NotEmpty(message = "上文内容不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "上文内容")
    private String aboveContent;

    @ApiModelProperty(value = "排序号")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "跳转路由（前端跳转）")
    private String routePath;

    @ApiModelProperty(value = "显示名称")
    private String iconName;

    @ApiModelProperty(value = "图片路径（本地）")
    private String iconPath;

    @ApiModelProperty(value = "窗口会话数据json")
    private String windowData;

    @NotEmpty(message = "领域类型不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "领域类型（domain_type）")
    private String type;

    @ApiModelProperty(value = "是否显示")
    private String ifShow;

    @ApiModelProperty(value = "首次会话内容")
    private String firstContent;

    @ApiModelProperty(value = "是否桌面显示")
    private String ifDeskShow;

}
