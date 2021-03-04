package com.dj.boot.controller.jsonserialize.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName Address
 * @Description TODO
 * @Author wj
 * @Date 2020/1/17 11:16
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class Address {

    private Integer id;

    private String addressName;


}
