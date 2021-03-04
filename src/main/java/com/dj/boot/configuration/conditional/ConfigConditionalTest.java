package com.dj.boot.configuration.conditional;


import com.dj.boot.configuration.conditional.corlor.Color;
import com.dj.boot.configuration.conditional.corlor.ColorFactoryBean;
import com.dj.boot.pojo.User;
import org.springframework.context.annotation.*;

@Configuration
//导入组件 id默认是组件的全类名
@Import({Color.class, MyImportSelector.class, MyImportBeanDefineRegistrar.class})
public class ConfigConditionalTest {




    @Bean("user01")
    //懒加载   启动的时候不创建对象  第一次获取 bean时创建对象     单实例bean 默认容器启动时创建对象
    //@Lazy
    // prototype  ioc容器启动时不会调用创建对像的方法 每次获取的时候才会创建
    //  singleton  默认模式 ioc容器启动时就会调用创建对像的方法  以后每次获取从容器中获取  缓存map.get()
    //     容器只创建一个实例 容器负责跟踪实例的状态 维护实例的生命周期
    @Scope("singleton")
    @Conditional({MyConditionalWindow.class})
    public User user01 () {
        return new User().setUserName("zs");
    }

    @Bean("user02")
    @Conditional({MyConditionalLinux.class})
    public User user02 () {
        return new User().setUserName("lisi");
    }


    @Bean
    public ColorFactoryBean colorFactoryBean () {
        return new ColorFactoryBean();
    }


}
