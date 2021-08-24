package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/04
 */
@Entity
public class Account {
    @Id
    @GeneratedValue
    private int id ;
    // 空的字段就去掉返回json改字段
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name ;
    // 指定返回的json属性
//    @JsonProperty("xxx")

    @JsonIgnore
    private double money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}