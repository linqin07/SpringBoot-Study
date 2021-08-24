package com.dao;

import com.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 * author: 林钦
 * date: 2019/07/30
 */
@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
//    /开头表示从classpath,(test)根目录开始搜索，没有以此开头默认在测试类所在包下。也可使用classpath:、file:、http: 开头
    @Sql(scripts = {"classpath:sql/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void test() {
        User user = userMapper.selectById(1);
        System.out.println(user.toString());
        Assert.assertEquals("Jone", user.getName());
    }
}