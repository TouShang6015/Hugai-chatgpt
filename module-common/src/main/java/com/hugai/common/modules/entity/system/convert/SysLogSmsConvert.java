package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.dto.SysLogSmsDTO;
import com.hugai.common.modules.entity.system.model.SysLogSmsModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 验证码日志 Convert
 *
 * @author wuhao
 * @date 2023-12-07
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysLogSmsConvert extends BaseConvert<SysLogSmsModel, SysLogSmsDTO> {

    SysLogSmsConvert INSTANCE = Mappers.getMapper(SysLogSmsConvert.class);

}
