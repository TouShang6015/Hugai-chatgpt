package com.hugai.core.chat.sessionType.strategy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hugai.chatsdk.common.utils.TokenCalculateUtil;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.ChatRole;
import com.hugai.common.enums.SessionType;
import com.hugai.common.modules.entity.session.model.DomainModel;
import com.hugai.common.modules.entity.session.model.SessionRecordModel;
import com.org.bebas.core.function.OR;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * domain 会话类型策略实现
 *
 * @author WuHao
 * @since 2023/11/30 14:49
 */
@Service
public class SessionDomainBusinessServiceStrategy extends SessionBusinessCommon {

    @Override
    public SessionType type() {
        return SessionType.domain;
    }

    @Override
    public List<SessionRecordModel> createSessionBeforeAddRecord(DomainModel domainModel) {
        List<SessionRecordModel> list = CollUtil.newArrayList();
        // 顶部内容
        OR.run(domainModel.getAboveContent(), StrUtil::isNotEmpty, aboveContent -> {
            SessionRecordModel model = new SessionRecordModel();
            model.setDomainUniqueKey(domainModel.getUniqueKey());
            model.setRole(ChatRole.user.name());
            model.setContent(aboveContent);
            model.setIfShow(Constants.BOOLEAN.FALSE);
            model.setIfContext(Constants.BOOLEAN.TRUE);
            model.setIfDomainTop(Constants.BOOLEAN.TRUE);
            model.setConsumerToken(TokenCalculateUtil.getTokenNumOfContent(aboveContent));
            list.add(model);
        });
        OR.run(domainModel.getFirstContent(), StrUtil::isNotEmpty, firstContent -> {
            SessionRecordModel model = new SessionRecordModel();
            model.setDomainUniqueKey(domainModel.getUniqueKey());
            model.setRole(ChatRole.assistant.name());
            model.setContent(firstContent);
            model.setIfShow(Constants.BOOLEAN.TRUE);
            model.setIfContext(Constants.BOOLEAN.FALSE);
            model.setIfDomainTop(Constants.BOOLEAN.FALSE);
            model.setConsumerToken(TokenCalculateUtil.getTokenNumOfContent(firstContent));
            list.add(model);
        });
        return list;
    }
}
