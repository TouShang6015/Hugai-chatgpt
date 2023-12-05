package com.hugai.common.modules.entity.system.convert;

import com.hugai.common.modules.entity.system.model.SysAttachmentModel;
import com.hugai.common.modules.entity.system.dto.SysAttachmentDTO;
import com.org.bebas.core.model.convert.BaseConvert;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

/**
 * @author WuHao
 * @since 2023/10/8 9:17
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysAttachmentConvert extends BaseConvert<SysAttachmentModel, SysAttachmentDTO> {
}
