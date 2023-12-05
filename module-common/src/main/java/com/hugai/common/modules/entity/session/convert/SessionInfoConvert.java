package com.hugai.common.modules.entity.session.convert;

import com.hugai.common.modules.entity.session.dto.SessionInfoDTO;
import com.hugai.common.modules.entity.session.model.SessionInfoModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 会话表 Convert
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SessionInfoConvert extends BaseConvert<SessionInfoModel, SessionInfoDTO> {

    SessionInfoConvert INSTANCE = Mappers.getMapper(SessionInfoConvert.class);

}
