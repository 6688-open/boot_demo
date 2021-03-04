package com.dj.boot.juc.thread;



import java.util.concurrent.*;
import java.util.stream.LongStream;

/**
 * https://baijiahao.baidu.com/s?id=1649619201425234676&wfr=spider&for=pc
 *
 * https://www.cnblogs.com/coloz/p/11167883.html
 *
 * 在古代，皇帝要想办成一件事肯定不会自己亲自去动手，而是把任务细分发给下面的大臣，下面的大臣也懒呀，
 * 于是把任务继续分成几个部分，继续下发，于是到了最后最终负责的人就完成了一个小功能。
 * 上面的领导再把这些结果一层一层汇总，最终返回给皇帝。这就是分而治之的思想，也是我们今天的主题ForkJoin。
 *
 * 从JDK1.7开始，Java提供ForkJoin框架用于并行执行任务，它的思想就是讲一个大任务分割成若干小任务，
 * 最终汇总每个小任务的结果得到这个大任务的结果。
 *
 *
 * 1、ForkJoinPool
 *
 * 既然任务是被逐渐的细化的，那就需要把这些任务存在一个池子里面，这个池子就是ForkJoinPool，
 * 它与其它的ExecutorService区别主要在于它使用“工作窃取“，那什么是工作窃取呢？
 *
 * 一个大任务会被划分成无数个小任务，这些任务被分配到不同的队列，这些队列有些干活干的块，有些干得慢。于是干得快的，
 * 一看自己没任务需要执行了，就去隔壁的队列里面拿去任务执行。
 *
 * 2、ForkJoinTask
 *
 * ForkJoinTask就是ForkJoinPool里面的每一个任务。
 * 他主要有两个子类：RecursiveAction和RecursiveTask。
 * 然后通过fork()方法去分配任务执行任务，通过join()方法汇总任务结果，
 *
 * 这就是整个过程的运用。他有两个子类，使用这两个子类都可以实现我们的任务分配和计算。
 *
 * （1）RecursiveAction 一个递归无结果的ForkJoinTask（没有返回值）
 *
 * （2）RecursiveTask 一个递归有结果的ForkJoinTask（有返回值）
 *
 * ForkJoinPool由  ForkJoinTask数组和  ForkJoinWorkerThread数组组成，
 * ForkJoinTask数组负责存放程序提交给ForkJoinPool的任务，而ForkJoinWorkerThread数组负责执行这些任务。
 *
 *
 *  abstract class ForkJoinTask<V> implements Future<V>,    ---callable
 *  public abstract class RecursiveTask<V> extends ForkJoinTask<V>
 *
 *      接口 要实现  抽象类要继承
 *
 *   ForkJoinPool
 *   ForkJoinTask;
 *   RecursiveTask   递归
 *
 */
public class ForkJoinPoolDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyTask myTask = new MyTask(0, 100);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //ForkJoinTask需要通过ForkJoinPool来执行
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        System.out.println(forkJoinTask.get());

        forkJoinPool.shutdown();

    }


    private void test () {
        //Java 8 并行流的实现
        long l = System.currentTimeMillis();
        long reduce = LongStream.rangeClosed(0, 10000000000L).parallel().reduce(0, Long::sum);
        //long reduce = LongStream.rangeClosed(0, 10000000000L).parallel().reduce(0, (a, b) -> a+b);
        long l1 = System.currentTimeMillis();
        System.out.println("invoke = " + reduce + "  time: " + (l1 - l));
    }


}

class MyTask extends RecursiveTask<Integer> {

    private static final Integer ADJUST_VALUE = 10;

    private int begin;

    private int end;

    private int result;

    public MyTask() {
    }

    public MyTask(Integer begin, Integer end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        //如果任务小的不能在细分了  就直接计算
        if (end - begin <= ADJUST_VALUE) {
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        } else {
            //取中间值 在这个方法中我们传进去数据，然后使用二分法继续分配给子任务，当任务小的不能再分，那就汇总返回。
            int middle = (begin + end) / 2;
            System.out.println("ForkJoin\t" + middle);
            MyTask myTask1 = new MyTask(begin, middle);
            MyTask myTask2 = new MyTask(middle + 1, end);
            myTask1.fork();
            myTask2.fork();
            result = myTask1.join() + myTask2.join();
        }

        return result;
    }





    // 源码
    /*public ForkJoinPool(int parallelism,
                        ForkJoinWorkerThreadFactory factory,
                        UncaughtExceptionHandler handler,
                        boolean asyncMode) {
        this(checkParallelism(parallelism),
                checkFactory(factory),
                handler,
                asyncMode ? FIFO_QUEUE : LIFO_QUEUE,
                "ForkJoinPool-" + nextPoolId() + "-worker-");
        checkPermission();
    }*/

