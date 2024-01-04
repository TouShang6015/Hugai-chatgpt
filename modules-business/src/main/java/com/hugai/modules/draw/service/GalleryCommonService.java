package com.hugai.modules.draw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.modules.entity.draw.model.GalleryCommonModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import com.org.bebas.mapper.service.IService;

public interface GalleryCommonService extends IService<GalleryCommonModel> {


    /**
     * 公开画廊绘图记录查询
     *
     * @param param
     * @return
     */
    IPage<SessionRecordDrawModel> queryCommonSessionRecord(SessionRecordDrawModel param);

    /**
     * 设置图片公开性
     *
     * @param param
     */
    GalleryCommonModel setDrawCommon(GalleryCommonModel param);

    /**
     * 删除图片公开性
     *
     * @param param
     */
    void removeDrawCommon(GalleryCommonModel param);

}
