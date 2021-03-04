package com.dj.boot.controller;

import com.dj.boot.BootDemoApplicationTests;

import java.util.regex.Pattern;

/**
 * @ClassName TestOne
 * @Description TODO
 * @Author wj
 * @Date 2019/12/23 16:37
 * @Version 1.0
 **/
public class TestOne  extends BootDemoApplicationTests {
    @Override
    public void run() {
        //long l = Long.parseLong((String) ("Y000000615"));
        String str = "Y000000615";
        //Pattern pattern = Pattern.compile("^-?[0-9]+");
        if(!Pattern.compile("^-?[0-9]+").matcher(str).matches()){
            //数字
            System.out.println(333);
        } else {
            //非数字
            System.out.println(222);
        }
        System.out.println(111);
    }
}
