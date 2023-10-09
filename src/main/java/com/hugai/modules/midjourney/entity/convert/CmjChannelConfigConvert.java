package com.hugai.modules.midjourney.entity.convert;

import com.hugai.modules.midjourney.entity.dto.CmjChannelConfigDTO;
import com.hugai.modules.midjourney.entity.model.CmjChannelConfigModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * mj频道配置 Convert
 *
 * @author wuhao
 * @date 2023-09-25
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface CmjChannelConfigConvert extends BaseConvert<CmjChannelConfigModel, CmjChannelConfigDTO> {

    CmjChannelConfigConvert INSTANCE = Mappers.getMapper(CmjChannelConfigConvert.class);

}
