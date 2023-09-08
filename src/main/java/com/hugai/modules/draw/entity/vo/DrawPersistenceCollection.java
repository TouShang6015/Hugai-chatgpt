package com.hugai.modules.draw.entity.vo;

import com.hugai.modules.session.entity.model.SessionInfoDrawModel;
import com.hugai.modules.session.entity.model.SessionRecordDrawModel;
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