    /*parallelism： 可并行级别，
        默认值 0x7FFF  a=32767 0x代表16进制 7FFF就是一个16进制的数，转化成10进制就是32767
        Math.min(MAX_CAP, Runtime.getRuntime().availableProcessors()
        对于最大并行任务数量也只是一个默认值——当前操作系统可以使用的CPU内核数量

      Fork/Join框架将依据这个并行级别的设定，决定框架内并行执行的线程数量。
      并行的每一个任务都会有一个线程进行处理，但是千万不要将这个属性理解成Fork/Join框架中最多存在的线程数量，
      也不要将这个属性和ThreadPoolExecutor线程池中的corePoolSize、maximumPoolSize属性进行比较，
      因为ForkJoinPool的组织结构和工作方式与后者完全不一样。而后续的讨论中，
      还可以发现Fork/Join框架中可存在的线程数量和这个参数值的关系并不是绝对的关联（有依据但并不全由它决定）。

    factory：
        当Fork/Join框架创建一个新的线程时，同样会用到线程创建工厂。
        只不过这个线程工厂不再需要实现ThreadFactory接口，而是需要实现ForkJoinWorkerThreadFactory接口。
        后者是一个函数式接口，只需要实现一个名叫newThread的方法。
        在Fork/Join框架中有一个默认的ForkJoinWorkerThreadFactory接口实现：DefaultForkJoinWorkerThreadFactory。

    handler：异常捕获处理器。当执行的任务中出现异常，并从任务中被抛出时，就会被handler捕获。

    asyncMode：
        这个参数也非常重要，从字面意思来看是指的异步模式，它并不是说Fork/Join框架是采用同步模式还是采用异步模式工作。
        Fork/Join框架中为每一个独立工作的线程准备了对应的待执行任务队列，这个任务队列是使用数组进行组合的双向队列。
        即是说存在于队列中的待执行任务，即可以使用先进先出的工作模式，也可以使用后进先出的工作模式。
        当asyncMode设置为ture的时候，队列采用先进先出方式工作；
        反之则是采用后进先出的方式工作，该值默认为false.(WorkQueue)*/


