package com.hugai.modules.system.entity.convert;

import com.hugai.modules.system.entity.dto.SysAttachmentDTO;
import com.hugai.modules.system.entity.model.SysAttachmentModel;
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
