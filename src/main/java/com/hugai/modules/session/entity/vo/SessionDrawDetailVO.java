package com.hugai.modules.session.entity.vo;

import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 绘图会话详情实体
 *
 * @author WuHao
 * @since 2023/9/12 9:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SessionDrawDetailVO extends SessionInfoDrawModel {

    private List<SessionRecordDrawModel> recordDrawList;

}
