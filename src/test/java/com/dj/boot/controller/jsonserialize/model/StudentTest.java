package com.dj.boot.controller.jsonserialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName StudentTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/17 11:14
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class StudentTest {
    //@JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，
    // 如把trueName属性序列化为name，@JsonProperty("name")
    @JsonProperty("student_id")
    private Integer id;
    @JsonProperty("student_name")
    private String studentName;
    @JsonProperty("student_age")
    private String studentAge;

}
