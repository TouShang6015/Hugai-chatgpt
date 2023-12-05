package com.hugai.common.entity.baseResource;

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
     * 网站发布时间
     */
    private String webIssueTime;

    /**
     * 当前项目版本
     */
    private String projectVersion;

    /**
     * 代理地址
     */
    private String proxyHost;
    /**
     * 代理端口
     */
    private Integer proxyPort;
    /**
     * 游客默认头像
     */
    private String defaultUserTouristImgUrl;
    /**
     * 新用户默认头像
     */
    private String defaultUserImgUrl;

}
