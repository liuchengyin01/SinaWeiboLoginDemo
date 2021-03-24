package pers.liuchengyin.weibo.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.liuchengyin.weibo.pojo.User;

/**
 * @ClassName UserDao
 * @Description
 * @Author 柳成荫
 * @Date 2021/3/24
 */
@Component
@Slf4j
public class UserDao {
    public User getUserByUid(String uid){
        log.info("进入到UserDao，模拟返回一个null");
        return null;
    }
}
