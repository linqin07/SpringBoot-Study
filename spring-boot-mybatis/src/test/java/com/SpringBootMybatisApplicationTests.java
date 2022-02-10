package com;

import com.convert.UserConvert;
import com.convert.UserDTO;
import com.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {

    @Test
    public void contextLoads() {
        User user = new User(1, "12", "name");
        UserDTO convert = UserConvert.INSTANCE.convert(user);


        System.out.println(convert);
    }

}
