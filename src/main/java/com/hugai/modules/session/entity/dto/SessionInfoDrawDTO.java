package com.hugai.modules.session.entity.dto;

import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/8/8 15:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SessionInfoDrawDTO extends SessionInfoDrawModel {

    List<SessionRecordDrawModel> recordList;

}
