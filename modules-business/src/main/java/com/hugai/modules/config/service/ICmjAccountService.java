package com.hugai.modules.config.service;

import com.hugai.common.modules.entity.config.model.CmjAccountModel;
import com.hugai.common.modules.entity.config.vo.CmjAccountDetailVO;
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
