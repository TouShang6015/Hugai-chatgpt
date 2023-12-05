package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.dto.BaseResourceConfigDTO;
import com.hugai.common.modules.entity.system.model.BaseResourceConfigModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统参数配置表 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface BaseResourceConfigConvert extends BaseConvert<BaseResourceConfigModel, BaseResourceConfigDTO> {

    BaseResourceConfigConvert INSTANCE = Mappers.getMapper(BaseResourceConfigConvert.class);

}
