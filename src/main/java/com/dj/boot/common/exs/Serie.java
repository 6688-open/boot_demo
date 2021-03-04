package com.dj.boot.common.exs;

import java.io.Serializable;
import java.util.List;

/**
 * 系列数据
 * */
public class Serie implements Serializable {

    private String name;

    private List<String> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public Serie(String name, List<String> data) {
        this.name = name;
        this.data = data;
    }

    public Serie() {
    }
}
