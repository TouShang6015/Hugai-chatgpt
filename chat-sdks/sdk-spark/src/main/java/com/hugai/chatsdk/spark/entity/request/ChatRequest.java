package com.hugai.chatsdk.spark.entity.request;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WuHao
 * @since 2023/12/27 11:32
 */
@Data
public class ChatRequest implements Serializable {

    private Header header;

    private Parameter parameter;

    private Payload payload;

    public String toString() {
        return JSON.toJSONString(this);
    }

}
