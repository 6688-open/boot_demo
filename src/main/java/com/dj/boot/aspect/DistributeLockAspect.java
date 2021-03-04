package com.dj.boot.aspect;

import com.dj.boot.annotation.DistributeLock;
import com.dj.boot.common.enums.LockType;
import com.dj.boot.service.lock.RedissonService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.aspect
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-17-10-56
 */

@Aspect
@Component
public class DistributeLockAspect {

    @Autowired
    RedissonService redissonService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.dj.boot.annotation.DistributeLock)")
    public void doBusiness() {

    }

    /**
     * 前事件
     *
     * @param joinPoint
     */
    @Before("doBusiness()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取执行方法
        Method method = signature.getMethod();
        // 获取注解信息
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);

        if (null != distributeLock) {
            //获取注解参数值
            String lockName = distributeLock.name();
            LockType type = distributeLock.type();

            if (type == LockType.LOCK) {
                redissonService.lock(lockName,30, TimeUnit.SECONDS);
                System.out.println("加锁成功");
            }

        }


    }

    /**
     * 后事件
     *
     * @param joinPoint
     */
    @After("doBusiness()")
    public void doAfter(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取执行方法
        Method method = signature.getMethod();
        // 获取注解信息
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);
        //获取执行方法名
        String methodName = method.getName();
        //获取方法传递的参数
        Object[] args = joinPoint.getArgs();

        if (null != distributeLock) {
            //获取注解参数值
            String lockName = distributeLock.name();

            if (null != distributeLock) {

                redissonService.unlock(lockName);
                System.out.println("解锁成功");

            }
        }

    }
}
