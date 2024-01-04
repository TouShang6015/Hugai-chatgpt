package com.hugai.common.webApi.baseResource;

import com.hugai.common.entity.baseResource.*;

/**
 * @author WuHao
 * @since 2023/10/25 9:29
 */
public interface BaseResourceWebApi {


    /**
     * ResourceMainVO 配置
     *
     * @return
     */
    ResourceMainVO getResourceMain();

    /**
     * ResourceChatConfigVO
     *
     * @return
     */
    ResourceChatConfigVO getResourceChatConfigVO();

    /**
     * ResourceDrawVO 配置
     *
     * @return
     */
    ResourceDrawVO getResourceDraw();

    ResourceSmsConfigVO getResourceSmsConfig();


}
