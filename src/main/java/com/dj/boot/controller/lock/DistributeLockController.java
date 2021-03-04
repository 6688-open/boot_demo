package com.dj.boot.controller.lock;

import com.dj.boot.annotation.DistributeLock;
import com.dj.boot.common.enums.LockType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.lock
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-17-10-58
 */
@RestController
@RequestMapping("/lock/")
public class DistributeLockController {

    @DistributeLock(type = LockType.LOCK, name = "lock")
    @GetMapping("testLock")
    public void testLock() throws Exception {
        System.out.println("test_lock");
        System.out.println("需要加分布式锁的业务逻辑");
        // 此处测试 如果处理 业务逻辑 报错 是否会 解锁（防止死锁）
        //throw new Exception("业务报错");
    }

    @GetMapping("/lockTimed")
    public void testLockTimed() {
        System.out.println("test_lockTimed");

    }

    @GetMapping("/tryLock")
    public void testTryLock() {

    }

    @GetMapping("tryLockTimed")
    public void testTryLockTimed(@RequestParam int time, @RequestParam TimeUnit unit) {

    }
}
