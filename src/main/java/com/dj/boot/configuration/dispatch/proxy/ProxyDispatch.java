package com.dj.boot.configuration.dispatch.proxy;

import java.lang.annotation.*;
/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.dispatch.proxy
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-17-10-41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProxyDispatch {
    /**
     * 请求选择参数
     * @return
     */
    String value();
}
