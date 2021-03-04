package com.dj.boot.pojo.vo;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;

@Data
@XStreamAlias("Header")
public class Header {

    @XStreamAlias("userVoList")
    private List<UserVo> userVoList;
}
