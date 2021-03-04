package com.dj.boot.configuration.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP :   动态代理
 *       程序在运行期间将某段代码切入到指定方法位置进行编程的方式
 *
 *       1 导入 aop模块  Spring Aop  org.aspectj
 *       2 定义逻辑类  MathCalculator  业务逻辑运行时 记录日志
 *       3 定义日志切面类  切面类里的方法动态感知 运行到哪一步了
 *            通知方法
 *              前置通知(@Before)  logStart  在目标方法（div）前运行
 *              后置通知 (@After)  logEnd    在目标方法（div）后运行  无论正常还是异常
 *              返回通知 (@AfterReturning)   logReturn   在目标方法（div）正常返回后运行
 *              异常通知 (AfterThrowing)  logException  在目标方法（div）异常   返回后运行
 *              环绕通知 (@Around)  动态代理   手动推进方法运行 （joinPoint。procced（））
 *
 *      4 给切面类的目标方法标注何时运行 通知注解
 *      5 将切面类 业务逻辑类  目标方法所在的类 都加入到容器中
 *      6 必须告诉Spring 哪个类是切面类  （加注解 @Aspect  ）
 *      7 给配置类加 EnableAspectJAutoProxy 开启基于注解的AOP模式
 */
@EnableAspectJAutoProxy
@Configuration
public class AOPConfig {


    /**
     * 业务逻辑加入到容器中
     * @return
     */
    @Bean
    public MathCalculator calculator () {
        return new MathCalculator();
    }

    /**
     * 切面类加入到容器中
     * @return
     */
    @Bean
    public LogAspects LogAspects () {
        return new LogAspects();
    }
}
