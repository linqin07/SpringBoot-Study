package com.service.impl;

import com.dao.UserMapper;
import com.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/04/04
 */
@Service
public class TxImpl {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User insert(User user) {
        userMapper.insert(user);
//        return userMapper.selectByPrimaryKey(user.getUserId());
        throw new RuntimeException("ggg");

    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public User update(User user) {
//        userMapper.updateByPrimaryKey(user);
//        int i = 1/0;
        return userMapper.selectByPrimaryKey(user.getUserId());

    }
}
