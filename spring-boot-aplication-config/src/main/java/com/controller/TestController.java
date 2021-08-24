package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @Description:
 * @author: LinQin
 * @date: 2018/09/14
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String Test() {
        System.out.println("我访问到了！");
        return "hello";
    }

    public static void main(String[] args) {
        String[] str = {"null",null};
        System.out.println(str.length);
    }
}
