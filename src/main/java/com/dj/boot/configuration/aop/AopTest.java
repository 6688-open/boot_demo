package com.dj.boot.configuration.aop;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopTest {

    @Test
    public void test01 () {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AOPConfig.class);

        //只有从容器中获取  不要自己创建
//        MathCalculator calculator = new MathCalculator();
//        calculator.div(1,1);

        MathCalculator calculator = context.getBean(MathCalculator.class);
        calculator.div(6, 3);

        context.close();


    }
}
