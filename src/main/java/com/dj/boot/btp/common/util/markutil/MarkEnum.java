package com.dj.boot.btp.common.util.markutil;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @Date 2020-04-23 18:26
 */
public interface MarkEnum {

    /**
     * 获取位数
     */
    int bit();

    /**
     * 位置值
     */
    char value();

    /**
     * 该标记的名称
     */
    String desc();

    /**
     * 该位置的名称
     */
    MarkEnum getMarkEnum(int bit, char value);

}
