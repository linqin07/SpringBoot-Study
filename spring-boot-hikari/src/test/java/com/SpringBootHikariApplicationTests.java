package com;

import com.vo.configProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
// @RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootHikariApplication.class)
public class SpringBootHikariApplicationTests {

    @Autowired
    private configProperties properties;

	@Test
	public void contextLoads() {
        // Assert.assertEquals("我是属性二",properties.getKey());
        // Assert.assertEquals("我是属性1",properties.getValue());
        System.out.println(properties.toString());

	}

}
