package com.dj.boot.jvm.classLoader;


import com.baomidou.mybatisplus.extension.api.R;
import com.dj.boot.pojo.User;

import java.lang.ref.SoftReference;
import java.util.Random;

/**
 * 1  JVM 系统架构
 *
 * 2  类加载器
 *
 * BootStrap  ClassLoader  JAVAHOME/jre/lib/rt.jar  启动类加载器  JDK 的类加载到内存中   C++ 编写
 *
 * Extension ClassLoader JAVAHOME/jre/lib/ext/*.jar   标准扩展类加载器  将其他的exe中的类 加载到内存中  Java 编写
 *
 * App ClassLoader  应用程序类加载器   负责将应用程序的类加载到内存中
 *
 * User  ClassLoader
 *
 *
 *  除了启动类加载器 都有父类
 *     双亲委派机制    保证沙箱安全   保证写的代码 不污染源代码
 *     沙箱安全机制
 *
 *
 *     什么是类加载器
 *       负责加载class文件  将class文件字节码加载到内存中
 *       将内容转换成方法区的运行时数据结构
 *       ClassLoader 只负责class文件的加载
 *     （class文件开头有特定文件表示  cafe babc ）
 *
 *     哪几种
 *
 *     双亲委派
 *
 *     沙箱机制
 *
 * 3  Native
 *      native  是一个关键字
 *     多线程Thread.start  只有声明 没有实现  -->  java 调用操作系统底层的方法 （本地方法栈） 调用 C语言实现的第三方函数库
 *
 * 4 PC 寄存器
 *      当前线程所执行的字节码的行号指示器  解释器通过改变这个值选取下一条需要执行的字节码指令
 *      记录方法之间的调用和执行情况
 *      线程私有  每一个线程都有一个程序计数器 指向方法区的字节码 （用来存储指向下一条指令地址 即将执行的指令代码）
 *      由执行引擎读取下一条指令 可忽略不计
 *
 *
 *     java栈  本地方法栈  程序计数器  线程私有
 *
 *     方法区 堆 线程共享
 *
 *5  方法区
 *    线程共享的内存区域  类加载信息 它存储了每一个类的 结构信息    静态变量 常量 属性 方法
 *     模板 运行时常量池 字段 方法数据 构造函数 普通方法的字节码内容    永久代  元空间
 *
 *     JVM 规范将方法区描述为堆的一个逻辑部分  他还有一个别名 Non-Heap 非堆  目的是与堆分开
 *
 *    实例变量在堆内存中
 *
 *    方法区是一套规范
 *    空调 k1 = new 格力（）；
 *    List list = new ArrayList();
 *    //接口       实现类
 *
 *    jdk 7
 *     方法区 f = new 永久代
 *    jdk8
 *     方法区 f = new 元空间
 *
 *
 * 6 stack
 *    栈管运行  堆管存储
 *
 *    程序 = 算法+数据结构
 *           框架  + 业务逻辑
 *
 *    数据结构
 *       队列  （FIFO）  先进先出
 *       栈       先进后出  后进先出、
 *
 *       栈  栈内存 java程序的运行  是在线程创建的时候的创建
 *           他的生命周期跟随线程的生命周期 线程结束 栈内存释放
 *           没有垃圾回收的问题
 *       栈中分配什么？
 *         8种基本类型的变量 对象的引用变量 实例方法都是在函数的栈内存分配的
 *
 *     java 方法  ----> 在栈中  就是 栈帧
 *
 *     栈存储什么？
 *      本地变量  输入参数  输出参数 方法内的变量
 *      栈操作  记录出栈 入栈的操作
 *      栈帧数据 类文件 方法等等。。。。
 *
 *
 *      每执行一个方法都会产生一个栈帧 保存到栈的顶部 （后进先出）
 *      顶部栈就是当前的方法  该方法执行完毕会自动将此栈帧出栈
 *
 *      栈内存溢出
 *      Exception in thread "main" java.lang.StackOverflowError
 *
 *      错误分析
 *       原因
 *          1  栈空间 内存分配不够  栈内存大小  -Xss
 *          2 函数调用栈太深了,注意代码中是否有了循环调用方法而无法退出的情况
 *          3  递归调用方法=栈帧  栈帧不出栈
 *      如果某个线程的线程栈空间被耗尽，没有足够资源分配给新创建的栈帧，
 *      就会抛出 java.lang.StackOverflowError 错误。
 *
 *
 *
 *
 *  7 堆 栈 方法区的关系
 *
 *         HotSpot (JDK8) 使用指针的方式访问对象
 *        堆里存放实例对象   （访问类元数据的地址） 方法区的模板
 *        栈 里实例对象的引用地址
 *
 *
 *
 * 8 Heap 堆
 *      一个JVM只有一个堆内存 堆内存的大小可以调节  类加载器读取文件后 ，把类 方法 变量 常量放到堆内存中
 *       堆内存逻辑上分为    新生代  老年代  永久区 (JDK8 永久区变成元空间)   （ 物理上 新生代  老年代）
 *       新生代 Young Generation space   1/3
 *       老年代 Old Generation space     2/3
 *       永久区 Permanent space      （永久区是方法去的一个实现）
 *
 *       新生代
 *           Eden Space 伊甸区              8/10
 *           Survivor 0 Space  幸存0区      1/10
 *           Survivor 1 Space  幸存1区      1/10
 *
 *
 *     堆new 对象的流程
 *      所有的类都是在Eden区 NEW 出来的
 *      Eden 区 满了之后 GC = YGC = 轻GC
 *      Eden区基本清空  98% 回收清楚掉 剩下的进去 S0 = from区    S0区有 2%  以此类推
 *
 *      S0 = from 区
 *      S1 = to 区
 *
 *      from 区 和  to 区  他们的位置和名分是不固定的  每次GC完之后会交换
 *      谁空谁是To 区
 *
 *      YGC 过程  复制 -- 清空 --互换
 *
 *      复制 ：Eden  和 SurvivorFrom 复制到  SurvivorTo 年龄 + 1  15次晋升老年代
 *
 *         当Eden区满了触发第一次GC 存活的对象复制到SurvivorFrom区
 *         当Eden区满了 再次扫描 Eden 和 SurvivorFrom 进行垃圾回收  多次回收还存活的复制到 SurvivorTo  年龄 + 1
 *
 *      清空 ： 清空 Eden  和 SurvivorFrom
 *
 *          清空 Eden  和 SurvivorFrom 复制之后  From 和 To 互换  谁空谁是To
 *
 *      互换 ： SurvivorFrom  SurvivorTo  互换
 *
 *          SurvivorFrom  SurvivorTo 互换   SurvivorTo 成为下一次GC的 SurvivorFrom
 *          对象在 SurvivorFrom  SurvivorTo 复制来 复制去 交换15次 仍然存活 进入老年代
 *          JVM参数 MaxTenuringThreshold 决定 默认值15
 *
 *
 *      YGC 一次 存活的 年龄+1 达到15次 晋升到老年代
 *
 *      Old老年代  满了 开启
 *      Full FC = FGC  重GC
 *      Full GC 多次 Old老年代空间没办法腾出来
 *
 *      OOM 堆内存溢出  当JVM无法分配内存来创建一个对象的时候，java.lang.OutOfMemoryError
 *
 *      错误分析
 *          1 java堆内存分配不够 可以通过-Xms -Xmx来调整
 *          2 代码中创建了大量对象 并长时间不能被垃圾收集器收集 （存在被引用）
 *
 *
 *
 *  9 永久代  （元空间）
 *    永久代是方法区的一个实现）              rt.jar   ArrayList.......
 *    是一个常驻内存区域 用于存放JDK 自身锁携带的Class Interface 的元数据  他存储的是运行环境必须的类信息
 *    被装载到此区域的数据是不会被垃圾回收  关闭JVM 才会释放此区域的内存
 *
 *    java8 元空间不在虚拟机 而是使用的本机物理内存
 *
 *
 *
 *
 * 10 堆参数调优
 *    -Xms  设置初始分配大小 默认为物理内存的 1/64
 *    -Xmx  最大分配内存 默认为物理内存的1/4
 *    -XX：+ PrintGCDetails  详细输出GC处理日志
 *
 *    idea VM options 配置调参
 *    -Xms1024m -Xmx1024m -XX:+PrintGCDetails
 *
 *
 *    OutOfMemoryError  OOM
 *      在年老代执行了 Full GC 仍然无法惊醒对象的保存 就会产生OOM
 *
 *
 *  GC详细收集日志信息
 *
 *    规律 ： GC类型   名称   GC前占用内存  GC后占用内存  该区总大小
 *
 *    GC类型 YGC  内存分配失败   Young区       GC前->后Young区占用内存 （Young区总大小）    GC前-> 后堆内存占用  堆总内存     GC耗时            用户耗时     系统耗时    实际耗时
 *   [GC (Allocation Failure)   [PSYoungGen: 2515K->504K(2560K)]                         3447K->1683K(9728K),     0.0009653 secs]   [Times: user=0.00 sys=0.00, real=0.00 secs]
 *
 *
 *
 * GC类型 FullGC   内存分配失败         Young区    GC前->后Young区占用内存 （Young区总大小）
 *   [Full GC (Allocation Failure)    [PSYoungGen:  0K->0K(2048K)]
 *
 *   Old区    GC前->后Old区占用内存 （Old区总大小）   GC前-> 后堆内存占用  堆总内存
 *   [ParOldGen: 3808K->3790K(7168K)]                3808K->3790K(9216K),
 *
 *   元空间      GC前->后元空间占用内存 （元空间总大小）
 *   [Metaspace: 3418K->3418K(1056768K)],    0.0136135 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 *
 *
 *
 *
 *
 *
 *   11 GC 垃圾收集算法
 *      GC -- 分代收集算法    不是一起回收的
 *                频繁收集 Young区
 *                次数较少收集Old区
 *                基本不动 元空间
 *
 *
 *       Minor GC 和 Full GC  的区别
 *       YoungGC  只针对新生代的GC 因为新生代存活率不高 GC频繁 速度也快
 *       Full GC   发生在老年代的GC  Full GC 要比 Young GC  慢上10倍    1/3  2/3 空间差异
 *
 *     如何确定对象是垃圾 ？？？
 *        判断对象是否已经死亡的算法：引用计数算法，可达性分析算法；
 *
 *     如何回收  ？？？
 *
 *       四大算法
 *          引用计数法
 *                用法 ： 引用一次就 + 1  不引用 -1 当为 0 时 不再引用   （引用100000次）
 *                缺点：
 *                    1每次对对象赋值都要维护引用计数器  引用计数器自身消耗
 *                    2 难以处理循环引用
 *          复制算法
 *               YoungGC   采用的复制算法
 *               将内存分为两块 每次只用其中一块 当一块用完 将活着的分配到另一块
 *
 *               优点 ：不会产生内存碎片
 *               缺点 ： 耗空间
 *
 *          标记清除
 *                 当程序运行期间 可以使用的内存快耗尽的时候 GC线程触发 将程序暂停 将要回收的对象标记一遍 最后统一回收
 *                 完成标记清除 程序恢复
 *                Full GC 老年代 采用标记清除算法  一般由标记清除   或者 标记清除和标记整理混合实现
 *                算法分为标记 和 清除 两阶段
 *                     先标记要回收的对象 然后统一回收这些对象
 *                优点 ：节约内存空间
 *                缺点： 两次扫描 耗时严重  会产生内存碎片 内存不连续
 *          标记压缩（整理）
 *
 *             Full GC 老年代 采用标记清除算法  一般由标记清除   或者 标记清除和标记整理混合实现
 *             标记 清除 压缩
 *             优点 ：不会产生内存碎片
 *  *          缺点： 耗时严重  效率不高
 *
 *
 *        标记 清除  多次GC 再压缩
 *        第一次标记并进行一次筛选:
 *                       筛选的条件是此对象是否有必要执行finalize()方法。
 *
 *        。标记算法分为两种:
 *             1.引用计数算法(Reference Counting)
 *             2.可达性分析算法(Reachability Analysis)。
 *             由于引用技术算法无法解决循环引用的问题，所以这里使用的标记算法均为可达性分析算法。
 *
 *
 *
 *             可达性分析算法 ： 是通过可达性分析（Reachability Analysis）来判定对象是否存活的
 *
 *                  通过一些被称为引用链（GC Roots）的对象作为起点，从这些节点开始向下搜索，
 *                  搜索走过的路径被称为（Reference Chain)，当一个对象到GC Roots没有任何引用链相连时（即从GC Roots节点到该节点不可达），
 *                  则证明该对象是不可用的。
 *
             在Java中，可作为GC Root的对象包括以下几种：
             虚拟机栈（栈帧中的本地变量表）中引用的对象
             方法区中类静态属性引用的对象
             方法区中常量引用的对象
             本地方法栈中JNI（即一般说的Native方法）引用的对象


      七个垃圾收集器：Serial，SerialOld,ParNew,Parallel Scavenge,Parallel Old,CMS,G1.

 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class MyObject {


    //递归调用方法 main 方法进栈  然后m1进栈 。。。。m1 。。。
    // m1不出栈  main 就不会出栈 所以栈越积越多
    // Exception in thread "main" java.lang.StackOverflowError   不是异常  是错误
    /*Class StackOverflowError
    java.lang.Object
        java.lang.Throwable
                java.lang.Error
                      java.lang.VirtualMachineError
                               java.lang.StackOverflowError*/
    //方法的调用 把栈撑爆了
    private static void m1 () {
        m1();
    }


    public static void main(String[] args) {
        //rt.jar  被BootStrap ClassLoader 加载器加载到了JVM里面  可以直接调用  Object   ArrayList ......

        //实例 object
        Object object = new Object();
        //object.getClass()  通过反射 找到实例模板
        //object.getClass().getClassLoader() 找到类加载器
        /**
         * Object  jdk 自带 启动类加载器  BootStrap ClassLoader
         * C++ 语言写的  输出的是 null
         */
        System.out.println(object.getClass().getClassLoader()); //null


        //标准扩展类加载器  JAVAHOME  jre  lib  ext  扩展jar包



        //MyObject
        MyObject myObject = new MyObject();
        //应用程序类加载器


        //null
        System.out.println(myObject.getClass().getClassLoader().getParent().getParent());
        //sun.misc.Launcher$ExtClassLoader@566776ad
        System.out.println(myObject.getClass().getClassLoader().getParent());
        //sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(myObject.getClass().getClassLoader());






        //native
        Thread thread = new Thread();
        thread.start();

        //递归 栈内存溢出
        System.out.println("start...");
        //m1();
        System.out.println("end...");


        //堆参数调优

        System.out.println("CPU核数 : "+ "\t"+Runtime.getRuntime().availableProcessors());


        //最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        // 初始内存 当前内存 默认值
        long totalMemory = Runtime.getRuntime().totalMemory();

        System.out.println(" -Xmx : maxMemory : " + maxMemory +"字节"  +"\t"+ (maxMemory / (double)1024 / 1024) + "MB");
        System.out.println(" -Xms : totalMemory : " + totalMemory +"字节" +"\t" + (totalMemory / (double)1024 / 1024) + "MB");


        //生产上将Xmx Xms 设置成一样  避免忽高忽低 产生停顿
        //程序运行时 与 JVM垃圾回收 (GC)争抢内存


        //测试OOM    -Xms10m -Xmx10m -XX:+PrintGCDetails
        // Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        //byte[] bytes = new byte[40 * 1024 * 1024];

        //String 在进行拼接的时候 就会创建一个对象  而StringBuilder ..StringBuffer 在原来的基础上拼接
//        String  str = "www.baidu.com";
//        while (true) {
//            str += str + new Random().nextInt(88888888) + new Random().nextInt(9999999);
//        }


    }
}
