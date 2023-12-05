package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.dto.SysOperLogDTO;
import com.hugai.common.modules.entity.system.model.SysOperLogModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 操作日志记录 Convert
 *
 * @author wuHao
 * @date 2022-10-14 15:13:02
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysOperLogConvert extends BaseConvert<SysOperLogModel, SysOperLogDTO> {

    SysOperLogConvert INSTANCE = Mappers.getMapper(SysOperLogConvert.class);

}
