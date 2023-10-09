package com.hugai.modules.midjourney.entity.dto;

import com.hugai.modules.midjourney.entity.model.CmjAccountModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * mj账户配置 Dto
 *
 * @author wuhao
 * @date 2023-09-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CmjAccountDTO extends CmjAccountModel {

    /**
     * socket连接状态
     * 1 已连接 0 未连接
     */
    private Integer socketStatus;

}
