package com.dj.boot.pojo.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @ClassName User
 * @Description TODO
 * @Author wj
 * @Date 2019/12/6 19:49
 * @Version 1.0
 **/
@Data
@XStreamAlias("UserVo")
public class UserVo {
    @XStreamAlias("userName")
    private String userName;
    @XStreamAlias("email")
    private String email;

    @Override
    public String toString() {
        return "UserVo{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
