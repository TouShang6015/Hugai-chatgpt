package com.hugai.core.openai.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/7/4 14:37
 */
@NoArgsConstructor
@Data
public class Usage {

    @JsonProperty("object")
    private String object;
    @JsonProperty("daily_costs")
    private List<DailyCosts> dailyCosts;
    @JsonProperty("total_usage")
    private Double totalUsage;

    @NoArgsConstructor
    @Data
    public static class DailyCosts {
        @JsonProperty("timestamp")
        private Long timestamp;
        @JsonProperty("line_items")
        private List<LineItems> lineItems;

        @NoArgsConstructor
        @Data
        public static class LineItems {
            @JsonProperty("name")
            private String name;
            @JsonProperty("cost")
            private Double cost;
        }
    }
}
