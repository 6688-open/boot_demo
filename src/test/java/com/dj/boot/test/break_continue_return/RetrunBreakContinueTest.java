package com.dj.boot.test.break_continue_return;

import com.dj.boot.common.util.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Test1
 * @Description TODO
 * @Author wj
 * @Date 2020/1/3 11:47
 * @Version 1.0
 **/
public class RetrunBreakContinueTest {

    public static void main(String[] args) {
        // 外层循环，outer作为标识符
        outer:
        for (int i = 0; i < 5 ; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("i的值为:" + i + " j的值为:" + j);
                if (j == 1) {
                    // 跳出outer标签所标识的循环。 否则跳出的是j的循环
                    break outer;
                }
            }
        }

        // break：直接结束一个循环，跳出循环体。break以后的循环体中的语句不会继续执行，循环体外面的会执行
        for (int i = 0; i < 6 ; i++) {
            LogUtils.info(String.valueOf(i));
            if (i == 3) {
                break;
            }
        }


        //continue：中止本次循环，继续下次循环。
        // continue以后的循环体中的语句不会继续执行，下次循环继续执行，循环体外面的会执行
        //当满足条件时  continue以后的语句不在执行
        for (int i = 0; i < 6 ; i++) {
            if (i == 3) {
                continue;
            }
            LogUtils.info(String.valueOf(i));
        }

        test();






        /**
         * foreach()处理集合时不能使用break和continue这两个方法，
         * 也就是说不能按照普通的for循环遍历集合时那样根据条件来中止遍历，
         * 而如果要实现在普通for循环中的效果时，
         * 可以使用return来达到
         *
         * return起到的作用和continue是相同的。
         */
        List<String> list = Arrays.asList("123", "45634", "7892", "abch", "sdfhrthj", "mvkd");
        list.stream().forEach(e ->{
            if(e.length() >= 5){
                return;
            }
            System.out.println(e);
        });


    }


    //return：return的功能是结束一个方法。
    // 一旦在循环体内执行return，将会结束该方法，循环自然也随之结束。
    // 与continue和break不同的是，return直接结束整个方法，不管这个return处于多少层循环之内。
    public static String test() {
        for (int i = 0; i < 5; i++) {
            if (i==2) {
                return "22222";
            }
        }
        return "3333";
    }




}
