package com.dj.boot.configuration.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MyConditionalLinux implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //获取环境信息
        Environment environment = context.getEnvironment();
        String property = environment.getProperty("os.name");
        assert property != null;
        if (property.contains("Windows")) {
            return false;
        }
        return true;
    }
}
