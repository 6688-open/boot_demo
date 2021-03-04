package com.dj.boot.configuration.applicationcontext.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 半注解半xml方式
 * @author ext.wangjia
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Component
public class UserTestAno implements Serializable {

    @Value("3000")
    private Integer id;
    private Integer sex;
    @Value("Nike")
    private String userName;
    private String salt;
    private String password;





    public UserTestAno(Integer id, Integer sex, @Valid @NotEmpty(message = "用户名不能为空") String userName, String salt, @NotEmpty(message = "密码不能为空") String password) {
        this.id = id;
        this.sex = sex;
        this.userName = userName;
        this.salt = salt;
        this.password = password;
    }

}
