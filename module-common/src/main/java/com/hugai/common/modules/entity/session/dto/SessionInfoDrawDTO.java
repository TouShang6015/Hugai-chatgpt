package com.hugai.common.modules.entity.session.dto;

import com.hugai.common.modules.entity.session.model.SessionInfoDrawModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
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
