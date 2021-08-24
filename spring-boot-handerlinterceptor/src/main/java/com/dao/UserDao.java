package com.dao;

import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/05
 */
public interface UserDao extends JpaRepository<User, Integer>,JpaSpecificationExecutor,Serializable {

}
