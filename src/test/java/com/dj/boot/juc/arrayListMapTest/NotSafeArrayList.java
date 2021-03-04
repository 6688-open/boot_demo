package com.dj.boot.juc.arrayListMapTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 线程不安全     ctrl + f12
 * 1 故障现象
 *     java.util.ConcurrentModificationException   并发修改异常
 *
 * 2 导致原因
 *
 *     多个线程对同一个资源类 同时读写
 *
 * 3解决方法
 *     1 new Vector<>();
 *     2 Collections.synchronizedList(new ArrayList<>())  集合的工具类
 *     3 new CopyOnWriteArrayList<>();    并发包下的
 *
 * 4 优化建议
 *
 *
 *
 *
 * 其核心思想是 如果有多个调用者（Callers）同时要求相同的资源（如内存或者是磁盘上的数据存储），
 * 他们会共同获取相同的指针指向相同的资源，直到某个调用者视图修改资源内容时，
 * 系统才会真正复制一份专用副本（private copy）给该调用者  扩容 + 1 ，而其他调用者所见到的最初的资源仍然保持不变。
 * 这过程对其他的调用者都是透明的（transparently）。此做法主要的优点是如果调用者没有修改资源，
 * 就不会有副本（private copy）被创建，因此多个调用者只是读取操作时可以共享同一份资源。
 *
 *
 *  CopyOnWrite的缺点　
 *        1 　内存占用问题。
 *                   因为CopyOnWrite的写时复制机制，所以在进行写操作的时候，内存里会同时驻扎两个对象的内存，旧的对象和新写入的对象
 *                  （注意:在复制的时候只是复制容器里的引用，只是在写的时候会创建新对象添加到新容器里，而旧容器的对象还在使用，所以有两份对象内存）
 *                    很有可能造成频繁的Yong GC和Full GC
 *
 *        2   数据一致性问题。
 *                 CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。
 *                 所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器。
 *
 *
 *
 *    CopyOnWriteArrayList为什么并发安全且性能比Vector好
 *
     * 　 我知道Vector是增删改查方法都加了synchronized，保证同步，但是每个方法执行的时候都要去获得锁，性能就会大大下降，
     *    而CopyOnWriteArrayList 只是在增删改上加锁，但是读不加锁，
     *    在读方面的性能就好于Vector，CopyOnWriteArrayList支持读多写少的并发情况。
 *
 *
 *      读写分离的思想
 *
 *
 *    public E set(int index, E element) {
 *     final ReentrantLock lock = this.lock;
 *     //获得锁
 *     lock.lock();
 *     try {
 *         Object[] elements = getArray();
 *         E oldValue = get(elements, index);
 *
 *         if (oldValue != element) {
 *             int len = elements.length;
 *             //创建新数组  扩容 + 1
 *             Object[] newElements = Arrays.copyOf(elements, len);
 *             //替换元素
 *             newElements[index] = element;
 *             //将新数组指向原来的引用
 *             setArray(newElements);
 *         } else {
 *             // Not quite a no-op; ensures volatile write semantics
 *             setArray(elements);
 *         }
 *         return oldValue;
 *     } finally {
 *         //释放锁
 *         lock.unlock();
 *     }
 * }
 */
public class NotSafeArrayList {

