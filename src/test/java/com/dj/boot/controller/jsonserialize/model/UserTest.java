package com.dj.boot.controller.jsonserialize.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName UserTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/17 11:15
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class UserTest {

    private Integer id;

    private String userName;

    private String userAge;

    private List<Address> addressList;

}
