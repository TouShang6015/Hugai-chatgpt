package com.hugai.common.modules.entity.config.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.org.bebas.core.model.BaseModel;
import com.org.bebas.core.validator.group.GroupInsert;
import com.org.bebas.core.validator.group.GroupUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * mj频道配置 Model
 *
 * @author wuhao
 * @date 2023-09-25
 * @tableName tb_cmj_channel
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_cmj_channel_config")
public class CmjChannelConfigModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 关联账户
     */
    @NotNull(message = "关联账户不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "关联账户", dataType = "Long")
    private Long cmjAccountId;
    /**
     * 服务器id
     */
    @NotEmpty(message = "服务器id不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "服务器id", dataType = "String")
    private String guildId;
    /**
     * 频道id
     */
    @NotEmpty(message = "频道id不能为空", groups = {GroupInsert.class, GroupUpdate.class})
    @ApiModelProperty(value = "频道id", dataType = "String")
    private String channelId;

}
