package com;

import com.constant.Sex;
import com.dao.TypehandleMapper;
import com.entity.Typehandle;
import com.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisTypehandlerApplicationTests {

    @Autowired
    private TypehandleMapper typehandleMapper;

    @Test
    public void contextLoads() {

        List list = new ArrayList<>();
        list.add("ll");
        list.add("qq");

        User user1 = new User(1, "lin");
        User user2 = new User(2, "qin");
        List list1 = new ArrayList();
        list1.add(user1);
        list1.add(user2);

        Typehandle typehandle = new Typehandle();
        typehandle.setAge(24).setConfig(list1).setName("blin").setSex(Sex.FMALE);
        typehandleMapper.insert(typehandle);
    }

    @Test
    public void read() {
        Typehandle withId = typehandleMapper.getWithId(8);
        List<User> config = withId.getConfig();
        System.out.println(config.get(1).toString());
        System.out.println(config.get(1).getName());
        System.out.println(withId.toString());
    }

}
