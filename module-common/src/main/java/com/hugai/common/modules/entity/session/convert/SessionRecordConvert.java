package com.hugai.common.modules.entity.session.convert;

import com.hugai.common.modules.entity.session.dto.SessionRecordDTO;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 会话详情 Convert
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SessionRecordConvert extends BaseConvert<SessionRecordModel, SessionRecordDTO> {

    SessionRecordConvert INSTANCE = Mappers.getMapper(SessionRecordConvert.class);

}
