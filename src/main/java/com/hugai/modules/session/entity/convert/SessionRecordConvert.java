package com.hugai.modules.session.entity.convert;

import com.hugai.core.openai.entity.response.api.ChatResponse;
import com.hugai.core.openai.entity.response.api.CompletionResponse;
import com.hugai.modules.session.entity.dto.SessionRecordDTO;
import com.hugai.modules.session.entity.model.SessionRecordModel;
import com.org.bebas.core.model.convert.BaseConvert;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 会话详情 Convert
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SessionRecordConvert extends BaseConvert<SessionRecordModel, SessionRecordDTO> {

    SessionRecordConvert INSTANCE = Mappers.getMapper(SessionRecordConvert.class);

    @Mappings({
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "content", target = "content")
    })
    ChatMessage convertChatMessage(SessionRecordModel param);

    List<ChatMessage> convertChatMessage(List<SessionRecordModel> param);

    @Mappings({
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "content", target = "content")
    })
    SessionRecordModel chatResponseConvertModel(ChatResponse param);

    List<SessionRecordModel> chatResponseConvertModel(List<ChatResponse> param);

    @Mappings({
            @Mapping(source = "content", target = "content")
    })
    SessionRecordModel domainResponseConvertModel(CompletionResponse param);

    List<SessionRecordModel> domainResponseConvertModel(List<CompletionResponse> param);
}
