package com.dj.boot.service.proxytest.staticproxy;

/**  委托类和代理类之间的约束接口Cat
     约束接口实现类 Lion，实现 Cat 接口，委托角色
     代理类实现 FeederProxy，实现Cat 接口，并含有一个 Cat接口引用属性。 代理角色，
     代理 cat接口属性引用实例的行为并可以新增公共逻辑
 *
 *
 *
 *
 *
 * @Description: 静态代理类接口, 委托类和代理类都需要实现的接口规范。
 * 定义了一个猫科动物的两个行为接口，吃东西，奔跑。
 * 作为代理类 和委托类之间的约束接口
 */
public interface Cat {
    String eatFood(String foodName);
    boolean running();
}
