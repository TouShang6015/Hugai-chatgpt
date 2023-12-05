package com.hugai.common.modules.entity.session.convert;

import com.hugai.common.modules.entity.session.dto.DomainDTO;
import com.hugai.common.modules.entity.session.model.DomainModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DomainConvert extends BaseConvert<DomainModel, DomainDTO> {

    DomainConvert INSTANCE = Mappers.getMapper(DomainConvert.class);

}
