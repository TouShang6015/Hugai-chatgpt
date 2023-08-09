package com.hugai.modules.user.entity.dto;

import com.hugai.modules.user.entity.model.UserInfoModel;
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
