package com.dj.boot.controller.jsonserialize.deserializemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressModel {

    // 反序列化后的字段名称
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("addressName")
    private String addressName;


}
