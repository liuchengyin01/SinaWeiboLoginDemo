package pers.liuchengyin.weibo.controller;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.liuchengyin.weibo.pojo.User;
import pers.liuchengyin.weibo.pojo.WeiBoUser;
import pers.liuchengyin.weibo.service.UserService;
import pers.liuchengyin.weibo.util.HttpUtils;
import pers.liuchengyin.weibo.util.WeiBoConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WeiboController
 * @Description 微博登录Controller
 * @Author 柳成荫
 * @Date 2021/3/24
 */
@Controller
@RequestMapping("/oauth2.0")
public class WeiBoLoginController {
    @Autowired
    private UserService userService;

    /**
     * 微博登录回调方法
     * @param code 根据这个code会去换一个token
     * @param model thymeleaf页面用到
     * @return
     */
    @GetMapping("/weibo/success")
    public String weiBoLogin(@RequestParam("code") String code, Model model) {
        // 头信息不能为null
        Map<String, String> header = new HashMap<>();
        // query不能为null
        Map<String, String> query = new HashMap<>();
        // 这个Map就是body参数体
        Map<String, String> map = new HashMap<>();
        // client_id，即App Key
        map.put("client_id", WeiBoConstant.WEIBO_CLIENT_ID);
        // client_secret，即App Secret
        map.put("client_secret", WeiBoConstant.WEIBO_CLIENT_SECRET);
        // 固定的grant_type - authorization_code
        map.put("grant_type", WeiBoConstant.WEBIBO_GRANT_TYPE);
        // 回调地址
        map.put("redirect_uri", WeiBoConstant.WEIBO_REDIRECT_URI);
        // code码
        map.put("code", code);
        // 发送POST请求 - 获取access_token
        try {
            // 官方文档：https://open.weibo.com/wiki/Oauth2/access_token
            HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", header, query, map);
            // 拿到access_token就可以为所欲为了 - 前提是有对应调用接口的权限
            if (response.getStatusLine().getStatusCode() == 200) {
                // 请求状态为200，表示成功
                // 通过getEntity方法可以得到如下格式的JSON字符串
                /*
                    {
                    "access_token": "2.00gvBwvFEBF9VzcseAe2b7c8Jy47iB",
                    "remind_in": "157679999",
                    "expires_in": 157679999,
                    "uid": "5438845756",
                    "isRealName": "true"
                    }
                */
                // 可以提前创建一个对应这个JSON的POJO类，我这里对应的POJO类为WeiBoUser
                String json = EntityUtils.toString(response.getEntity());
                // 转换成对应的POJO类
                WeiBoUser weiBoUser = JSON.parseObject(json, WeiBoUser.class);
                /**
                 * 此时可以写一个业务去判断登录的用户是否已经存在
                 * 一般就通过uid去查询，如果能查询出来对应的用户对象，直接返回即可
                 * 当然在返回之前还需要更新数据库中对应的access_token、expires_in字段（令牌和令牌过期时间）
                 *
                 * 如果数据库中不存在，则需要存入数据库之后返回
                 */
                // 将这段逻辑封装成loginService中的一个方法
                User user = userService.oauthLogin(weiBoUser);

                model.addAttribute("user", user);
                return "index";
            } else {
                // 失败返回登录页面，并提示.....
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

}
