package com.dj.boot.configuration.conditional;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 返回值  导入到容器中的组件的全类名
        // AnnotationMetadata 当前 @Import 注解类上的所有注解信息
        //importingClassMetadata.getAnnotationTypes();
        return new String[] {"com.dj.boot.configuration.conditional.corlor.Yellow", "com.dj.boot.configuration.conditional.corlor.Bule"};
    }
}
