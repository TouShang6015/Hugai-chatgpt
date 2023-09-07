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

        /**
         * 短信队列
         */
        String sms = "queue.sms";
        /**
         * openai绘图队列
         */
        String draw_openai = "queue.draw.openai";
        /**
         * sd绘图队列
         */
        String draw_sd = "queue.draw.sd";
    }

}
