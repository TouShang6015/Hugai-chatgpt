package com.hugai.common.enums.flow;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.hugai.common.constants.MQConstants;
import com.hugai.core.sd.entity.Img2ImgRequest;
import com.hugai.core.sd.entity.TxtImgRequest;
import com.hugai.core.session.entity.SessionDrawCreatedOpenaiCacheData;
import com.hugai.core.session.entity.SessionDrawEditOpenaiCacheData;
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

    OPENAI("OPENAI", "openAi接口") {
        @Override
        public List<ApiKey> getApiKey() {
            return CollUtil.newArrayList(ApiKey.openai_txt2img, ApiKey.openai_img2img);
        }

        @Override
        public String queueKey() {
            return MQConstants.Queue.draw_openai;
        }
    },

    SD("SD", "stable diffusion") {
        @Override
        public List<ApiKey> getApiKey() {
            return CollUtil.newArrayList(ApiKey.sd_txt2img, ApiKey.sd_img2img);
        }

        @Override
        public String queueKey() {
            return MQConstants.Queue.draw_sd;
        }
    };

    private final String key;

    private final String value;


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
     */
    @Getter
    @AllArgsConstructor
    public enum ApiKey {

        openai_txt2img(SessionDrawCreatedOpenaiCacheData.class),

        openai_img2img(SessionDrawEditOpenaiCacheData.class),

        sd_txt2img(TxtImgRequest.class),

        sd_img2img(Img2ImgRequest.class),

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
