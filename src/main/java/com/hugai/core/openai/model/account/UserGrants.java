package com.hugai.core.openai.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WuHao
 * @since 2023/7/4 13:15
 */
@NoArgsConstructor
@Data
public class UserGrants {
    @JsonProperty("object")
    private String object;
    @JsonProperty("has_payment_method")
    private Boolean hasPaymentMethod;
    @JsonProperty("canceled")
    private Boolean canceled;
    @JsonProperty("access_until")
    private Integer accessUntil;
    @JsonProperty("soft_limit")
    private Integer softLimit;
    @JsonProperty("hard_limit")
    private Integer hardLimit;
    @JsonProperty("system_hard_limit")
    private Integer systemHardLimit;
    @JsonProperty("soft_limit_usd")
    private Double softLimitUsd;
    @JsonProperty("hard_limit_usd")
    private Double hardLimitUsd;
    @JsonProperty("system_hard_limit_usd")
    private Double systemHardLimitUsd;
    @JsonProperty("plan")
    private Plan plan;
    @JsonProperty("primary")
    private Boolean primary;
    @JsonProperty("account_name")
    private String accountName;

    @NoArgsConstructor
    @Data
    public static class Plan {
        @JsonProperty("title")
        private String title;
        @JsonProperty("id")
        private String id;
    }
}
