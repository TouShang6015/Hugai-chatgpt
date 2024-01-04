package com.hugai.chatsdk.entity.session;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WuHao
 * @since 2023/11/28 9:48
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordData {

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色", dataType = "String")
    private String role;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;

    private Long customerToken;

    private StringBuilder contentSB = new StringBuilder();

}
