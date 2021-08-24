package com;

import com.google.common.collect.ImmutableList;
import org.apache.commons.beanutils.ConvertUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/05/17
 */
public class Test {
    public static void main(String[] args) {
//        long[] 数组转 Long[] 数组
        long[] lon = new long[]{1L, 2L, 3L};
        Long[] convert = (Long[]) ConvertUtils.convert(lon, Long[].class);
        for (Long item : convert) {
            System.out.println(item);
        }

        // List to Array
        List<String> list = ImmutableList.of("1", "2", "3", "4", "5", "6");
        String[] objects = (String[]) list.toArray(new String[list.size()]);
        System.out.println(Arrays.toString(objects));

        // Array to List
        Long[] loo = new Long[]{1L, 2L, 3L, 4L, 5L};
        List<Long> longs = Arrays.asList(loo);
//        List long2 = Lists.newArrayList(loo);
        List long2 = new ArrayList(Arrays.asList(loo));
//        System.out.println(long2);

        String s = list.stream().reduce("a232rrr",(i1, i2) -> i1 += i2);
        System.out.println(s);
    }
}
