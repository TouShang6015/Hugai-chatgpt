package com.hugai.modules.system.entity.vo.baseResource;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wuhao
 * @date 2022/9/23 9:21
 */
@Data
public class ResourceMainVO implements Serializable {

    /**
     * 文件存储策略
     */
    private String fileSaveStrategy;
    /**
     * 是否开启注册
     */
    private Boolean registerOpen;
    /**
     * 是否开启登陆验证码
     */
    private Boolean authCodeOpen;
    /**
     * 是否使用系统ApiKey
     */
    private Boolean ableSystemApiKey;
    /**
     * 最大登陆人数
     */
    private Integer maxUserLogin;
    /**
     * 静态资源访问域名 （不需要带/）
     */
    private String staticWebsite;
    /**
     * websocket请求地址
     */
    private String websocketUrl;
    /**
     * 打字机响应模式
     */
    private String streamResponseType;
    /**
     * 网站域名
     */
    private String website;
    /**
     * 客户端默认背景图
     */
    private String deskImgDefault;
    /**
     * 网站发布时间
     */
    private String webIssueTime;

}
