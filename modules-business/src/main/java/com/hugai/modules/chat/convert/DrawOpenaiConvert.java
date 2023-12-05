package com.hugai.modules.chat.convert;

import com.hugai.common.modules.entity.draw.vo.openai.OpenaiTxt2ImgRequest;
import com.theokanning.openai.image.CreateImageRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author WuHao
 * @since 2023/7/17 9:49
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface DrawOpenaiConvert {

    DrawOpenaiConvert INSTANCE = Mappers.getMapper(DrawOpenaiConvert.class);

    CreateImageRequest convertApiParam(OpenaiTxt2ImgRequest param);

}
