package com.hugai.modules.system.entity.convert;

import com.hugai.modules.system.entity.dto.BaseDictTypeDTO;
import com.hugai.modules.system.entity.model.BaseDictTypeModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典类型表 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface BaseDictTypeConvert extends BaseConvert<BaseDictTypeModel, BaseDictTypeDTO> {

    BaseDictTypeConvert INSTANCE = Mappers.getMapper(BaseDictTypeConvert.class);


}
