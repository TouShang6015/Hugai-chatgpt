package com.hugai.chatsdk.spark.entity.response;

import com.hugai.chatsdk.spark.entity.Text;
import lombok.Data;

import java.util.List;

@Data
public class Choices {

    private List<Text> text;

}