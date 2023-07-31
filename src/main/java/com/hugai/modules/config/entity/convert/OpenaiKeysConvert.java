package com.hugai.modules.config.entity.convert;

import com.hugai.modules.config.entity.dto.OpenaiKeysDTO;
import com.hugai.modules.config.entity.model.OpenaiKeysModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * apikeys Convert
 *
 * @author WuHao
 * @date 2023-05-26
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface OpenaiKeysConvert extends BaseConvert<OpenaiKeysModel, OpenaiKeysDTO> {

    OpenaiKeysConvert INSTANCE = Mappers.getMapper(OpenaiKeysConvert.class);

}
