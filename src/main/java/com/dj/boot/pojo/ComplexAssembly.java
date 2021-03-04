package com.dj.boot.pojo;

import java.util.*;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-13-10-20
 */
public class ComplexAssembly {

    private Long id;
    private List<String> list;
    private Map<String,String> map;
    private Properties properties;
    private Set<String> set;
    private String[] array;

    @Override
    public String toString() {
        return "ComplexAssembly{" +
                "id=" + id +
                ", list=" + list +
                ", map=" + map +
                ", properties=" + properties +
                ", set=" + set +
                ", array=" + Arrays.toString(array) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }
}
