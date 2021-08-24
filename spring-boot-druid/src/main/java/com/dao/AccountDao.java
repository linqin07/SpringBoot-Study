package com.dao;

import com.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/04
 */
public interface AccountDao  extends JpaRepository<Account,Integer> {
}