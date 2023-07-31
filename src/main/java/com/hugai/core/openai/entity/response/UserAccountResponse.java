package com.hugai.core.openai.entity.response;

import com.hugai.core.openai.model.account.Usage;
import com.hugai.core.openai.model.account.UserGrants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/7/4 14:39
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccountResponse {

    private Usage usage;

    private UserGrants userGrants;

    private String apiKey;

    // 使用情况
    private List<UsableCondition> usableConditionList;

    // 总额
    private Double total;
    // 已用
    private Double totalUsage;
    // 余额
    private Double totalBalance;

    @NoArgsConstructor
    @Data
    public static class UsableCondition{
        // 日期
        private String time;
        // 消耗
        private Double totalCost;
    }

}
