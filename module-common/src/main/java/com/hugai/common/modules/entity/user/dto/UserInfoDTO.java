package com.hugai.common.modules.entity.user.dto;

import com.hugai.common.modules.entity.user.model.UserInfoModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoDTO extends UserInfoModel {

    /**
     * 总消耗token
     */
    private Integer allCustomerToken;

    /**
     * 会话数
     */
    private Integer sessionCount;

}
