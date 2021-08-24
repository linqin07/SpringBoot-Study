package com.controller;

import com.annotation.AccessLimit;
import com.dao.UserDao;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/05
 */
@RestController
public class LoginController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(User user, Map<String, String> map) {
        System.out.println("--------");
        System.out.println(user.toString());
        // map 参数要用Object来接收，
        Map map1 = (Map) map;
        System.out.println(map1.entrySet());
        return "user";
    }

    @AccessLimit(seconds = 1, maxCount = 3)
    @RequestMapping("/index")
    public String index() {
        System.out.println("index");
        return "index";
    }

}
