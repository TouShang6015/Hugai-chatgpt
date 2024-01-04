package com.hugai.chatsdk.spark.entity.request;

import com.hugai.chatsdk.spark.entity.Text;
import lombok.Data;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/12/27 11:43
 */
@Data
public class Payload {

    private Message message;

    @Data
    public static class Message {
        private List<Text> text;
    }

}
