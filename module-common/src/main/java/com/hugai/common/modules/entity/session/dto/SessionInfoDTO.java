package com.hugai.common.modules.entity.session.dto;

import com.hugai.common.modules.entity.session.model.SessionInfoModel;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 会话表 Dto
 *
 * @author WuHao
 * @date 2023-05-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SessionInfoDTO extends SessionInfoModel {

    private String userName;

    private String ifTourist;

    private String userIpAddress;

    private String email;

    private List<SessionRecordModel> recordList;

}
