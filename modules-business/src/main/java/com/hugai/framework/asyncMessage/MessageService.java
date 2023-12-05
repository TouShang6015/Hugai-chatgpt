package com.hugai.framework.asyncMessage;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.enums.ChannelEnum;

/**
 * 异步消息发送接口
 *
 * @author wuhao
 * @date 2022/8/25 17:53
 */
public interface MessageService {

    /**
     * send发送消息
     *
     * @param channel 通道id
     */
    void send(ChannelEnum channel, String data);

    default <T> void send(ChannelEnum channel, T data) {

        Assert.notNull(data);

        this.send(channel, JSON.toJSONString(data));
    }

}
