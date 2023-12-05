package com.hugai.common.modules.entity.session.convert;

import com.hugai.common.modules.entity.session.dto.SessionRecordDrawDTO;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface SessionRecordDrawConvert extends BaseConvert<SessionRecordDrawModel, SessionRecordDrawDTO> {

    SessionRecordDrawConvert INSTANCE = Mappers.getMapper(SessionRecordDrawConvert.class);

}
