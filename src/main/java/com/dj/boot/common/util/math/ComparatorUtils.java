package com.dj.boot.common.util.math;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 排序
 */
public class ComparatorUtils {


   public static  void sortById(List<Long> list){
        //Long类型比较器
        Comparator<Long> comparator = new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        };
        Collections.sort(list, comparator);
    }
}
