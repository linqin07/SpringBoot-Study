package com.service.impl;

import com.dao.UserMapper;
import com.pojo.User;
import com.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/04/04
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    // 测试事务嵌套
    @Autowired
    TxImpl tx;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User insert(User user) {
        userMapper.insert(user);

        User user1 = userMapper.selectByPrimaryKey(user.getUserId());
        user1.setUserAge("99329");
        System.err.println(user1);
        try {
            // 新起的事务无效，动态代理留的坑。要通过代理执行或者注入bean来执行。这个方法写的事务注解都是无效的，等于没写。
//            update(user1);

            // 这个更新方法失败。记录被行锁了。但是换成insert是生效的
//            ((IUserService) AopContext.currentProxy()).update(user1);

            //
            user.setUserName("tx1执行");
            User update = tx.update(user);
            System.out.println(update);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User update(User user) {
        userMapper.updateByPrimaryKey(user);
        throw new RuntimeException("ggg");
//        return userMapper.selectByPrimaryKey(user.getUserId());

    }
}
