package com.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:  http://localhost:8080/swagger-ui.html
 * @author: LinQin
 * @date: 2019/05/05
 */
@RestController
@RequestMapping("/")
public class TestController {

    @ApiOperation(value="测试日志程序", notes="测试日志程序", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "school", value = "学校名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Map test(String school, String name) {
        System.out.println("gg");
        Map map = new HashMap();
        map.put("school", school);
        map.put("name", name);
        return map;
    }
}
