package com;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: LinQin
 * @date: 2020/01/06
 */
public class Test {
    public static void main(String[] args) {
        URI uri = URI.create("http://192.168.13.51:8080/aa/bb/cc");
        System.out.println(uri.getPath());

        // 骚操作
        Map map = new HashMap() {{
            put("1", "1");
            put("2", "2");
        }};

        List list = new ArrayList() {{
            add("1");
            add("2");
        }};


    }
}
