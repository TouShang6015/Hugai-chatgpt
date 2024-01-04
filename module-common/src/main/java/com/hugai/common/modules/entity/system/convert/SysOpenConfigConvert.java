package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.dto.SysOpenConfigDTO;
import com.hugai.common.modules.entity.system.model.SysOpenConfigModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 第三方配置 Convert
 *
 * @author wuhao
 * @date 2023-12-20
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysOpenConfigConvert extends BaseConvert<SysOpenConfigModel, SysOpenConfigDTO> {

    SysOpenConfigConvert INSTANCE = Mappers.getMapper(SysOpenConfigConvert.class);

}
