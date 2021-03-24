package pers.liuchengyin.weibo.pojo;

import lombok.Data;

/**
 * @ClassName User
 * @Description
 * @Author 柳成荫
 * @Date 2021/3/24
 */
@Data
public class User {
    /** 昵称 */
    private String nickname;
    /** 头像Url */
    private String avatar;
    /** 微博UID */
    private String weiBoUid;
    /** token */
    private String accessToken;
    /** token过期时间 */
    private Long expiresIn;
}
