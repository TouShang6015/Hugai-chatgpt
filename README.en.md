![](http://chat.static.equinox19.xyz/hugai-doc/HugAiLogo1-icon.png)

## Introduced

HugAi is a set of intelligent AI knowledge base developed by Springboot integrated with openAi SDK, supporting GPT dialogue, AI drawing Midjourney, Stable Diffusion, openai, the front and back end source code is completely open source.

> Disclaimers-based on the Apache-2.0 protocol, it is free and open source for learning, and cannot be used in commercial projects without my consent.

- HugAi backend source address：
    - [https://gitee.com/toushang6015/hugai-chatgpt](https://gitee.com/toushang6015/hugai-chatgpt)
    - [https://github.com/TouShang6015/hugai-chatgpt](https://github.com/TouShang6015/hugai-chatgpt)

- HugAi foreground source address：
    - [https://gitee.com/toushang6015/hugai-chatgpt-ui](https://gitee.com/toushang6015/hugai-chatgpt-ui)
    - [https://github.com/TouShang6015/hugai-chatgpt-ui](https://github.com/TouShang6015/hugai-chatgpt-ui)

- **Online experience address**：[http://chat.equinox19.xyz/home](http://chat.equinox19.xyz/home)
- **help document**：[http://chat.doc.equinox19.xyz/](http://chat.doc.equinox19.xyz/)


- My blog：[https://www.equinox19.xyz/](https://www.equinox19.xyz/)

> Special note: See the help documentation for your deployment project

## characteristic

✅	ChatGpt chat dialogue, support interrupt stop response, support GPT3.5, 4.0, 3.5-16K and other models

✅	Support mainstream Midjourney, Stable Diffusion, dall-e AI drawing functions, Vincennes, Tu-sheng, magnification, redrawing, AI optimization Prompt

✅	Play a variety of roles, such as little Red book copywriter, advertising creative, novelist, Prompt optimizer, etc

✅	Support sse and websocket two ways, flow response typewriter output, background dynamic setting, no need to restart

✅	The cloud stores conversation records, conversation maintenance, and context token calculation

✅	ApiKey Multiple policies, polling, random, custom, RSA encryption to ensure security, exception handling mechanism, automatically disable ApiKey

✅	Support multi-user login: administrator, user, visitor, dynamic route authentication, no intrusion code

✅	Supports local, server, minio cloud file storage, and dynamic switching of multiple file upload policies

## Technology stack

The project uses a variety of design patterns, practical application of code abstraction, aop code decoupling, as well as java8 features, streaming programming, functional interfaces and other related skills. This project is a full stack project, carefully read the code, I believe you can further improve your technology.

> Think the project is good, can you help me star⭐ warehouse, thank you very much for your support🧎‍♂️~

### Server

- Language and framework
    - Java 17
    - Springboot2.7+
    - Maven 3.5+
    - mybatis-plus
    - rabbitMQ
- Database：
    - Mysql 8.0
    - Redis
- Core technology
    - Java8+ features, streams, functional interfaces, Optional... etc
    - sse message push
    - redission
    - anji Captcha
    - minio 
    - SpringSecurity Authentication, multi-user login
    - okhttp
    - deque
    - mapstruct
- Third party sdk
    - openai sdk
    - minio Qi Niu Yun
    - Stable Diffusion
    - Discord、Midjourney

### Front

- vue2
- element-ui
- mavon-editor
- pace-js
- echarts

## TODO

In my spare time, I will maintain this open source project and develop both the front and back ends by myself. If you have any questions, please join the discussion group at the bottom. Customization or other needs can be added to work below wechat.

|          Function           |                                                                                     description                                                                                     |
|:---------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| Management module functions | At present, only site configuration and domain session maintenance are available on the management side. Functions such as user module and home page statistics will be added later |
|          pdf Read           |                                                                        Vector library. Working on it... etc                                                                         |
|         sd Drawing          |                                                           Is studying, currently using openai drawing sdk, not how to use                                                           |
|             melanism              |                                                            prompt has a discussion of kittens, if you know what I mean?                                                             |
|           .......           |                                                                                                                                                                                     |

## Project screenshot

![](http://chat.static.equinox19.xyz/hugai-doc/effect/1693874917258.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1693875024839.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1693875097749.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1693875273399.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691030140010.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691030163684.jpg)
![](http://chat.static.equinox19.xyz/hugai-doc/effect/1691030196637.jpg)

### Discussion groups/contact information

Look at [help](http://chat.doc.equinox19.xyz/), if there are any question or opinion, welcome to join the discussion groups together 🤤 communication

- discussion group(QQ)：747193904
