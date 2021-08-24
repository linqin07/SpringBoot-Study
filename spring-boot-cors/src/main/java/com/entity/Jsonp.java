package com.entity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/19
 * 本类给控制器返回实体类继承，安装访问是否带参数判断是否返回json或者jsonp数据
 */

@ControllerAdvice(basePackages = "com.controller")
public class Jsonp extends AbstractJsonpResponseBodyAdvice {
    public Jsonp() {
        //构造函数
        super("callback","jsonp");
    }

}
