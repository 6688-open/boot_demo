package com.dj.boot.juc.threadLocal;



import java.util.Random;

/**
 * ThreadLocal的确是数据的隔离，在每一个线程中创建一个新的数据对象，然后每一个线程使用的是不一样的。
 *
 * ThreadLocal是JDK包提供的，它提供线程本地变量，
 * 如果创建一乐ThreadLocal变量，那么访问这个变量的每个线程都会有这个变量的一个副本，
 * 在实际多线程操作的时候，操作的是自己本地内存中的变量，从而规避了线程安全问题，如下图所示
 */
public class ThreadLocalDemo {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalDemo p = new ThreadLocalDemo();
        //这样做其实就是在操作同一个对象，如果需要实现多线程应该像下下面的注释一样，这
        // 样就针对于每一个线程创建一个独立的Person对象
        final ThreadLocal<ThreadLocalDemo> t = new ThreadLocal<ThreadLocalDemo>(){
            public ThreadLocalDemo initialValue() {
                //return new Person();
                //返回此线程局部变量的当前线程的“初始值”。
                return p;
            }
        };

        p.setUserName("小明");
        for(int i=0;i<3;i++)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //将当前线程的此线程局部变量的副本设置为指定的值。
                    t.set(p);
                    //返回当前线程的此线程局部变量的副本中的值。
                    t.get().setUserName(new Random().nextInt(100)+"");
                    System.out.println(t.get().getUserName()+"=="+t.get());
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(p.getUserName());
    }
}

/**
 * 每个线程的本地变量不是存放在ThreadLocal实例中，
 * 而是放在调用线程的ThreadLocals变量里面（前面也说过，该变量是Thread类的变量）。
 * 也就是说，ThreadLocal类型的本地变量是存放在 具体的线程空间上 (副本)，
 * 通过set方法将value添加到调用线程的threadLocals中，当调用线程调用get方法时候能够从它的threadLocals中取出变量
 * 如果调用线程一直不终止，那么这个本地变量将会一直存放在他的threadLocals中，
 * 所以不使用本地变量的时候需要调用remove方法将threadLocals中删除不用的本地变量。
 */
        /*1、set方法源码

         public void set(T value) {
             //(1)获取当前线程（调用者线程）
             Thread t = Thread.currentThread();
             //(2)以当前线程作为key值，去查找对应的线程变量，找到对应的map
             ThreadLocalMap map = getMap(t);
             //(3)如果map不为null，就直接添加本地变量，key为当前线程，值为添加的本地变量值
             if (map != null)
                 map.set(this, value);
             //(4)如果map为null，说明首次添加，需要首先创建出对应的map
             else
                 createMap(t, value);
         }*/

          /*
            ThreadLocalMap getMap(Thread t) {
            //获取线程自己的变量threadLocals，并绑定到当前调用线程的成员变量threadLocals上
                return t.threadLocals;
            }*/



/*get方法源码

  public T get() {
         //(1)获取当前线程
         Thread t = Thread.currentThread();
         //(2)获取当前线程的threadLocals变量
         ThreadLocalMap map = getMap(t);
         //(3)如果threadLocals变量不为null，就可以在map中查找到本地变量的值
         if (map != null) {
                 ThreadLocalMap.Entry e = map.getEntry(this);
                 if (e != null) {
                         @SuppressWarnings("unchecked")
                         T result = (T)e.value;
                         return result;
                     }
             }
         //(4)执行到此处，threadLocals为null，调用该更改初始化当前线程的threadLocals变量
         return setInitialValue();
     }

     private T setInitialValue() {
     //protected T initialValue() {return null;}
     T value = initialValue();
     //获取当前线程
     Thread t = Thread.currentThread();
    //以当前线程作为key值，去查找对应的线程变量，找到对应的map
     ThreadLocalMap map = getMap(t);
     //如果map不为null，就直接添加本地变量，key为当前线程，值为添加的本地变量值
     if (map != null)
         map.set(this, value);
    //如果map为null，说明首次添加，需要首先创建出对应的map
    else
         createMap(t, value);
     return value;
 }
*/



/*
　　3、remove方法的实现

        　　remove方法判断该当前线程对应的threadLocals变量是否为null，
            不为null就直接删除当前线程中指定的threadLocals变量

          public void remove() {
             //获取当前线程绑定的threadLocals
              ThreadLocalMap m = getMap(Thread.currentThread());
              //如果map不为null，就移除当前线程中指定ThreadLocal实例的本地变量
              if (m != null)
                  m.remove(this);
          }*/
