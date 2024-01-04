package com.hugai.chatsdk.spark.entity.response;

import lombok.Data;

@Data
public class Header {
    private int code;
    private int status;
    private String sid;
}