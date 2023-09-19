package com.hugai.core.drawTask.strategy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hugai.common.constants.Constants;
import com.hugai.common.enums.ModulesToken;
import com.hugai.common.enums.flow.DrawType;
import com.hugai.core.openai.factory.AiServiceFactory;
import com.hugai.core.openai.service.OpenAiService;
import com.hugai.core.security.context.UserThreadLocal;
import com.hugai.core.drawTask.entity.SessionCacheDrawData;
import com.hugai.framework.file.context.FileServiceContext;
import com.hugai.modules.draw.entity.model.TaskDrawModel;
import com.hugai.modules.draw.entity.vo.DrawPersistenceCollection;
import com.hugai.modules.session.service.SessionInfoDrawService;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.spring.SpringUtils;
import com.org.bebas.utils.OptionalUtil;
import com.theokanning.openai.completion.chat.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 绘图策略器抽象层
 *
 * @author WuHao
 * @since 2023/9/8 13:13
 */
@Slf4j
public abstract class DrawAbstractStrategy<MappingCls> implements DrawApiService {

    protected DrawType drawType;

    protected DrawType.ApiKey apiKey;

    protected TaskDrawModel drawData;

    protected SessionCacheDrawData cacheData;

    protected SessionInfoDrawService sessionInfoDrawService;

    protected FileServiceContext fileServiceContext;

    public DrawAbstractStrategy(TaskDrawModel drawData, SessionCacheDrawData cacheData) {
        Assert.notNull(drawData);
        this.drawData = drawData;
        this.apiKey = DrawType.ApiKey.getByName(drawData.getDrawApiKey());
        this.drawType = DrawType.getByApiKey(this.apiKey);
        this.cacheData = cacheData;
        this.sessionInfoDrawService = SpringUtils.getBean(SessionInfoDrawService.class);
        this.fileServiceContext = SpringUtils.getBean(FileServiceContext.class);
        if (Objects.isNull(cacheData)) {
            OR.run(this.drawData.getRequestParam(), StrUtil::isNotEmpty, requestParamString -> {
                this.cacheData = JSON.parseObject(requestParamString, SessionCacheDrawData.class);
            });
        }
    }

    public DrawAbstractStrategy(TaskDrawModel drawData) {
        this(drawData, null);
    }

    /**
     * 请求外部接口返回持久化集合实体
     *
     * @return
     */
    @Override
    public DrawPersistenceCollection executeApi() {
        UserThreadLocal.set(this.drawData.getUserId());
        try {
            return this.executeApiHandle();
        } finally {
            UserThreadLocal.remove();
        }
    }


