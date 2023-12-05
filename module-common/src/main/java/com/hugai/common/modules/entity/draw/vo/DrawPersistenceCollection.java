package com.hugai.common.modules.entity.draw.vo;

import com.hugai.common.modules.entity.session.model.SessionInfoDrawModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import lombok.Data;

import java.util.List;

/**
 * 绘图持久化集合存储实体
 *
 * @author WuHao
 * @since 2023/9/7 17:11
 */
@Data
public class DrawPersistenceCollection {

    private SessionInfoDrawModel sessionInfoDrawModelInsert;

    private List<SessionRecordDrawModel> sessionRecordDrawModelListInsert;

}
