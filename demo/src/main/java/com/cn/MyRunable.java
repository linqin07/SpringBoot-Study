package com.cn;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Description:
 * @author: LinQin
 * @date: 2020/06/16
 */
public class MyRunable implements Runnable {
    @Value("${gg:gg}")
    private String gg;
    @Override
    public void run() {
        System.out.println(gg);
    }
}
