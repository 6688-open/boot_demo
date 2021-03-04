package com.dj.boot.test.list;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {


        //String[] arr={"AAA","BBB","CCC"};

       List lists= Arrays.asList("AAA", "BBB", "CCC");

        List<?> list = new ArrayList<>(); // 通配符泛型  当做入参 和 返回值



        String a = null;
        if (StringUtils.isNoneBlank(a) && !a.contains("null")) {
            System.out.println(111);
        } else {
            System.out.println(222);
        }
        test();
    }


    @Scheduled(cron = "*/5 * * * * ?")
    public static void test() {
        List<String> l = new ArrayList<>();
        l.add("a");
        l.add("b");
        l.add("c");
        l.add("d");
        l.forEach(System.out::println);
        Integer a = 0;
        for (String s : l) {
            a++;
            System.out.println(a+":"+ s);
        }
    }
}
