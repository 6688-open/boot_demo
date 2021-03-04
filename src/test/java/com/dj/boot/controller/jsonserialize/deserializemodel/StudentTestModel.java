package com.dj.boot.controller.jsonserialize.deserializemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentTestModel {


    //反序列化时  序列化的数据是student_id  找到 student_id  赋值给 id
    @JsonProperty("student_id")
    private Integer id;
    @JsonProperty("student_name")
    private String studentName;
    @JsonProperty("student_age")
    private String studentAge;

}