    /**
     * 优化prompt
     *
     * @return
     */
    protected String optimizePrompt() {
        AtomicReference<String> prompt = new AtomicReference<>(this.cacheData.getPrompt());
        OR.run(this.cacheData.getOptimizePrompt(), StrUtil::isNotEmpty, optimizePrompt -> {
            if (Constants.BOOLEAN.FALSE.equals(optimizePrompt)) {
                return;
            }
            try {
                log.info("[绘图 - prompt参数优化]，原始prompt：{}", prompt.get());
                OpenAiService service = AiServiceFactory.createService();
                ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), chatRequestForOptimizePrompt + prompt.get());
                ChatCompletionResult chatCompletion = service.createChatCompletion(
                        ChatCompletionRequest.builder()
                                .model("gpt-3.5-turbo")
                                .messages(CollUtil.newArrayList(chatMessage))
                                .n(1)
                                .maxTokens(ModulesToken.DEFAULT.getOnce())
                                .logitBias(new HashMap<>())
                                .build()
                );
                OR.run(
                        OptionalUtil.ofNullList(chatCompletion.getChoices()).stream().findFirst().orElseGet(ChatCompletionChoice::new).getMessage()
                        , Objects::nonNull
                        , message -> {
                            prompt.set(message.getContent());
                            log.info("[绘图 - prompt参数优化]，GPT 优化后的prompt：{}", prompt.get());
                        });
            } catch (Exception ignored) {
            }
        });
        return prompt.get();
    }

    /**
     * executeApi 策略器完整处理
     *
     * @return
     */
    protected abstract DrawPersistenceCollection executeApiHandle();

    /**
     * 获取策略器映射aip映射实体Class
     *
     * @return
     */
    protected abstract Class<MappingCls> getMappingCls();

    /**
     * 释放线程变量
     */
    protected void close() {
        UserThreadLocal.remove();
    }

    /**
     * 通过GPT优化prompt默认问题
     */
    protected final String chatRequestForOptimizePrompt = "You are a Stable Diffusion prompt word generator\n" +
            "\n" +
            "Stable Diffusion is a text based AI image generation model that generates corresponding images based on text prompts. You need to follow these guidelines when writing prompt words:\n" +
            "\n" +
            "1）Be as detailed and specific as possible. SD handles specific prompts better than abstract or vague prompts. For example, rather than writing a portrait of a woman, it would be better to write a Renaissance style portrait of a woman with red hair and brown eyes.\n" +
            "\n" +
            "2） Specify a specific artistic style or stroke. If you want to obtain images with a specific style or texture, it should be clearly specified in the request. For example, rather than writing about \"scenery\", it is better to write about \"the scenery of mountains and lakes in the style of watercolor  painting\".\n" +
            "\n" +
            "3）Specify a reference artist. If you want to obtain images similar to an artist's work, you should specify their name in the request. For example, instead of writing 'abstract images', it is better to write' abstract paintings like Picasso's style '.\n" +
            "\n" +
            "4）The weight of the keyword. You can use the format 'Keyword: Number' to specify the weight of a keyword in the prompt word. The greater the weight of a keyword, the greater its impact on the results. For example, if you want to obtain an image of a cat with green eyes and pink nose, you can write \"Cat: 1.5, green eyes: 1.3, pink nose: 1\". This means that cats will be the most important element in the image, with green eyes having less importance and pink noses having the least importance.\n" +
            "\n" +
            "Another method to adjust the weight size of keywords is to use symbols () and []. (Keyword) will increase the strength of the keyword by 1.1 times, equivalent to (Keyword: 1.1). [Keyword] Reduce the strength of the keyword by 0.9 times, equivalent to (keyword: 0.9). You can use multiple matches, and the effect is multiplication. For example: (Keyword): 1.1, (Keyword): 1.21, (Keyword): 1.33\n" +
            "\n" +
            "Similarly, the effect of using multiple [] is as follows: [keywords]: 0.9, [keywords]: 0.81, [keywords]: 0.73\n" +
            "\n" +
            "Here are some examples of prompt words:\n" +
            "\n" +
            "example 1：a painting of a woman in medieval knight armor with a castle in the background and clouds in the sky behind her, (impressionism:1.1), ('rough painting style':1.5), ('large brush texture':1.2), ('palette knife':1.2), (dabbing:1.4), ('highly detailed':1.5), professional majestic painting by Vasily Surikov, Victor Vasnetsov, (Konstantin Makovsky:1.3), trending on ArtStation, trending on CGSociety, Intricate, High Detail, Sharp focus, dramatic\n" +
            "\n" +
            "example 2：Jane Eyre with headphones, natural skin texture, 24mm, 4k textures, soft cinematic light, adobe lightroom, photolab, hdr, intricate, elegant, highly detailed, sharp focus, ((((cinematic look)))), soothing tones, insane details, intricate details, hyperdetailed, low contrast, soft cinematic light, dim colors, exposure blend, hdr, faded\n" +
            "\n" +
            "example 3：(8k, RAW photo, highest quality), beautiful girl, close up, t-shirt, (detailed eyes:0.8), (looking at the camera:1.4), (highest quality), (best shadow), intricate details, interior, (ponytail, ginger hair:1.3), dark studio, muted colors, freckles\n" +
            "\n" +
            "When I list the topic, you will write a detailed reminder word about the topic. Only the reminder word is needed, and those unrelated to the reminder word are not needed,plase Output in English, Do not export Chinese. The word limit cannot exceed 500, Remember to follow the above rules.\n" +
            "\n" +
            "My first topic is this: ";

}
