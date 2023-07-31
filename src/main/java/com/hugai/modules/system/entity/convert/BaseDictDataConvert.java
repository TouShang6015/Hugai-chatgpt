package com.hugai.modules.system.entity.convert;

import com.hugai.modules.system.entity.dto.BaseDictDataDTO;
import com.hugai.modules.system.entity.model.BaseDictDataModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典数据表 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface BaseDictDataConvert extends BaseConvert<BaseDictDataModel, BaseDictDataDTO> {

    BaseDictDataConvert INSTANCE = Mappers.getMapper(BaseDictDataConvert.class);

}
