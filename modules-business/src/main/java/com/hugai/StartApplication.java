package com.hugai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author WuHao
 * @since 2023/5/22 9:14
 */
@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);

        System.out.println("启动成功，帮助文档地址：http://chat.doc.equinox19.xyz/");
    }

}
