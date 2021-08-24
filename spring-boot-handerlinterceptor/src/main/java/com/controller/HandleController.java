package com.controller;

import com.dao.UserDao;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/05
 */
@Controller
@RequestMapping("/user")
public class HandleController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/handle")
    public String handle(User user, HttpSession session) {

        Specification<User> specification = new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.equal(root.get("name"), user.getName()));
                return null;
            }
        };

        boolean flag = true;
        String json = "";

        User user1 = (User) userDao.findOne(specification).get();
        if (user1 == null) {
            json = "用户不存在，登录失败！";
        } else if (!user.getPwd().trim().equals(user1.getPwd().trim())) {
            System.out.println(user1.toString());
            flag = false;
            json = "密码不正确！请重新登录！";
        }

        if (flag) {
            session.setAttribute("session_user", user1);
            json = "登录成功";
            return "index";
        } else {
            return "login";
        }
    }
}
