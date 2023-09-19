package com.hugai.modules.session.entity.convert;

import com.hugai.modules.session.entity.dto.SessionRecordDrawDTO;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface SessionRecordDrawConvert extends BaseConvert<SessionRecordDrawModel, SessionRecordDrawDTO> {

    SessionRecordDrawConvert INSTANCE = Mappers.getMapper(SessionRecordDrawConvert.class);

}
