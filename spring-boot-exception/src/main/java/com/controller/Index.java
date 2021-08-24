package com.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/19
 */
@RestController
public class Index {

    @RequestMapping("/index/{id}")
    public String Index(@PathVariable int id) {
        System.out.println(20 / id);
        return "成功访问";
    }
}