    public static void main(String[] args) {


        /**
         * 有序有重复
         */
        List<String> list = new ArrayList<>();  //创建了一个object数组
        //List<String> list = new Vector<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        //List<String> list = new CopyOnWriteArrayList<>();


        /**   无序 无重复
         *    HashSet 的底层是   HashMap      new HashMap<>()
         *
         *   HashSet 的add 方法就是  HashMap 的put方法
         *   key 就是 HashSet传过来的值   value就是一个 Object 常量
         *
         *   map.put(o, PRESENT)==null
         *
         * 实际执行的是 map 的方法，并且我们添加的对象是 map 中的 key,value 是执行的同一个对象PRESENT.
         *
         * 因为map中的key是不允许重复的，所以set中的元素不能重复。
         *
         *
         *  public HashSet() {
         *         map = new HashMap<>();
         *     }
         *
         *       public boolean add(E e) {
         *         return map.put(e, PRESENT)==null;
         *     }
         *
         *      // Dummy value to associate with an Object in the backing Map
         *         private static final Object PRESENT = new Object();
         *
         *
         *
         */
        Set<String> set = new HashSet<>();
        Set<String> set1 = new CopyOnWriteArraySet<>();


        /**
         * 无序 无重复
         * Node节点的 数组 + 链表 + 红黑树
         *  16个数组位  通过 hash + 高位运算  hash 值不同 放在不同的位置  不够则扩容
         *
         *  hash  相同  放在同一个Node 节点   Node<k, v> next  单向链表
         *
         *  每次put 判断该索引位置 是否已存在 entry Node节点
         *  重复 替换  覆盖
         *  不重复 连入尾部 形成Node节点的单向链表   hash碰撞
         *  超过 8  由链表转为红黑树
         *f
         * new HashMap<>() 相当于默认  new HashMap<>(16, 0.75) 也可以自定义
         */
        Map<Object, Object> map = new HashMap<>();
        /**
         * java.util.IdentityHashMap类利用哈希表实现 Map 接口，比较键（和值）时使用引用相等性代替对象相等性。
         * 换句话说，在 IdentityHashMap 中，当且仅当 (k1==k2) 时，才认为两个键 k1 和 k2 相等
         * 在正常 Map 实现（如 HashMap）中，当且仅当满足下列条件时才认为两个键 k1 和 k2 相等：(k1==null ? k2==null : e1.equals(e2))）。
         *
         * 也就是说，IdentityHashMap比较的单单key的值，它不管可以的内存地址还是基本数据类型。而正常的Map实现比较的是具体的内容。
         */
        IdentityHashMap<Object, String> idenMap = new IdentityHashMap<>();
        Map<String, Object> map1 = new ConcurrentHashMap<String, Object>(1000, 0.75f);


        for (int i = 1; i <=30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }


    /**
     *     HashMap为什么是线程不安全的
     *
     *     我们知道hashmap的扩容因子是0.75如果hashmap的数组长度已经使用了75%就会引起扩容，
     *     会新申请一个长度为原来两倍的桶数组，然后将原数组的元素重新映射到新的数组中，原有数据的引用会逐个被置为null。
     *     就是在resize()扩容的时候会造成线程不安全。另外当一个新节点想要插入hashmap的链表时，
     *     在jdk1.8之前的版本是插在头部，在1.8后是插在尾部
     *
     *     那么hashmap什么时候进行扩容呢？
     *     当hashmap中的元素个数超过数组大小*loadFactor时，就会进行数组扩容，loadFactor的默认值为0.75，
     *     也就是说，默认情况下，数组大小为16，那么当hashmap中元素个数超过16*0.75=12的时候，
     *     就把数组的大小扩展为2*16=32，即扩大一倍，然后重新计算每个元素在数组中的位置，而这是一个非常消耗性能的操作，
     *     所以如果我们已经预知hashmap中元素的个数，那么预设数组的大小能够有效的提高hashmap的性能。
     *     比如说，我们有1000个元素new HashMap(1000), 但是理论上来讲new HashMap(1024)更合适，
     *     不过上面annegu已经说过，即使是1000，hashmap也自动会将其设置为1024。
     *     但是new HashMap(1024)还不是更合适的，因为0.75*1024 < 1000, 也就是说为了让0.75 * size > 1000,
     *     我们必须这样new HashMap(2048)才最合适，避免了resize的问题。
     *
     *     不安全原因：
     *
     *             (1)在put的时候，因为该方法不是同步的，假如有两个线程A,B它们的put的key的hash值相同，
     *              不论是从头插入还是从尾插入，假如A获取了插入位置为x，但是还未插入，
     *              此时B也计算出待插入位置为x，则不论AB插入的先后顺序肯定有一个会丢失
     *
     *             (2)在扩容的时候，jdk1.8之前是采用头插法，当两个线程同时检测到hashmap需要扩容，
     *               在进行同时扩容的时候有可能会造成链表的循环，主要原因就是，采用头插法，新链表与旧链表的顺序是反的，
     *               在1.8后采用尾插法就不会出现这种问题，同时1.8的链表长度如果大于8就会转变成红黑树。
     */

}
