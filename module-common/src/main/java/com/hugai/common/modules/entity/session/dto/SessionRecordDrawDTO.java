package com.hugai.common.modules.entity.session.dto;

import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图像会话详情 Model
 *
 * @author WuHao
 * @date 2023-05-29
 * @tableName tb_session_record_draw
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SessionRecordDrawDTO extends SessionRecordDrawModel {

    private String userName;

}
