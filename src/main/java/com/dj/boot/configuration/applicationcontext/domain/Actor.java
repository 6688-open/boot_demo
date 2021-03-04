package com.dj.boot.configuration.applicationcontext.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.applicationcontext.domain
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-27-14-25
 */
@Component
public class Actor {

    @Value("Dave")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                '}';
    }
}
