package com.hugai.modules.draw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hugai.common.modules.entity.draw.model.GalleryCommonModel;
import com.hugai.common.modules.entity.session.model.SessionRecordDrawModel;
import org.apache.ibatis.annotations.Param;

public interface GalleryCommonMapper extends BaseMapper<GalleryCommonModel> {

    IPage<SessionRecordDrawModel> queryCommonSessionRecord(IPage<SessionRecordDrawModel> page, @Param("param") SessionRecordDrawModel param);

}
