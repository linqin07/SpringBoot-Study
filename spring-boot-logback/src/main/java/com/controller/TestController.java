package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/06
 */
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger("haha");

    private Logger logger1 = LoggerFactory.getLogger(TestController.class);


    @RequestMapping("/hello")
    public String hello () {
        logger.info("hello world!");
        logger1.info("hello world!");
        return "hello";
    }
}
