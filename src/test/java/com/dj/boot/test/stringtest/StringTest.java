package com.dj.boot.test.stringtest;

import com.dj.boot.pojo.User;
import com.dj.boot.test.TestDemo;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.test.stringtest
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-10-18-23
 */
public class StringTest {


    /**
     * java中的数据类型，可分为两类：
     * 1.基本数据类型，
     *      也称原始数据类型。byte,short,char,int,long,float,double,boolean
     *
     *      他们之间的比较，应用双等号（==）,比较的是他们的值。 它们以二进制位的形式直接保存它们的值。
     *
     *      基本数据类型比较(string 除外)， == 和 Equals 两者都是比较值；
     * 2.复合数据类型(类)
     *      类，接口，枚举，数组等。引用类型保存对象的地址而不是对象本身。
     *
     *    当他们用（==）进行比较的时候，比较的是他们在内存中的存放地址，
     *    所以，除非是同一个new出来的对象，他们的比较后的结果为true，否则比较后结果为false。
     *
     *     JAVA当中所有的类都是继承于Object这个基类的，在Object中的基类中定义了一个equals的方法，
     *     这个方法的初始行为是比较对象的内存地址， 对于复合数据类型之间进行equals比较，
     *     在没有覆写equals方法的情况下，他们之间的比较还是基于他们在内存中的存放位置的地址值的
     *
     * 但在一些类库当中这个方法被覆盖掉了，如String,Integer,Date，在这些类当中equals有其自身的实现，
     * 而不再是比较类在堆内存中的存放地址了。
     *
     * 因为Object的equals方法也是用双等号（==）进行比较的，所以比较后的结果跟双等号（==）的结果相同。
     *
     *
     *equals方法是在Object中就有。注意了这里的源码是Object里面的equals。
     *  public boolean equals (Object obj){
     *      return (this == obj);
     *  }
     * 从这个源码中你会发现，比较的是当前对象的引用和obj的引用是否相同，
     * 也就是说比较的默认就是地址。还记的在上面我们使用的是User而不是String嘛？
     * 在这里 == 比较的是引用的地址，equals也是比较的是引用的地址，所以他们的效果在这里是一样的。
     *
     * Object对象里面的 equals 和 ==  都是比较的内存地址
     *
     *
     *
     *
     *
     * String中equals方法
     * String在Object的基础之上重写了equals
     * public boolean equals(Object anObject) {
     *         if (this == anObject) {
     *             return true;
     *         }
     *         if (anObject instanceof String) {
     *             String anotherString = (String)anObject;
     *             int n = value.length;
     *             if (n == anotherString.value.length) {
     *                 char v1[] = value;
     *                 char v2[] = anotherString.value;
     *                 int i = 0;
     *                 while (n-- != 0) {
     *                     if (v1[i] != v2[i])
     *                         return false;
     *                     i++;
     *                 }
     *                 return true;
     *             }
     *         }
     *         return false;
     *     }
     *     String中的equals方法其实比较的是字符串的内容是否一样。
     *     也就是说如果像String、Date这些重写equals的类，你可要小心了。使用的时候会和Object的不一样。
     *
     *         String str1 = "Hello";
     *         String str2 = new String("Hello");
     *         String str3 = str2; //引用传递
     *         System.out.println(str1 == str2); //false
     *         System.out.println(str1 == str3); //false
     *         System.out.println(str2 == str3); // true
     *         System.out.println(str1.equals(str2)); //true
     *         System.out.println(str1.equals(str3)); //true
     *         System.out.println(str2.equals(str3)); //true
     *
     *
     *     （1）String str1 = "Hello"会在堆区存放一个字符串对象
     *
     *     （2）String str2 = new String("Hello")会在堆区再次存放一个字符串对象
     *
     *      （3）String str3 = str2这时候Str3和Str2是两个不同的引用，但是指向同一个对象。
     *
     *
     *
     *
     * 4、总结：
     *    1、基本数据类型比较
     * 　　==和Equals都比较两个值是否相等。相等为true 否则为false；
     *
     * 　　2、引用对象比较
     * 　　  ==和Equals都是比较栈内存中的地址是否相等 。相等为true 否则为false；
     *
     *      （1）、基础类型比较
     *
     *          使用==比较值是否相等。
     *
     *      （2）、引用类型比较
     *
     *          ①重写了equals方法，比如String。
     *
     *          第一种情况：使用==比较的是String的引用是否指向了同一块内存
     *
     *          第二种情况：使用equals比较的是String的引用的对象内容是否相等。
     *
     *      ②没有重写equals方法，比如User等自定义类
     *
     *          ==和equals比较的都是引用是否指向了同一块内存。
     *
     *
     *
     *
     *
     *
     *
     *               String str1 = "Hello";
     *               String str2 = new String("Hello");
     *               s2= s2.intern();
     *               System.out.println(str1 == str2); //true
     *               System.out.println(str1.equals(str2)); //true
     *
     *          在这里多了一个intern方法。他的意思是检查字符串池里是否存在，
     *          如果存在了那就直接返回为true。因此在这里首先s1会在字符串池里面有一个，
     *          然后 s2.intern()一看池子里有了，就不再新建了，直接把s2指向它。
     *
     *
     *
     *     */
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        //Objects.equals  源码 比较内存 或者 对象 (是否重写equals) （String 已经重写）的equals
        //User 重写了equals方法  内存地址不同  值是相同的
        User u = new User();
        User u1 = new User();
        System.out.println(u.equals(u1));
        System.out.println(Objects.equals(u, u1));

