package pers.liuchengyin.weibo.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConstantUtil
 * @Description
 * @Author 柳成荫
 * @Date 2021/3/24
 */
@Component
public class WeiBoConstant implements InitializingBean {
    /** AppKey */
    @Value("${weibo.clientId}")
    private String weiBoClientId;
    /** AppSecret */
    @Value("${weibo.clientSecret}")
    private String weiBoClientSecret;
    /** grant_type */
    @Value("${weibo.grantType}")
    private String weiBoGrantType;
    /** 回调地址 */
    @Value("${weibo.redirectUri}")
    private String weiBoRedirectUri;

    public static String WEIBO_CLIENT_ID;
    public static String WEIBO_CLIENT_SECRET;
    public static String WEBIBO_GRANT_TYPE;
    public static String WEIBO_REDIRECT_URI;

    @Override
    public void afterPropertiesSet() throws Exception {
        WEIBO_CLIENT_ID = weiBoClientId;
        WEIBO_CLIENT_SECRET = weiBoClientSecret;
        WEBIBO_GRANT_TYPE = weiBoGrantType;
        WEIBO_REDIRECT_URI = weiBoRedirectUri;
    }
}
