package com.hugai.common.modules.entity.draw.vo;

import lombok.Data;

/**
 * 画廊数据展示
 *
 * @author WuHao
 * @since 2023/11/10 14:09
 */
@Data
public class GalleryItemDataVO {

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userImgUrl;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 绘图会话ID
     */
    private Long sessionInfoDrawId;
    /**
     * 绘图会话详情ID
     */
    private Long sessionRecordDrawId;
    /**
     * 提示词
     */
    private String prompt;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 是否公开
     */
    private String ifCommon;
    /**
     * 公开主键ID
     */
    private Long galleryCommonId;


}
