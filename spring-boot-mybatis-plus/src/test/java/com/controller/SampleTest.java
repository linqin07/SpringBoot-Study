package com.controller;

import com.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/04/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
//        userList.forEach(System.out::println);

//        User user = new User();
////        user.setAge(1);
////        user.setEmail("163@");
////        user.setName("linlin1");
////        userMapper.insert(user);

//        使用mp自带方法删除和查找都会附带逻辑删除功能 (自己写的xml不会)
        userMapper.deleteById("1120267997492035586");

    }


    @Autowired
    private UserMapper userMapper;

}