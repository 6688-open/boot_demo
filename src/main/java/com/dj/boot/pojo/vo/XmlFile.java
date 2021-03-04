package com.dj.boot.pojo.vo;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("XmlFile")
public class XmlFile {
    @XStreamAlias("header")
    private Header header;
    @XStreamAlias("body")
    private Body body;

}