    /*6.fork方法

    fork方法用于将新创建的子任务放入当前线程的work queue队列中，
    Fork/Join框架将根据当前正在并发执行ForkJoinTask任务的ForkJoinWorkerThread线程状态，
    决定是让这个任务在队列中等待，还是创建一个新的ForkJoinWorkerThread线程运行它，
    又或者是唤起其它正在等待任务的ForkJoinWorkerThread线程运行它。

    fork方法,将当前任务入池 ;
    当我们调用ForkJoinTask的fork方法时，程序会把任务放在ForkJoinWorkerThread的pushTask的workQueue中，
    异步地执行这个任务，然后立即返回结果。

    代码如下:
    public final ForkJoinTask<V> fork() {
        Thread t;
　　　　//如果当前线程是ForkJoinWorkerThread,将任务压入该线程的任务队列
        if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread)
            ((ForkJoinWorkerThread)t).workQueue.push(this);
        else
　　　　　　//否则调用common池的externalPush方法入队
        ForkJoinPool.common.externalPush(this);
        return this;
    }

    pushTask方法把当前任务存放在ForkJoinTask数组队列里。
    然后再调用ForkJoinPool的signalWork()方法唤醒或创建一个工作线程来执行任务。代码如下：

    final void push(ForkJoinTask<?> task) {
        ForkJoinTask<?>[] a; ForkJoinPool p;
        int b = base, s = top, n;
        if ((a = array) != null) {    // ignore if queue removed
            int m = a.length - 1;     // fenced write for task visibility
            U.putOrderedObject(a, ((m & s) << ASHIFT) + ABASE, task);
            U.putOrderedInt(this, QTOP, s + 1);
            if ((n = s - b) <= 1) {
                if ((p = pool) != null)
                    p.signalWork(p.workQueues, this);
            }
            else if (n >= m)
                growArray();
        }
    }


7.join方法

    join方法用于让当前线程阻塞，直到对应的子任务完成运行并返回执行结果。
    或者，如果这个子任务存在于当前线程的任务等待队列（work queue）中，则取出这个子任务进行“递归”执行。

    其目的是尽快得到当前子任务的运行结果，然后继续执行。也就是让子任务先执行的意思。

    public final V join() {
        int s;
　　　　　 //调用doJoin方法阻塞等待的结果不是NORMAL,说明有异常或取消.报告异常
        if ((s = doJoin() & DONE_MASK) != NORMAL)
            reportException(s);
　　　　//等于NORMAL,正常执行完毕,返回原始结果
        return getRawResult();
    }

    它首先调用doJoin方法，通过doJoin()方法得到当前任务的状态来判断返回什么结果，
    任务状态有4种：已完成（NORMAL）、被取消（CANCELLED）、信号（SIGNAL）和出现异常（EXCEPTIONAL）。

            　　如果任务状态是已完成，则直接返回任务结果。

            　　如果任务状态是被取消，则直接抛出CancellationException

　　            如果任务状态是抛出异常，则直接抛出对应的异常

 　　           如果没有返回状态,会否则使用当线程池所在的ForkJoinPool的awaitJoin方法等待.

　　让我们分析一下doJoin方法的实现


    private int doJoin() {
        int s; Thread t; ForkJoinWorkerThread wt; ForkJoinPool.WorkQueue w;
　　　　　　//已完成,返回status,未完成再尝试后续
        return (s = status) < 0 ? s :
　　　　　　//未完成,当前线程是ForkJoinWorkerThread,从该线程中取出workQueue,并尝试将当前task出队然后执行,
         //执行的结果是完成则返回状态,否则使用当线程池所在的ForkJoinPool的awaitJoin方法等待
        ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
                (w = (wt = (ForkJoinWorkerThread)t).workQueue).
                        tryUnpush(this) && (s = doExec()) < 0 ? s :
                        wt.pool.awaitJoin(w, this, 0L) :
　　　　　　　　//当前线程不是ForkJoinWorkerThread,调用externalAwaitDone方法.
        externalAwaitDone();
    }

    final int doExec() {
        int s; boolean completed;
        if ((s = status) >= 0) {
            try {
                completed = exec();
            } catch (Throwable rex) {
                return setExceptionalCompletion(rex);
            }
            if (completed)
                s = setCompletion(NORMAL);
        }
        return s;
    }

    在doJoin()方法里，首先通过查看任务的状态，看任务是否已经执行完成，
    如果执行完成，则直接返回任务状态；如果没有执行完，则从任务数组里取出任务并执行。

    如果任务顺利执行完成，则设置任务状态为NORMAL，
    如果出现异常，则记录异常，并将任务状态设置为EXCEPTIONAL。



            8.invoke方法


    public final V invoke() {
        int s;
　　　　　　//先尝试执行
        if ((s = doInvoke() & DONE_MASK) != NORMAL)
　　　　　　//doInvoke方法的结果status只保留完成态位表示非NORMAL,则报告异常
        reportException(s);
　　　　 //正常完成,返回原始结果.
        return getRawResult();
    }

    //ForkJoinPool::awaitJoin,在该方法中使用循环的方式进行internalWait,满足了每次按截止时间或周期进行等待,同时也顺便解决了虚假唤醒
    private int doInvoke() {
        int s; Thread t; ForkJoinWorkerThread wt;
        return (s = doExec()) < 0 ? s :
                ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
                        (wt = (ForkJoinWorkerThread)t).pool.
                                awaitJoin(wt.workQueue, this, 0L) :
                        externalAwaitDone();
    }

    externalAwaitDone函数.它体现了ForkJoin框架的一个核心:外部帮助,

    externalAwaitDone的逻辑不复杂,在当前task为ForkJoinPool.common的情况下可以在外部进行等待和尝试帮助完成.

           方法会首先根据ForkJoinTask的类型进行尝试帮助,并返回当前的status,若发现未完成,则进入下面的等待唤醒逻辑.
           该方法的调用者为非worker线程.

    //外部线程等待一个common池中的任务完成.
    private int externalAwaitDone() {
        int s = ((this instanceof CountedCompleter) ?
                //当前task是一个CountedCompleter,尝试使用common ForkJoinPool去外部帮助完成,并将完成状态返回.
                ForkJoinPool.common.externalHelpComplete(
                        (CountedCompleter<?>)this, 0) :
                //当前task不是CountedCompleter,则调用common pool尝试外部弹出该任务并进行执行,
                //status赋值doExec函数的结果,若弹出失败(其他线程先行弹出)赋0.
                ForkJoinPool.common.tryExternalUnpush(this) ? doExec() : 0);
        if (s >= 0 && (s = status) >= 0) {
            //检查上一步的结果,即外部使用common池弹出并执行的结果(不是CountedCompleter的情况),
            //或外部尝试帮助CountedCompleter完成的结果
            //status大于0表示尝试帮助完成失败.
            //扰动标识,初值false
            boolean interrupted = false;
            do {
                //循环尝试,先给status标记SIGNAL标识,便于后续唤醒操作.
                if (U.compareAndSwapInt(this, STATUS, s, s | SIGNAL)) {
                    synchronized (this) {
                        if (status >= 0) {
                            try {
                                //CAS成功,进同步块发现double check未完成,则等待.
                                wait(0L);
                            } catch (InterruptedException ie) {
                                //若在等待过程中发生了扰动,不停止等待,标记扰动.
                                interrupted = true;
                            }
                        }
                        else
                            //进同步块发现已完成,则唤醒所有等待线程.
                            notifyAll();
                    }
                }
            } while ((s = status) >= 0);//循环条件,task未完成.
            if (interrupted)
                //循环结束,若循环中间曾有扰动,则中断当前线程.
                Thread.currentThread().interrupt();
        }
        //返回status
        return s;
    }
    */



}
