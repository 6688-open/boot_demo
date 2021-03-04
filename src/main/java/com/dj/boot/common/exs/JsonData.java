package com.dj.boot.common.exs;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口返回数据类
 * */
public class JsonData implements Serializable{

    private static final long serialVersionUID = 1449279030150760393L;

    private List<Serie> series = new ArrayList<>();

    private List<String> xaxis;

    private List<Map<String,Object>> otherList;

    private Map<String,Object> otherData;

    private Object extendData;

    public Object getExtendData() {
        return extendData;
    }

    public void setExtendData(Object extendData) {
        this.extendData = extendData;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public List<String> getXaxis() {
        return xaxis;
    }

    public void setXaxis(List<String> xaxis) {
        this.xaxis = xaxis;
    }

    public List<Map<String, Object>> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<Map<String, Object>> otherList) {
        this.otherList = otherList;
    }

    public Map<String, Object> getOtherData() {
        return otherData;
    }

    public void setOtherData(Map<String, Object> otherData) {
        this.otherData = otherData;
    }

    public JsonData() {
    }

    public JsonData(List<Map<String, Object>> otherList, String name) {
        this.otherList = otherList;
        Map<String,Object> other = new HashMap<>(1);
        other.put("title",name);
        this.otherData = other;
    }

    public JsonData(Object extendData) {
        this.extendData = extendData;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
