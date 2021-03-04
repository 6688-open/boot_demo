package com.dj.boot.controller.jsonserialize.deserializemodel;

import com.dj.boot.controller.jsonserialize.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTestModel {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("userAge")
    private String userAge;
    @JsonProperty("addressList")
    private List<Address> addressList;

}
