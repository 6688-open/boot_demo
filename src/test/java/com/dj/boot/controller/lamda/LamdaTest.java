package com.dj.boot.controller.lamda;

import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.common.functionInterface.FunctionInterfaceTest;
import com.dj.boot.common.util.LogUtils;

/**
 * @ClassName LamdaTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/2 17:04
 * @Version 1.0
 **/

/** 复制括号   ->  落地 {}
 * functionInterface
 * dafault 方法
 * static 方法
 *
 */
public class LamdaTest  extends BootDemoApplicationTests {
    @Override
    public void run() {


        /**
         *  案例1 无参无返回
         */
        // 1.传统方式 需要new接口的实现类来完成对接口的调用
        ICar car1 = new IcarImpl();
        car1.drive();

        // 2.匿名内部类使用
        ICar car2 = new ICar() {
            @Override
            public void drive() {
                System.out.println("Drive BMW");
            }
        };
        car2.drive();

        // 3.无参无返回Lambda表达式
        ICar car3 = () -> {System.out.println("Drive Audi");};
        // ()代表的就是方法，只是匿名的。因为接口只有一个方法所以可以反推出方法名
        // 类似Java7中的泛型 List<String> list = new ArrayList<>(); Java可以推断出ArrayList的泛型。
        car3.drive();

        // 4.无参无返回且只有一行实现时可以去掉{}让Lambda更简洁
        ICar car4 = () -> System.out.println("Drive Ferrari");
        car4.drive();

        // 去查看编译后的class文件 大家可以发现 使用传统方式或匿名内部类都会生成额外的class文件，而Lambda不会


        /**
         * 案例2 有参有返回值
         */
        // 1.有参无返回
        IEat eat1 = (String thing) -> System.out.println("eat " + thing);
        eat1.eat("apple");

        // 参数数据类型可以省略
        IEat eat2 = (thing) -> System.out.println("eat " + thing);
        eat2.eat("banana");

        // 2.多个参数
        ISpeak speak1 = (who, content) -> System.out.println(who + " talk " + content);
        speak1.talk("xinzong", "hello word");

        // 3.返回值
        IRun run1 = () -> {return 10;};
        run1.run();

        // 4.返回值简写
        IRun run2 = () -> 10;
        run2.run();


        /**
         * 案例3 final类型参数
         */
        // 全写
        IAddition addition1 = (final int a, final int b) -> a + b;
        addition1.add(1, 2);
        // 简写
        IAddition addition2 = (a, b) -> a+b;
        addition2.add(2, 3);


        // 使用Lambda启动线程
        // 1.传统方式使用
//        Thread t1 = new Thread();
//        t1.start();


        // 2.匿名内部类使用
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("q1111111");
            }
        });

        // 3.Lambda使用
        Thread thread3 = new Thread(() -> System.out.println("222222222222222"));
        thread3.start();
        // 更简写法1
        new Thread(() -> System.out.println("t4 running...")).start();

        // 更简写法2
        Process.process(()-> System.out.println("t5 running..."));




        //测试函数式接口
        Integer a = 1;
        String age = "12";
        Boolean isFail = false;
//        if (a == 1) {
//            LogUtils.info(age + isFail);
//        }
//        if (a == 2) {
//            LogUtils.info(age + isFail);
//        }
//        if (a == 3) {
//            LogUtils.info(age + isFail);
//        }
        //()代表的就是方法，只是匿名的。因为接口只有一个方法所以可以反推出方法名
        FunctionInterfaceTest test1 =(int c, int b) -> a == 1 ;
        FunctionInterfaceTest test = new FunctionInterfaceTest() {

            @Override
            public boolean test(int a, int b) {
                return false;
            }
        };

        testOne((c, b) -> {return false;}, age, isFail);
        testOne((int c, int b) -> a == 2, age, isFail);
        testOne((int c, int b) -> a == 3, age, isFail);

    }



    interface ICar {

        void drive();
    }

    class IcarImpl implements ICar{

        @Override
        public void drive() {
            System.out.println("Drive Benz");
        }
    }






    interface IEat {

        void eat(String thing);
    }

    interface ISpeak {
        void talk(String who, String content);
    }

    interface IRun {

        int run();
    }




    interface IAddition {

        int add(final int a, final int b);
    }




    public  void testOne(FunctionInterfaceTest test, String age, Boolean isFail){
        if (test.test(1, 2)) {
            LogUtils.info(age + isFail);
        }
    }




    interface Process {
        // Java8中允许接口中定义非抽象方法 前提该方法必须为 default 或 static
        static void process(Runnable runnable) {
            new Thread(runnable).start();
        }
    }




}


/**
 * @ClassName Test
 * @Description TODO
 * @Author wj
 * @Date 2019/12/31 18:47
 * @Version 1.0
 **/
@FunctionalInterface
interface FunctionInterfaceTest1 {
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







