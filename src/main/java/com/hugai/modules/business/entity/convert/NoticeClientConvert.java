package com.hugai.modules.business.entity.convert;

import com.hugai.modules.business.entity.dto.NoticeClientDTO;
import com.hugai.modules.business.entity.model.NoticeClientModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * NoticeClient Convert
 *
 * @author WuHao
 * @date 2023-05-26
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface NoticeClientConvert extends BaseConvert<NoticeClientModel, NoticeClientDTO> {

    NoticeClientConvert INSTANCE = Mappers.getMapper(NoticeClientConvert.class);

}
