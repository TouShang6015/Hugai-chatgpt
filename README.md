# hugai-chatgpt-server

## 介绍

HugAi是由Springboot集成openAi SDK开发的一套智能AI知识库, 公益项目，为爱发电🤭。

- HugAi后端源码地址：
    - [https://gitee.com/toushang6015/hugai-chatgpt](https://gitee.com/toushang6015/hugai-chatgpt)
    - [https://github.com/TouShang6015/hugai-chatgpt](https://github.com/TouShang6015/hugai-chatgpt)

- HugAi前台源码地址：
    - [https://gitee.com/toushang6015/hugai-chatgpt-ui](https://gitee.com/toushang6015/hugai-chatgpt-ui)
    - [https://github.com/TouShang6015/hugai-chatgpt-ui](https://github.com/TouShang6015/hugai-chatgpt-ui)
    
- 演示地址：[http://chat.equinox19.xyz/home](http://chat.equinox19.xyz/home)
- **帮助文档**：[http://chat.doc.equinox19.xyz/](http://chat.doc.equinox19.xyz/)


- 我的博客：[https://www.equinox19.xyz/](https://www.equinox19.xyz/)

> 特别提醒：部署项目请查看帮助文档

## 技术栈

项目有很多设计模式、代码抽象的实际应用、aop代码解耦等，以及java8特性，流式编程、函数式接口等相关技能点。

### 后端

- 语言与框架
  - Java 8
  - Springboot2.7+
  - Maven 3.5+
  - mybatis-plus
- 数据库：
  - Mysql 8.0
  - Redis
- 核心技术
  - Java8+特性，流、函数式接口、Optional等
  - sse消息推送
  - 分布式锁redission
  - anji Captcha验证码
  - minio 云文件存储
  - SpringSecurity 鉴权、多用户登陆
  - okhttp
  - 双端队列
  - mapstruct
- 第三方sdk
  - openai sdk
  - minio 七牛云

### 前端

- vue2
- element-ui
- mavon-editor
- pace-js
- echarts

## 待办

有空余时间就会来维护项目

|   功能    |                  描述                  |
|:-------:|:------------------------------------:|
| 管理端模块功能 | 管理端目前只有站点配置、领域会话维护，后续会添加用户模块、首页统计等功能 |
|  sd画图   |    正在研究，目前使用的是openai的画图sdk，不怎么好用     |
|   黑化    |       prompt中文大合集中有讨论猫娘的，懂我意思吧       |
| ....... |                                      |

## 项目截图

![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691027744983.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691028127389.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691028170014.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691028204255.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691030140010.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691030163684.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691030196637.jpg)

### 联系方式

优先查看 [帮助文档](http://chat.doc.equinox19.xyz/) ，如有疑问或宝贵的意见，欢迎加入讨论群一起交流🤤

- 讨论群：747193904

> 觉得项目不错可以帮我star下仓库嘛，非常感谢🧎‍♂️~