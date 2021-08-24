package com.dao;

import com.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/04
 */
public interface AccountDao  extends JpaRepository<Account,Integer> {


    @Query(value = "select * from account where name = ?1", nativeQuery = true)
    public List<Account> nativeQuery(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from account where name = ?1", nativeQuery = true)
    public void deleteByName(String name);
}