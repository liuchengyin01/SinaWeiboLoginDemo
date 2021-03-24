package pers.liuchengyin.weibo.pojo;

import lombok.Data;

/**
 * @ClassName WeiBoUser
 * @Description
 * @Author 柳成荫
 * @Date 2021/3/24
 */
@Data
public class WeiBoUser {
    /** 用户授权的唯一票据 */
    private String access_token;
    /** access_token的生命周期 - 该参数即将废弃，开发者请使用expires_in */
    private String remind_in;
    /** access_token的生命周期 秒 */
    private long expires_in;
    /** 授权用户的UID */
    private String uid;
}
