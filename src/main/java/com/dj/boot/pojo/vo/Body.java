package com.dj.boot.pojo.vo;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;

@Data
@XStreamAlias("Body")
public class Body {

    @XStreamAlias("studentList")
    private List<Student> studentList;
}
