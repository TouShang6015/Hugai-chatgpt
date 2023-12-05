package com.hugai.common.modules.entity.business.convert;

import com.hugai.common.modules.entity.business.dto.NoticeClientDTO;
import com.hugai.common.modules.entity.business.model.NoticeClientModel;
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
