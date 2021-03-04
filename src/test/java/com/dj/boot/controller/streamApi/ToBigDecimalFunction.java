package com.dj.boot.controller.streamApi;

import java.math.BigDecimal;

/**
 * @ClassName StreamApiTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/2 11:50
 * @Version 1.0
 **/
@FunctionalInterface
public interface ToBigDecimalFunction<T> {
    BigDecimal applyAsBigDecimal(T value);
}
