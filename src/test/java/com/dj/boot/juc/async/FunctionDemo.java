package com.dj.boot.juc.async;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionDemo {

    public static void main(String[] args) {
        // 函数式接口

        /**
         * 函数型接口 Function <T, R>   有参数  有返回  apply()
         */
        Function<String, Integer> function = s -> 33;
        System.out.println( function.apply("sss"));


        /**
         *  断定型接口 Predicate<T>  有参数  返回值 boolean  test()
         */
        Predicate<String> predicate = s -> false;
        System.out.println(predicate.test("222"));
        /**
         * 消费型接口  Consumer<T>     有参数   无返回      accept()
         */
        Consumer<String> consumer = s -> System.out.println(s.length());
        consumer.accept("ss");


        /**
         * 供给型接口 Supplier<T>    无参数  有返回     get()
         */
        Supplier<String> supplier = () -> "222";
        System.out.println(supplier.get());


    }
}

interface MyInterface {

    public Integer myInt (Integer x);

    public  boolean isOk(String str);

    public void consumer (String str);

    public String sup ();


}

