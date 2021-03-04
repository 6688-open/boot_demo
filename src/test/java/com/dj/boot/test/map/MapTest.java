package com.dj.boot.test.map;

import com.dj.boot.common.util.LogUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName LengthTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/8 11:37
 * @Version 1.0
 **/
public class MapTest {
    /**
     *  一个集合里面有多少重复的元素和 个数 并且打印
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        String [] s = {"qq", "eee", "xxx", "xxx", "qq", "qq", "ff"};
        List<String> asList = Arrays.asList(s);
        List<String> asList1 = Lists.newArrayList();
        asList1.add("222");
        //asList1.add("333");
        List<String> strings = asList.subList(1, asList.size());
        List<String> strings1 = asList1.subList(1, asList1.size());
        asList.forEach(a -> {
            if (map.containsKey(a)) {
                map.put(a, map.get(a) + 1);
            } else {
                map.put(a, 1);
            }
        });



        Map<String, Integer> map1 = map.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        map1.forEach((k, v) -> {
            System.out.println(k+": " + map1.get(k));
        });

        LogUtils.info("--------------------------------------");


        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            System.out.println(keys.next() + " :" +map.get(keys.next()));
        }

    }



    @Test
    public  void LengthTest() {
        List<String> list = Lists.newArrayList("A","BA");
        //String -> String.length()  以A的长度为key
        ImmutableMap<Integer, String> uniqueIndex = Maps.uniqueIndex(list, String -> String.length());
        ImmutableMap<String, String> uniqueIndex1 = Maps.uniqueIndex(list, String -> String + "key");
        System.err.println(uniqueIndex.keySet());
        String a = "Microsoft22222222222222222222222222";
        LogUtils.info(a.length()+"");
    }
}
