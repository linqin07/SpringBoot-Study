package com.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Description:
 * author: 林钦
 * date: 2019/03/12
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBeanFactoryTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test1() throws IOException {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) this.applicationContext;
        context.close();
        System.in.read();
    }

}