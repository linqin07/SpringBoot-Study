package com;

import com.dao.AccountDao;
import com.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
// @WebAppConfiguration  //web项目增加这个 SpringApplicationConfiguration过时了，用下面的代替
@SpringBootTest(classes = SpringBootJpaApplication.class)
public class SpringBootJpaApplicationTests {

    @Autowired
    private AccountDao dao;

	@Test
	public void contextLoads() {
	    //懒加载问题,不能使用getOne()
        Optional<Account> account = dao.findById(1);
        System.out.println(account.get().toString());
    }

}
