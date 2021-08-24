package com.controller;

import com.annotation.AccessLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/06
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    @AccessLimit(maxCount = 3, seconds = 1)
    public String hello() {
        try {
            System.out.println("hello");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }
}
