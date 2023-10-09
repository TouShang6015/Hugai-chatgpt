package com.hugai.modules.midjourney.service;

import com.hugai.modules.midjourney.entity.model.CmjAccountModel;
import com.hugai.modules.midjourney.entity.vo.CmjAccountDetailVO;
import com.org.bebas.mapper.service.IService;

import java.util.List;

/**
 * mj账户配置 业务接口
 *
 * @author wuhao
 * @date 2023-09-25
 */
public interface ICmjAccountService extends IService<CmjAccountModel> {

    List<CmjAccountDetailVO> getAccountAll();

}
