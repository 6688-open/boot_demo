package com.dj.boot.test.object;

import com.dj.boot.common.util.LogUtils;
import lombok.Data;

import java.util.Objects;

/**
 * @ClassName ObjectTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/8 15:27
 * @Version 1.0
 **/
@Data
public class ObjectTest implements Cloneable {

    private String name;

    private Integer age;


    public void eat () {
        LogUtils.info("------------eat -------------  方法");
    }







    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectTest that = (ObjectTest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
