package com.dj.boot.pojo.vo;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("Student")
public class Student {
    @XStreamAlias("age")
    private Integer age;
    @XStreamAlias("sex")
    private String sex;
}
