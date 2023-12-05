package com.hugai.common.modules.entity.config.convert;

import com.hugai.common.modules.entity.config.dto.CmjAccountDTO;
import com.hugai.common.modules.entity.config.vo.CmjAccountDetailVO;
import com.hugai.common.modules.entity.config.model.CmjAccountModel;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * mj账户配置 Convert
 *
 * @author wuhao
 * @date 2023-09-25
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface CmjAccountConvert extends BaseConvert<CmjAccountModel, CmjAccountDTO> {

    CmjAccountConvert INSTANCE = Mappers.getMapper(CmjAccountConvert.class);

    CmjAccountDetailVO convertDetailVo(CmjAccountModel model);

}
