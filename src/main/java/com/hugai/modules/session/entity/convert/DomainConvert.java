package com.hugai.modules.session.entity.convert;

import com.hugai.modules.session.entity.dto.DomainDTO;
import com.hugai.modules.session.entity.model.DomainModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DomainConvert extends BaseConvert<DomainModel, DomainDTO> {

    DomainConvert INSTANCE = Mappers.getMapper(DomainConvert.class);

}
