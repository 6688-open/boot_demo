package com.dj.boot.annotation;


import com.dj.boot.common.enums.LockType;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.annotation
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-17-10-49
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeLock {

    /**
     * 锁类型
     *
     * @return
     */
    LockType type() default LockType.LOCK;

    /**
     * 分布式锁 名称
     *
     * @return
     */
    String name() default "lock";

    long leaseTime() default -1;

    TimeUnit unit() default TimeUnit.SECONDS;
}
