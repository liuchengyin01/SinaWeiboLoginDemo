package pers.liuchengyin.weibo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.liuchengyin.weibo.dao.UserDao;
import pers.liuchengyin.weibo.pojo.User;
import pers.liuchengyin.weibo.pojo.WeiBoUser;
import pers.liuchengyin.weibo.service.UserService;
import pers.liuchengyin.weibo.util.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author 柳成荫
 * @Date 2021/3/24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 登录和注册合并逻辑
     * @param weiBoUser 新浪微博返回的对象
     * @return 登录的User对象
     */
    @Override
    public User oauthLogin(WeiBoUser weiBoUser) {
        // 获取Uid，根据Uid去数据库查询是否已经存在
        String uid = weiBoUser.getUid();
        // 模拟查询数据库 - 这里这个user应该是去数据库查询出来的，但这里是一个null
        User user = userDao.getUserByUid(uid);
        // 根据获取到的user是否为null判断用户曾经是否登录过系统
        if (null != user){
            // 用户登陆过/注册过
            User trueUser = new User();
            BeanUtils.copyProperties(user,trueUser);
            trueUser.setAccessToken(weiBoUser.getAccess_token());
            trueUser.setExpiresIn(weiBoUser.getExpires_in());
            // 同时修改数据库里的信息...
            // xxxDao.update
            return trueUser;
        }else{
            // 用户未登录过/注册过
            User register = new User();
            try{
                // 查询社交账号的信息
                Map<String, String> query = new HashMap<>();
                query.put("access_token",weiBoUser.getAccess_token());
                query.put("uid",weiBoUser.getUid());
                // 根据token和uid获取用户信息
                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<String, String>(), query);
                if (response.getStatusLine().getStatusCode() == 200){
                    // 查询成功
                    String json = EntityUtils.toString(response.getEntity());
                    // 你需要的数据都可以从这个jsonObject中获取 - 见官方文档：https://open.weibo.com/wiki/2/users/show
                    JSONObject jsonObject = JSON.parseObject(json);
                    // 昵称
                    String name = jsonObject.getString("name");
                    // 头像url
                    String avatar = jsonObject.getString("avatar_hd");
                    register.setNickname(name);
                    register.setAvatar(avatar);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            register.setWeiBoUid(weiBoUser.getUid());
            register.setAccessToken(weiBoUser.getAccess_token());
            register.setExpiresIn(weiBoUser.getExpires_in());
            // 同时将信息插入数据库...
            // xxxDao.insert
            return register;
        }
    }
}
