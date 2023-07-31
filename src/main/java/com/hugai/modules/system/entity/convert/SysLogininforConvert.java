package com.hugai.modules.system.entity.convert;

import com.hugai.modules.system.entity.dto.SysLogininforDTO;
import com.hugai.modules.system.entity.model.SysLogininforModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统访问记录 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysLogininforConvert extends BaseConvert<SysLogininforModel, SysLogininforDTO> {

    SysLogininforConvert INSTANCE = Mappers.getMapper(SysLogininforConvert.class);

}
