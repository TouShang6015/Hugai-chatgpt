<div align="center">
    <a href="http://chat.equinox19.xyz/"><img src="http://chat.static.equinox19.xyz/hugai-doc/HugAiLogo1-icon.png"/></a>
</div>

<div align="center">
    <a href="https://github.com/TouShang6015/Hugai-chatgpt/stargazers">
        <img src="https://img.shields.io/github/stars/TouShang6015/Hugai-chatgpt.svg" />
    </a>
    <a href="http://chat.equinox19.xyz/" target="_blank">
        <img alt="GitHub forks" src="https://img.shields.io/github/forks/TouShang6015/Hugai-chatgpt?color=eb6ea5">
    </a>
    <a href="http://chat.equinox19.xyz/" target="_blank">
        <img src="https://img.shields.io/badge/HugAi-ChatGpt-blue.svg">
    </a>
    <a href="http://chat.equinox19.xyz/" target="_blank">
        <img src="https://img.shields.io/github/license/TouShang6015/Hugai-chatgpt" />
    </a>
</div>


## 介绍

HugAi是由Springboot集成openAi SDK开发的一套智能AI知识库，支持GPT对话，AI绘图Midjourney、Stable Diffusion、openai，前后端源码完全开源。

> 声明：基于 Apache-2.0 协议，免费且作为开源学习使用，开源不易，二开或商用请注明原作者。

- HugAi后端源码地址：
    - [https://gitee.com/toushang6015/hugai-chatgpt](https://gitee.com/toushang6015/hugai-chatgpt)
    - [https://github.com/TouShang6015/hugai-chatgpt](https://github.com/TouShang6015/hugai-chatgpt)

- HugAi前台源码地址：
    - [https://gitee.com/toushang6015/hugai-chatgpt-ui](https://gitee.com/toushang6015/hugai-chatgpt-ui)
    - [https://github.com/TouShang6015/hugai-chatgpt-ui](https://github.com/TouShang6015/hugai-chatgpt-ui)
    
- **在线体验地址**：[http://chat.equinox19.xyz/home](http://chat.equinox19.xyz/home)
- **帮助文档**：[http://chat.doc.equinox19.xyz/](http://chat.doc.equinox19.xyz/)
- 我的博客：[https://codesand.fun/](https://codesand.fun/)

> 特别提醒：部署项目请查看帮助文档

## 特点

✅	ChatGpt聊天对话，支持中断停止响应，支持GPT3.5、4.0、3.5-16K等模型选择

✅	对话配置中心，平台管理、模型管理、api配置管理、镜像地址管理

✅	可配置镜像地址，ApiKey池

✅	支持主流Midjourney、Stable Diffusion、dall-e AI绘图功能，文生图、图生图、放大、重绘，AI优化Prompt

✅	扮演多种角色，小红书文案、广告创意、小说家、Prompt优化师等

✅	支持sse与websocket两种方式，流式响应 打字机输出，后台动态设置，无需重启

✅	云端存储对话记录，对话维护，上下文token计算

✅	支持多用户登陆：管理员、用户、游客，动态路由鉴权，不侵入代码

✅	支持本地、服务器、minio云端文件存储，多种文件上传策略动态切换

> 已接入大模型：OpenAi、讯飞星火、文心一言

## 技术栈

项目使用了多种设计模式、代码抽象的实际应用、延时队列、并发同步模式等，以及java8+特性，流式编程、函数式接口等相关技能点。该项目是一个全栈项目，认真的阅读代码，相信可以使你的技术进一步提升。

> 觉得项目不错可以帮我star⭐下仓库嘛，非常感谢你的支持🧎‍♂️~

### 后端

- 语言与框架
  - Java 17
  - Springboot2.7+
  - Maven 3.5+
  - mybatis-plus
  - rabbitMQ
- 数据库：
  - Mysql 8.0
  - Redis
- 核心技术
  - Java8+特性，流、函数式接口、Optional等
  - websocket、sse消息推送，动态切换
  - 队列任务，并发同步模式、延时队列socket消息推送
  - 分布式锁redission
  - anji Captcha验证码
  - minio 云文件存储
  - SpringSecurity 鉴权、多用户登陆
  - okhttp
  - 双端队列、延时队列
  - mapstruct
- 第三方sdk
  - openai sdk
  - minio 七牛云
  - Stable Diffusion
  - Discord、Midjourney

### 前端

- vue2
- element-ui
- mavon-editor
- pace-js
- echarts

## 待办 TODO

闲暇时间会来维护这个开源项目，前后端都是自己开发，如有疑问欢迎加入最下方讨论群

|      |       功能        |                             描述                             |
| ---- | :---------------: | :----------------------------------------------------------: |
| ✅    |  管理端模块功能   | 管理端目前只有站点配置、领域会话维护，后续会添加用户模块、首页统计等功能 |
| ❌    |    ~~pdf阅读~~    | ~~向量库，阅读pdf、word等，正在研究~~（比较花时间，milvus需要服务器成本） |
| ✅    |      sd画图       |      正在研究，目前使用的是openai的画图sdk，不怎么好用       |
| ❌    |     ~~黑化~~      | ~~prompt中文大合集中有讨论猫娘的，懂我意思吧~~（会被封...）  |
| ✅    | sd画图结合chatGpt | prompt交给chatGpt优化，配置化，用户可自选专业模式或简单模式  |
| ✅    |      mj画图       |                      midjourney画图接入                      |
| ✅    |  gpt对话交互优化  |           优化gpt问答对话样式，添加删除对话等功能            |
|      |       画廊        |       个人、公开画廊，分享精选图片，提供绘图参数画同款       |
| ❌    |  ~~api key管理~~  | ~~目前使用的是免费额度，用完了就轮询，毕竟有限，不能一直开放。有些用户有自己的apiKey可以自己添加，需要做一些加密处理保证安全性~~ |
|      |     社区功能      | gpt社区、画廊社区，值得推荐的对话与ai绘图可自由发布到社区，以及评论功能，点赞等 |
|      |     其他模型      |           文心一言、讯飞星火、通义千问等其他大模型           |
|      |      .......      |                                                              |



## 项目截图

> 用户端

![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205100114.png)
![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205100213.png)
![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205100250.png)
![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205100752.png)
![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205100810.png)

> 后台管理端

![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205095735.png)
![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205095758.png)
![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205095828.png)
![](http://chat.static.equinox19.xyz/hugai-doc/eg/20231205095843.png)

### 讨论群/联系方式

部署前优先查看 [帮助文档](http://chat.doc.equinox19.xyz/) ，如有疑问或宝贵的意见，欢迎加入讨论群一起交流🤤

- [HugAi交流群](http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=aMuWiFSF07SRRGAjWoncq37lPo_LhWKL&authKey=A0PnuyDPFwVlgADcH5BDyKbWGhRJ7JnjjcSnSI7bg2RDjxXXkrowqeQEk2Z9x%2B3%2F&noverify=0&group_code=747193904)：747193904


[![Star History Chart](https://api.star-history.com/svg?repos=TouShang6015/Hugai-chatgpt&type=Date)](https://star-history.com/?utm_source=bestxtools.com#TouShang6015/Hugai-chatgpt&Date)