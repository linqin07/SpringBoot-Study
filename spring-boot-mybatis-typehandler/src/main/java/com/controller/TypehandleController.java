package com.controller;


import com.constant.Sex;
import com.dao.TypehandleMapper;
import com.entity.Typehandle;
import com.service.TypehandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linqin
 * @since 2019-05-22
 */
@RestController
@RequestMapping("/")
public class TypehandleController {
    @Autowired
    private TypehandleService typehandleService;

    @Autowired
    private TypehandleMapper typehandleMapper;

    @RequestMapping("/insert")
    public void test() {
        List list = new ArrayList<>();
        list.add("ll");
        list.add("qq");
        Typehandle typehandle = new Typehandle();
        typehandle.setAge(24).setConfig(list).setName("blin").setSex(Sex.FMALE);
        typehandleService.save(typehandle);
    }

    @RequestMapping("/select")
    public void select() {
        Typehandle typehandle = typehandleMapper.selectById(4);
        System.out.println(typehandle.toString());
    }
}

