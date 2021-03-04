package com.dj.boot.test.initia;

import com.dj.boot.common.util.LogUtils;

/**
 * @ClassName InitiaTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/8 15:08
 * @Version 1.0
 **/
public class InitiaTest {
    //静态变量
    public static String staticField = "静态变量";
    //变量
    public String field = "----------普通变量-------------";

    //静态代码块
    static {
        LogUtils.info(staticField);
        LogUtils.info("静态代码块");
    }


    //初始化块  非静态代码块
    {
        LogUtils.info(field);
        LogUtils.info("-------------非静态代码块 -----------");
    }

    //构造方法

    public InitiaTest() {
        LogUtils.info("构造方法");
    }

    public static void main(String[] args) {
        new InitiaTest();
    }


    // 初始化 顺序   静态代码块  非静态代码块  构造函数




}
