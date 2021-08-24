package com.controller;

import com.dao.AccountDao;
import com.entity.Account;
import com.entity.BaseAccountPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
        //获取排序对象
        Sort.Direction direction = Sort.Direction.ASC.toString().equalsIgnoreCase(page.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        //根据排序对象排序
        Sort sort = new Sort(direction, page.getIndex());

        //创建分页对象
        PageRequest pageRequest = new PageRequest(page.getPage()-1, page.getSize(), sort);
        return accountDao.findAll(pageRequest).getContent();

    }

    @RequestMapping("/findByParam")
    public Account findByParam(int id) {

        // getOne返回引用对象 findOne返回实体,Optional 是一个包含或着不包含一个非空值的容器对象。
        //getOne查不到报错。查到又不可以直接json返回，原因不详。
        Account one = accountDao.getOne(3);
        System.out.println(one.toString());

        //根据  `实例` 查询Example
        //这里条件会自动把int、duble的默认为0，注意一下。
        Account account = new Account();
        account.setId(10);
        account.setMoney(12.0);

        // 如果一个值存在，isPresent()将会返回true 并且 get() 将会返回所对应的值.
        Example<Account> example = Example.of(account);
        Optional<Account> optional = accountDao.findOne(example);

        return optional.orElse(null);
    }




}
