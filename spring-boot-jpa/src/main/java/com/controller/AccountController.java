package com.controller;

import com.dao.AccountDao;
import com.entity.Account;
import com.entity.BaseAccountPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/04
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountDao accountDao;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccounts() {
        return accountDao.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Account> getAccountById(@PathVariable("id") int id) {
        return accountDao.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateAccount(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
                                @RequestParam(value = "money", required = true) double money) {
        Account account = new Account();
        account.setMoney(money);
        account.setName(name);
        account.setId(id);
        Account account1 = accountDao.saveAndFlush(account);

        return account1.toString();

    }

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public String postAccount(@RequestParam(value = "name") String name,
                              @RequestParam(value = "money") double money) {
        Account account = new Account();
        account.setMoney(money);
        account.setName(name);
        Account account1 = accountDao.save(account);
        return account1.toString();

    }

    @RequestMapping("/delete")
    public void delete(String id) {
        accountDao.deleteById(Integer.valueOf(id));
    }

    /********************************************************************************************************/

    @RequestMapping("/query")
    public List<Account> query(String name) {
        return accountDao.nativeQuery(name);
    }

    @RequestMapping("/deleteByName")
    public void deleteByName(String name) {
        accountDao.deleteByName(name);

    }

    @RequestMapping("/queryByPage")
    public List<Account> queryByPage(int pageNum) {
        BaseAccountPage page = new BaseAccountPage();
        page.setPage(pageNum);
        page.setSize(5);
        page.setSord("desc");
        page.setIndex("id");


        System.out.println(page.toString());
        //??????????????????
        Sort.Direction direction = Sort.Direction.ASC.toString().equalsIgnoreCase(page.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        //????????????????????????
        Sort sort = new Sort(direction, page.getIndex());

        //??????????????????
        PageRequest pageRequest = new PageRequest(page.getPage()-1, page.getSize(), sort);
        return accountDao.findAll(pageRequest).getContent();

    }

    @RequestMapping("/findByParam")
    public Account findByParam(int id) {

        // getOne?????????????????? findOne????????????,Optional ???????????????????????????????????????????????????????????????
        //getOne??????????????????????????????????????????json????????????????????????
        Account one = accountDao.getOne(3);
        System.out.println(one.toString());

        //??????  `??????` ??????Example
        //????????????????????????int???duble????????????0??????????????????
        Account account = new Account();
        account.setId(10);
        account.setMoney(12.0);

        // ????????????????????????isPresent()????????????true ?????? get() ???????????????????????????.
        Example<Account> example = Example.of(account);
        Optional<Account> optional = accountDao.findOne(example);

        return optional.orElse(null);
    }




}
