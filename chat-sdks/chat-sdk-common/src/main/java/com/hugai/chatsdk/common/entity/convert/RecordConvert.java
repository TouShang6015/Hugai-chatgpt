package com.hugai.chatsdk.common.entity.convert;

import com.hugai.chatsdk.common.entity.session.RecordData;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/11/29 13:29
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface RecordConvert {

    RecordConvert INSTANCE = Mappers.getMapper(RecordConvert.class);

    RecordData sessionConvertRecord(SessionRecordModel model);

    List<RecordData> sessionConvertRecord(List<SessionRecordModel> model);

}
