package com.dj.boot.test.initia;

import com.dj.boot.common.util.LogUtils;

/**
 * @ClassName InitiaTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/8 15:08
 * @Version 1.0
 **/
public class FatherClass {
    //静态变量
    public static String staticField = "父类  -------------静态变量";
    //变量
    public String field = "------父类 ----普通变量-------------";

    //静态代码块
    static {
        LogUtils.info(staticField);
        LogUtils.info("----父类 ----   静态代码块");
    }


    //初始化块  非静态代码块
    {
        LogUtils.info(field);
        LogUtils.info("------父类-------非静态代码块 -----------");
    }

    //构造方法

    public FatherClass() {
        LogUtils.info("父类======================构造方法");
    }





}
