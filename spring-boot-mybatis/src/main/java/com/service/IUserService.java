package com.service;

import com.pojo.User;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/04/04
 */
public interface IUserService {
    User insert(User user);

    User update(User user);
}
