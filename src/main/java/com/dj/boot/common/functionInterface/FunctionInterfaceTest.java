package com.dj.boot.common.functionInterface;

/**
 * @ClassName Test
 * @Description TODO
 * @Author wj
 * @Date 2019/12/31 18:47
 * @Version 1.0
 **/
@FunctionalInterface
public interface FunctionInterfaceTest {
    boolean test(int a, int b);


    //多个 dafault 方法
    default int mul (int x, int y) {
        return x + y;
    }

    // 多个 static 方法

    public  static int mul1 (int x, int y) {
        return x + y;
    }


}
