package com.dj.boot.jvm.TransferValue;

import com.dj.boot.pojo.User;

public class TransferValue {


    public void value1 (int age) {
        age = 30;
    }

    public void value2 (User user) {
        user.setUserName("xxx");
    }

    public void value3 (String str) {
        str = "xxx";
    }


    public static void main(String[] args) {

        TransferValue test = new TransferValue();

        //基本类型 传递的是个副本 副本在value1中改变  但是原件的值不变
        // 基本类型 20  调用 test.value1(age); 时 age = 30 这时候value1入栈 出栈
        //然后value1 出栈 拉扯 main方法 main 方法输出age = 20
        int age = 20;
        test.value1(age);
        System.out.println("age" +"\t" + age);


        // 引用数据类型 main 方法 User user  指向堆里内存地址  new User() userName= abc
        // value2入栈 将User user 引用的地址abc 改为xxx
        // value2 出栈 拉扯main方法 这时候user  的引用地址已经改成了xxx
        User user = new User();
        user.setUserName("abc");
        test.value2(user);
        System.out.println("userName" + "\t" + user.getUserName());



        //引用类型  常量
        //放在 JDK8 方法区的 常量池里 有就引用 没有就新建
        //mian 方法  str 常量池 abc 没有 所以新建abc
        // value3 入栈 str xxx 没有 所以在常量池中新建 xxx  然后出栈
        //但是main 方法引用地址str 是abc
        String str = "abc";
        test.value3(str);
        System.out.println("str" + '\t' + str);






    }
}
