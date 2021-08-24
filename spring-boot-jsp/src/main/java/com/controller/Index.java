package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/03
 */
@Controller
public class Index {

    @RequestMapping("/jsp")
    public String index() {
        System.out.println("进入到jsp");
        return "index";
    }
}
