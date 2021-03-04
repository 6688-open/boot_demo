package com.dj.boot.configuration.applicationcontext.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.FactoryBean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author ext.wangjia
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserTest  implements FactoryBean<UserTest> {

    private Integer id;
    private Integer sex;
    private String userName;
    private String salt;
    private String password;





    public UserTest(Integer id, Integer sex, @Valid @NotEmpty(message = "用户名不能为空") String userName, String salt, @NotEmpty(message = "密码不能为空") String password) {
        this.id = id;
        this.sex = sex;
        this.userName = userName;
        this.salt = salt;
        this.password = password;
    }


    /**
     * ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext/applicationContext.xml");
     *  UserTest userTest = context.getBean(UserTest.class);
     *
     * 实现了FactoryBean接口的bean是一类叫做factory的bean。
     * 其特点是，spring会在使用 getBean()调用获得该bean时，
     * 会自动调用该bean的getObject()方法，所以返回的不是factory这个bean，
     * 而是这个bean.getOjbect()方法的返回值：
     */
    @Override
    public UserTest getObject() throws Exception {
        return getObj();
    }

    @Override
    public Class<?> getObjectType() {
        return UserTest.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private UserTest getObj(){
        UserTest userTest = new UserTest();
        userTest.setId(1);
        userTest.setPassword("1");
        userTest.setSalt("1");
        userTest.setSex(1);
        userTest.setUserName("1");
        return userTest;
    }
}
