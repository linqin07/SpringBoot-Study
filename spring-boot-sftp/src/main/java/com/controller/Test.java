package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: LinQin
 * @date: 2021/02/10
 */

@RestController
public class Test {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/test")
    public String test() {
        log.info("gg 思密达！");
        return "this is a test ! ";
    }

}
