package com.dj.boot.aspect.holder;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.aspect.holder
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-01-15-01
 */
public class LoginUserContextHodler {

    private static ThreadLocal<UserAndPermissionContext> contextHolder = new InheritableThreadLocal();

    public LoginUserContextHodler() {
    }

    public static UserAndPermissionContext context() {
        return (UserAndPermissionContext)contextHolder.get();
    }

    public static UserAndPermissionContext get() {
        return (UserAndPermissionContext)contextHolder.get();
    }

    public static void set(UserAndPermissionContext context) {
        if(null != context) {
            contextHolder.set(context);
        }
    }

    public static void remove() {
        contextHolder.remove();
    }
}
