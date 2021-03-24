package pers.liuchengyin.weibo.service;

import pers.liuchengyin.weibo.pojo.User;
import pers.liuchengyin.weibo.pojo.WeiBoUser;

/**
 * @ClassName UserService
 * @Description
 * @Author 柳成荫
 * @Date 2021/3/24
 */
public interface UserService {
    /**
     * 登录和注册合并逻辑
     * @param weiBoUser weiBoUser
     * @return
     */
    User oauthLogin(WeiBoUser weiBoUser);
}
