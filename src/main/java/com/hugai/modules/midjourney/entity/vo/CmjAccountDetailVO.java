package com.hugai.modules.midjourney.entity.vo;

import com.hugai.modules.midjourney.entity.model.CmjAccountModel;
import com.hugai.modules.midjourney.entity.model.CmjChannelConfigModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/9/25 16:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CmjAccountDetailVO extends CmjAccountModel {

    private List<CmjChannelConfigModel> channelConfigList;

    private List<String> channelIds;

}