        String s ="aa";
        String s1 = new String("aa");
        System.out.println(s.equals(s1));
        System.out.println(Objects.equals(s, s1));

        testUser();
        testInteger();
        testString();
        testObject();
    }


    /**
     *      String中的equals方法其实比较的是字符串的内容是否一样。
     *      也就是说如果像String、Date这些重写equals的类
     *      ，你可要小心了。使用的时候会和Object的不一样。
     */
    public static void testString(){
        String str1 = "Hello";
        String str2 = new String("Hello");
        String str3 = str2; //引用传递
        System.out.println(str1 == str2); //false
        System.out.println(str1 == str3); //false
        System.out.println(str2 == str3); // true
        System.out.println(str1.equals(str2)); //true
        System.out.println(str1.equals(str3)); //true
        System.out.println(str2.equals(str3)); //true



        System.out.println(Objects.equals(str1, str2)); //true
        System.out.println(Objects.equals(str1, str3)); //true
        System.out.println(Objects.equals(str2, str3)); //true
    }

    public static void testObject(){
        TestDemo testDemo = new TestDemo();
        TestDemo testDemo1 = new TestDemo();
        System.out.println(testDemo.equals(testDemo1)); //false

    }


    public static void testInteger() {
        //
        /**
         * Integer.valueOf()方法的源代码，我们可以清楚地看到，
         * 如果传递的int literal i大于IntegerCache.low且小于IntegerCache.high该方法，
         * 则返回Integer对象IntegerCache。默认值IntegerCache.low和IntegerCache.high是-128和127分别。
         *
         * 如果传递的int literal大于和小于，则Integer.valueOf()方法不是创建和返回新的整数对象，
         * 而是从内部返回Integer对象。IntegerCache``-128``127
         *
         * Java缓存整数对象，这些对象属于-128到127范围，
         * 因为这个整数范围在日常编程中被大量使用，间接节省了一些内存。
         *
         *
         *     public static Integer valueOf(int i) {
         *         if (i >= IntegerCache.low && i <= IntegerCache.high)
         *             return IntegerCache.cache[i + (-IntegerCache.low)];
         *         return new Integer(i);
         *     }
         *
         *     类似于Integer.IntegerCache
         *     我们也有ByteCache，ShortCache，LongCache，CharacterCache
         *     为Byte，Short，Long，Character分别。
         *
         */
        //自动装箱  -128 -127 缓存里取  内存地址一样  否则 new Integer() 所以 不在范围内 == 就会有问题
        // Compiler converts this line to Integer a = Integer.valueOf(127);
        Integer count = 127; //Integer integer = Integer.valueOf(127);
        // Compiler converts this line to Integer b = Integer.valueOf(127);
        Integer count1 = 127;
        // Output -- true
        //用 equals 解决 因为 Integer equals 源码 是自动拆箱 成 int ==比较  比较的是值
//        public boolean equals(Object obj) {
//            if (obj instanceof Integer) {
//                return value == ((Integer)obj).intValue();
//            }
//            return false;
//        }
        System.out.println(count.equals(count1));
    }

    public static void testUser () {
        User u1 = new User();
        u1.setId(130);
        User u2 = new User();
        u2.setId(130);
        //user 重写了 equals方法  比较的是对象的值是否相等
        if (u1.equals(u2)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        boolean equals = Objects.equals(u1, u2);
        boolean b2 = Objects.equals("222", "333");
    }



    @Test
    public void test10(){
        List<String> list = Lists.newArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        String str = String.join(",", list);
        System.out.println(str);
    }
}
