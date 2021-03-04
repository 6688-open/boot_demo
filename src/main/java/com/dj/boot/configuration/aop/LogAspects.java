package com.dj.boot.configuration.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;


@Aspect
public class LogAspects {

    /**
     * 抽取公共的切入点表达式
     * 1 本类引用
     * 2 其他的切面引用  com.dj.boot.configuration.aop.LogAspects.pointCut()全路径
     */
    //@Pointcut("execution(public * com.dj.boot.controller..*.*(..))")
    @Pointcut("execution(public * com.dj.boot.configuration.aop.MathCalculator.*(..))")
    public void pointCut() {

    }


    //@Before z在方法之前切入 切入点表达式  指定的在哪个方法切入
    //@Before("public int com.dj.boot.configuration.aop.MathCalculator.div(int, int)")
    @Before("com.dj.boot.configuration.aop.LogAspects.pointCut()")
    public void logStart (JoinPoint joinPoint) {
        System.out.println("方法名 : "+joinPoint.getSignature().getName()+"运行   @Before；参数列表 ： {"+ Arrays.toString(joinPoint.getArgs()) +"}");
    }

    @After("pointCut()")
    public void logEnd (JoinPoint joinPoint) {
        System.out.println("方法"+ joinPoint.getSignature().getName()+ "结束  @After ：参数列表 ：{"+ Arrays.toString(joinPoint.getArgs()) +"}");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn (JoinPoint joinPoint, Object result) {
        System.out.println("方法 "+joinPoint.getSignature().getName()+"正常返回  @AfterReturning ：运行结果 ： {"+ result +"}");
    }
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException (JoinPoint joinPoint, Exception exception) {
        System.out.println("方法 "+ joinPoint.getSignature().getName()+"异常运行  @AfterThrowing ：参数列表 ： {"+exception+"}");
    }

}
