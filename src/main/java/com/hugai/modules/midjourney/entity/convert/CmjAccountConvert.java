package com.hugai.modules.midjourney.entity.convert;

import com.hugai.modules.midjourney.entity.dto.CmjAccountDTO;
import com.hugai.modules.midjourney.entity.model.CmjAccountModel;
import com.hugai.modules.midjourney.entity.vo.CmjAccountDetailVO;
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
