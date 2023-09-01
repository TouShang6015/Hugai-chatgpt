package com.hugai.common.constants;

/**
 * @author WuHao
 * @since 2023/9/1 15:12
 */
public interface MQConstants {

    interface Exchange {

        String deadLetter = "deadLetterExchange";

        String topic = "topicExchange";

    }

    interface Queue {

        String dle = "dle.queue";

        String sms = "queue.sms";
    }

}
