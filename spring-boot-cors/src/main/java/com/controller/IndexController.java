package com.controller;

import com.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/12
 */
@Controller
@RequestMapping(value = "", method = RequestMethod.GET)
public class IndexController {
    // @CrossOrigin(origins = "*")
    // @CrossOrigin
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public Student index(HttpSession session, HttpServletResponse response) throws IOException {
        System.out.println("我的sessionId为" + session.getId());
        Student student = new Student();
        student.setId(12);
        student.setName("苍老师");

        response.setCharacterEncoding("utf-8");
      return student;
    }


    @RequestMapping(value = "/index1")
    @ResponseBody
    public void index1( HttpServletResponse response, HttpServletRequest request) throws IOException {
        String callBackFuncString = request.getParameter("callback");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String json = "\"凉凉\"";
        out.write(callBackFuncString + "(" + json +")");
    }
}
