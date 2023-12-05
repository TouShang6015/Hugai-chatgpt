package com.hugai.core.midjourney.common.entity;

import lombok.Data;

/**
 * mj messageHandle响应实体存储
 *
 * @author WuHao
 * @since 2023/9/27 17:00
 */
@Data
public class MessageResponseData {

    private String imgUrl;

    private String messageId;

    private String messageHash;

}
