package com.hugai.core.drawTask.enums;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.hugai.common.constants.MQConstants;
import com.hugai.common.constants.RedisCacheKey;
import com.hugai.common.modules.entity.draw.vo.openai.OpenaiImg2ImgRequest;
import com.hugai.common.modules.entity.draw.vo.openai.OpenaiTxt2ImgRequest;
import com.hugai.core.midjourney.common.entity.request.MjBaseRequest;
import com.hugai.core.midjourney.common.entity.request.MjImg2ImgRequest;
import com.hugai.core.midjourney.common.entity.request.MjTxt2ImgRequest;
import com.hugai.core.sd.entity.request.Img2ImgRequest;
import com.hugai.core.sd.entity.request.TxtImgRequest;
import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import com.org.bebas.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 绘图类型
 *
 * @author WuHao
 * @since 2023/6/19 10:08
 */
@Getter
@AllArgsConstructor
public enum DrawType implements FlowBaseEnum {

    OPENAI("OPENAI", "openAi接口", RedisCacheKey.TASK_DRAW_QUEUE_OPENAI) {
        @Override
        public List<ApiKey> getApiKey() {
            return CollUtil.newArrayList(ApiKey.openai_txt2img, ApiKey.openai_img2img);
        }

        @Override
        public String queueKey() {
            return MQConstants.Queue.draw_openai;
        }
    },

    SD("SD", "stable diffusion", RedisCacheKey.TASK_DRAW_QUEUE_SD) {
        @Override
        public List<ApiKey> getApiKey() {
            return CollUtil.newArrayList(ApiKey.sd_txt2img, ApiKey.sd_img2img);
        }

        @Override
        public String queueKey() {
            return MQConstants.Queue.draw_sd;
        }
    },

    MJ("MJ", "Midjourney", RedisCacheKey.TASK_DRAW_QUEUE_MJ) {
        @Override
        public List<ApiKey> getApiKey() {
            return CollUtil.newArrayList(ApiKey.mj_img2img, ApiKey.mj_txt2img, ApiKey.mj_img2mix, ApiKey.mj_u, ApiKey.mj_v);
        }

        @Override
        public String queueKey() {
            return MQConstants.Queue.draw_mj;
        }
    };

    private final String key;

    private final String value;

    private final String taskRedisKey;

    /**
     * 获取api key标识
     *
     * @return
     */
    public abstract List<ApiKey> getApiKey();

    /**
     * mq队列
     *
     * @return
     */
    public abstract String queueKey();

    /**
     * 会话api接口标识
     * <p> txt2img  -   文生图</p>
     * <p> img2img  -   图生图</p>
     * <p> img2mix  -   2图融合</p>
     */
    @Getter
    @AllArgsConstructor
    public enum ApiKey {

        // openai

        openai_txt2img(OpenaiTxt2ImgRequest.class),

        openai_img2img(OpenaiImg2ImgRequest.class),

        // sd

        sd_txt2img(TxtImgRequest.class),

        sd_img2img(Img2ImgRequest.class),

        // mj

        mj_txt2img(MjTxt2ImgRequest.class),

        mj_img2img(MjImg2ImgRequest.class),

        mj_img2mix(MjBaseRequest.class),

        mj_u(MjBaseRequest.class),

        mj_v(MjBaseRequest.class),

        ;

        private final Class<?> mappingCls;

        /**
         * 根据名称获取枚举值
         *
         * @param name
         * @return
         */
        public static ApiKey getByName(String name) {
            Assert.notEmpty(name, () -> new BusinessException("绘图api映射失败，api唯一标识为空"));
            for (ApiKey value : ApiKey.values()) {
                if (value.name().equals(name)) {
                    return value;
                }
            }
            throw new BusinessException("绘图api映射失败");
        }

    }

    /**
     * apikey标识获取DrawType值
     *
     * @param apiKey
     * @return
     */
    public static DrawType getByApiKey(ApiKey apiKey) {
        for (DrawType value : DrawType.values()) {
            List<ApiKey> apiKeyList = value.getApiKey();
            if (apiKeyList.contains(apiKey)) {
                return value;
            }
        }
        throw new BusinessException("获取绘图类型失败。");
    }

}
