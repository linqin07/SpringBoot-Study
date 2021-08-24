package com.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/03
 */
@Controller
public class HelloWordController {
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public String index() {
        System.out.println("进入index方法");
//        MyRunable myRunable = new MyRunable();
//        new Thread(myRunable).start();
        return "HelloWorld";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test() throws IOException {
        System.out.println(ResourceUtils.getURL("classpath").getPath());

        // 可以直接读取jar包内的文件。
        ClassPathResource ss = new ClassPathResource("banner.txt");
//        InputStream is = ss.getInputStream();
        InputStream is = HelloWordController.class.getClassLoader().getResourceAsStream("banner.txt");

        String path = this.getClass().getResource("/").getPath();
        System.out.println(path);

        int len = -1;//初始值，起标志位作用
        byte buf[] = new byte[128];//缓冲区
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = is.read(buf)) != -1) {//循环读取内容,将输入流的内容放进缓冲区中
            baos.write(buf, 0, len);//将缓冲区内容写进输出流，0是从起始偏移量，len是指定的字符个数
        }

        String result = new String(baos.toByteArray());
        System.out.println(result);


        return "testJRebel";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, String[]> handle(HttpServletRequest request) {
        System.out.println(request.getParameterMap().entrySet());
        return request.getParameterMap();
    }
}
