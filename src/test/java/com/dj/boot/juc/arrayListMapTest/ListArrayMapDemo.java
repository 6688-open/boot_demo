package com.dj.boot.juc.arrayListMapTest;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class ListArrayMapDemo {


    public static void main(String[] args) {
        /**
         * 数组,在内存上给出了连续的空间
         *
         *数据类型定型
         * 数组定长、大小固定，不适合动态存储，不方便动态添加
         * 只能通过数组索引去赋值，所以不存在数组插入说法
         * 不能切确的知道数组内存了多少数据，length只是读取数组长度，这个长度不受数组内容影响
         */
        int[] array=new int[10];


        /**
         * 数组在申请内存空间的时候 申请一段固定长度 连续的内存空间 使用索引可以快速定位数组中的元素，
         * 但是删除 添加的时候 元素后方的索引位置都会进行改变
         *  查找快 增删慢
         *
         *  List fruits=new ArrayList();构造一个初始容量为10的空列表。
         *
         *  add() 方法      扩容 Arrays.copyOf
         *
         *  private void grow(int minCapacity) {
         *         // overflow-conscious code
         *         int oldCapacity = elementData.length;
         *         int newCapacity = oldCapacity + (oldCapacity >> 1);
         *         if (newCapacity - minCapacity < 0)
         *             newCapacity = minCapacity;
         *         if (newCapacity - MAX_ARRAY_SIZE > 0)
         *             newCapacity = hugeCapacity(minCapacity);
         *         // minCapacity is usually close to size, so this is a win:
         *         elementData = Arrays.copyOf(elementData, newCapacity);
         *     }
         */
        List<Integer> list = new ArrayList<>();
        //默认添加到末尾
        list.add(22);
        list.add(11);
        //指定索引位置
        list.add(1, 3);

        list.remove(2);


        /**
         * 底层的数据结构是基于双向链表的，该数据结构我们称为节点
         * 允许所有元素为null
         * 它的查找是分两半查找，先判断index是在链表的哪一半，然后再去对应区域查找，这样最多只要遍历链表的一半节
         */
        List<Object> objects = new LinkedList<>();


        /**
         * HashTable和  HashMap  TreeMap
         *
         *
         * TreeMap有序，底层采用红黑树（自平衡的二叉树）,线程不安全(ensure)
         *
         * 默认初始容量—必须是2的幂。  static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
         *  说明初始化容量是16  static final int MAXIMUM_CAPACITY = 1 << 30;
         *
         *
         *  key是经过hash计算过才存入内存中，内存不可能存在相同的hash值.所以不能有重复的key
         *  hashSet 的add   方法 底层就是new HashMap<>  元素就是HashMap的key 值就一个Object 常量
         *  所以 set集合 不可重复 无序
         *
         *  put
         *  putVal(hash(key), key, value, false, true)
         *
         *
         *
         *
         *
         *
         *
         *  hash(key)    取key的hashCode算法，然后对16进行异或运算和右移运算
         *  static final int hash(Object key) {
         *         int h;
         *         return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
         *     }
         *
         *     是通过hashCode的高16位异或低16位实现的  主要从速度 功率 质量 考虑
         *     这么做 可以在bucket的n比较小的时候 也能保证高低bit 都参与到hash的计算中去
         *     同时不会有太大的开销
         *
         *    getNode(int hash, Object key)
         *     通过key hash 然后取模数组长度  判断Entry的位置
         *     HashMap中定位到桶的位置 是根据Key的hash值与数组的长度取模来计算的。
         *
         *      首先比对首节点，如果首节点的hash值和key的hash值相同 并且 首节点的键对象和key相同（地址相同或equals相等），则返回该节点
         *      如果首节点比对不相同、那么看看是否存在下一个节点，如果存在的话，可以继续比对，如果不存在就意味着key没有匹配的键值对
         *
         *      如果存在下一个节点 e，那么先看看这个首节点是否是个树节点
         *              如果是首节点是树节点，那么遍历树来查找
         *              如果首节点不是树节点，就说明还是个普通的链表，那么逐个遍历比对即可
         *
         *               如果当前节点e的键对象和key相同，那么返回e
         *
         *
         *
         */
        Map<String, Object> map1 = null;
        Map<String, Object> map = new HashMap<>(16);
        map.put("22", 22);
        map.put("22", 22);

        //赋值
        map1=map;









        List<String> stringList = new ArrayList<>();//数据集合
        List<Integer> integerList = new ArrayList<>();//存储remove的位置

        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        stringList.add("e");

        integerList.add(2);
        integerList.add(4);//此处相当于要移除最后一个数据

        //因为执行stringList.remove(2)后，list.size()就-1为4了，我们原来要移除的最后一个位置的数据移动到了第3个位置上，自然就造成了越界。
        //我们修改代码先执行stringList.remove(4)，再执行执行stringList.remove(2)。
        for (Integer i :integerList){
            //仔细观察上面代码你会发现，其实i是Integer对象，而由于Java系统中如果找不到准确的对象，会自动向上升级，而(int < Integer < Object)，所以在调用stringList.remove(i)时，其实使用的remove(object object)，而很明显stringList不存在Integer对象，自然会移除失败（0.0），Java也不会因此报错。
            int a = i;
            stringList.remove(a);
        }

        for (String s :stringList){
            System.out.println(s);
        }


        /**
         * List调用remove(index)方法后，list的大小发生了变化，而你的索引也在变化，
         * 会移除index位置上的元素，index之后的元素就全部依次左移
         * 即索引依次-1要保证能操作所有的数据，需要把index-1，
         * 否则原来索引为index+1的元素就无法遍历到(因为原来索引为index+1的数据，在执行移除操作后，
         * 索引变成index了，如果没有index-1的操作，就不会遍历到该元素，而是遍历该元素的下一个元素)
         * 方式可以用在删除特定的一个元素时使用，但不适合循环删除多个元素时使用。
         *
         *
         * 第一次执行完remove方法后，并不像我们简单想象的那样就把第一个删除了，
         * “第一个A”这个对象被删除了没错，但是当被删除后List中“2-5个A”以后的4个对象的index索引也变了，
         * 都比原来的值减一，换句话说就是剩下的4个对象的index值为从0到3，
         * 而不是原来的从1到4了，那么第二次执行remove方法时，
         * 此时list.remove(1) 删除的index 为 1 但是index 为 0 也是A 被忽略了

         */

        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("A");
        list1.add("A");
        list1.add("A");
        list1.add("A");

        for(int i = 0;i<list1.size();i++){
            if("A".equals(list1.get(i))){
                list1.remove(i);
            }
        }


        System.out.println("--查看结果--");
        for(int i = 0;i<list1.size();i++){
            System.out.println(list1.get(i));
        }


        //iterator迭代器删除元素（正确版）
        list1.removeIf(x -> !"A".equals(x));

        //iterator迭代器删除元素（正确版）
        Iterator<String> it = list1.iterator();
        while(it.hasNext()){
            String x = it.next();
            if(!"A".equals(x)){
                it.remove();
            }
        }

        System.out.println("--查看结果--");
        for(int i = 0;i<list1.size();i++){
            System.out.println(list1.get(i));
        }


        //过滤掉元素
        List<String> list2 = list1.stream().filter(e -> !"b".equals(e)).collect(Collectors.toList());
        System.out.println("method4|list2=" + list2);


        //方法三：倒序遍历删除元素（正确版）
        for (int i = list1.size() - 1; i >= 0; i--) {
            if ("sss".equals(list1.get(i))) {
                list1.remove(i);
            }
        }
        System.out.println("method3|list=" + list);

    }

    /**
     * 在调用者中 取两个集合的交集
     */
    @Test
    public void testList() {
        List<String> list3 = new ArrayList<>();
        list3.add("4");
        list3.add("2");
        list3.add("8");
        List<String> list4 = new ArrayList<>();
        list4.add("2");
        list4.add("abc");
        list4.add("a");
        // 在调用者中  留下交集的部分
        boolean flag = list3.retainAll(list4);
        System.out.println(flag);// 如果因为取交集删除元素了  true  没有删除元素   false
        System.out.println(list3);
    }




    /**
     * 检查是否包含key
     * 如果key有对应的节点对象，则返回ture，不关心节点对象的值是否为空
     */
/*    public boolean containsKey(Object key) {
        // 调用getNode方法来获取键值对，如果没有找到返回false，找到了就返回ture
        return getNode(hash(key), key) != null; //真正的查找过程都是通过getNode方法实现的
    }




     public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    *//**
     * 该方法是Map.get方法的具体实现
     * 接收两个参数
     * @param hash key的hash值，根据hash值在节点数组中寻址，该hash值是通过hash(key)得到的，可参见：hash方法解析
     * @param key key对象，当存在hash碰撞时，要逐个比对是否相等
     * @return 查找到则返回键值对节点对象，否则返回null
     *//*
    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        // 声明节点数组对象、链表的第一个节点对象、循环遍历时的当前节点对象、数组长度、节点的键对象
        // 节点数组赋值、数组长度赋值、通过位运算得到求模结果确定链表的首节点
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // 首先比对首节点，如果首节点的hash值和key的hash值相同 并且 首节点的键对象和key相同（地址相同或equals相等），则返回该节点
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first; // 返回首节点

            // 如果首节点比对不相同、那么看看是否存在下一个节点，如果存在的话，可以继续比对，如果不存在就意味着key没有匹配的键值对
            if ((e = first.next) != null) {
                // 如果存在下一个节点 e，那么先看看这个首节点是否是个树节点
                if (first instanceof TreeNode)
                    // 如果是首节点是树节点，那么遍历树来查找
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);

                // 如果首节点不是树节点，就说明还是个普通的链表，那么逐个遍历比对即可
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) // 比对时还是先看hash值是否相同、再看地址或equals
                        return e; // 如果当前节点e的键对象和key相同，那么返回e
                } while ((e = e.next) != null); // 看看是否还有下一个节点，如果有，继续下一轮比对，否则跳出循环
            }
        }
        return null; // 在比对完了应该比对的树节点 或者全部的链表节点 都没能匹配到key，那么就返回null*/



    /**
     * @param hash key的hash值
     * @param key 键
     * @param value 值
     * @param onlyIfAbsent 设为true表示如果键不存在，才会写入值。
     * @param evict
     * @return 返回value
     */
  /*  final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i; // 定义元素数组、当前元素变量
        // 如果当前Map的元素数组为空 或者 数组长度为0，那么需要初始化元素数组
        // tab = resize() 初始化了元素数组，resize方法同时也可以实现数组扩容，可参见：resize方法解析
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length; // n 设置为 数组长度

        // 根据hash值和数组长度取摸计算出数组下标
        if ((p = tab[i = (n - 1) & hash]) == null)  // 如果该位置不存在元素，那么创建一个新元素存储到数组的该位置。
            tab[i] = newNode(hash, key, value, null); // 此处单独解析
        else { // 如果该位置已经存在元素，说明有以下情况
            Node<K,V> e; K k; // e 用来指向根据key匹配到的元素
            // 如果要写入的key的hash值和当前元素的key的hash值相同，并且key也相等   覆盖
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p; // 用e指向到当前元素

            // 如果要写入的key的hash值和当前元素的key的hash值不同，或者key不相等，说明不是同一个key，要通过其他数据结构来存储新来的数据
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value); // 参见：putTreeVal方法解析
            else { // 运行到这里，说明采用链表结构来存储
                for (int binCount = 0; ; ++binCount) {  // 要逐一对比看要写入的key是否和链表上的某个key相同
                    if ((e = p.next) == null) { // 如果当前元素没有下一个节点
                        // 根据键值对创建一个新节点，挂到链表的尾部
                        p.next = newNode(hash, key, value, null);
                        //  如果链表上元素的个数已经达到了阀值（可以改变存储结构的临界值），
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            // 将该链表上所有元素改为TreeNode方式存储（是为了增加查询性能，元素越多，链表的查询性能越差） 或者 扩容
                        treeifyBin(tab, hash); // 参见：treeifyBin方法解析
                        break;// 跳出循环，因为没有可遍历的元素了
                    }
                    // 如果下一个节点的 hash值和key值都和要写入的hash 和 key相同
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;	// 跳出循环，因为找到了相同的key对应的元素
                    p = e;
                }
            }

            put在放入数据时，如果放入数据的key已经存在与Map中，最后放入的数据会覆盖之前存在的数据，
            而putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值。

            if (e != null) { // 说明找了和要写入的key对应的元素，根据情况来决定是否覆盖值
                V oldValue = e.value; // 旧值
                if (!onlyIfAbsent || oldValue == null)	// 如果旧值为空  后者  指定了需要覆盖旧值，那么更改元素的值为新值
                    e.value = value;
                afterNodeAccess(e); // 元素被访问之后的后置处理， LinkedHashMap中有具体实现
                return oldValue; // 返回旧值
            }
        }

        // 执行到这里，说明是增加了新的元素，而不是替换了老的元素，所以相关计数需要累加

        ++modCount; // 修改计数器递增
        // 当前map的元素个数递增
        if (++size > threshold) // 如果当前map的元素个数大于了扩容阀值，那么需要扩容元素数组了
            resize(); // 元素数组扩容
        afterNodeInsertion(evict); // 添加新元素之后的后后置处理， LinkedHashMap中有具体实现
        return null; // 返回空
    }
    resize方法解析
        if ((tab = table) == null || (n = tab.length) == 0)
    n = (tab = resize()).length; // n 设置为 数组长度*/

}


/** 二叉树
 * 排序二叉树  退化成链表      排序二叉树检索效率类似于二分查找，二分查找的时间复杂度为 O(log n)
 * 平衡二叉树
 *
 *  红黑树在 Java 中的实现   Java 中的红黑树实现类是 TreeMap
 *             TreeMap的插入节点和普通的排序二叉树没啥区别，唯一不同的是，
 *             在TreeMap 插入节点后会调用方法fixAfterInsertion(e)来重新调整红黑树的结构来让红黑树保持平衡。
 *             我们重点关注下红黑树的fixAfterInsertion(e)方法，接下来我们来分别介绍两种场景来演示fixAfterInsertion(e)方法的执行流程。
 *             第一种场景：只需变色即可平衡
 *  *          第二种场景 ：转搭配变色来保持平衡
 *
 * 红黑树
 *      本质
 *         解决排序二叉树在极端情况下退化成链表导致检索效率大大降低的问题
 *
 *
 *                        若它的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
 *                        若她的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
 *
 *      红黑树的特性 ：
 *             1 每个节点要么是红色，要么是黑色。
 *             2 根节点永远是黑色的。
 *             3 所有的叶子节点都是空节点（即null），并且是黑色的
 *             4 每个红色节点的两个子节点都是黑色。（从每个叶子到根的路径上不会有两个连续的红色节点。）
 *             5 从任一节点到其子树中每个叶子节点的路径都包含相同数量的黑色节点。
 *
 *             红黑树在最差情况下，最长的路径都不会比最短的路径长出两倍
 *
 *     节点的黑高
 *              从任一节点到它的子树的每个叶子节点黑色节点的数量都是相同的，这个数量被称为这个节点的黑高
 *
 *     树的黑色高度
 *             从根节点出发到每个叶子节点的路径都包含相同数量的黑色节点，这个黑色节点的数量被称为树的黑色高度
 *
 *    只要保证以上五个特性就能保证树的平衡
 *
 *
 *      1 红黑树的插入
 *             1.1 红黑树和排序二叉树不同的是，红黑树需要在插入节点调整树的结构来让树保持平衡。
 *
 *             1.2 红黑树中新插入的节点都是红色的 :
 *                       当前红黑树中从根节点到每个叶子节点的黑色节点数量是一样的，
 *                       此时假如新的黑色节点的话，必然破坏规则
 *
 *            1.3 调整树的结构来让树保持平衡 ：
 *
 *                  1.3.1 通过变色就可以保持树的平衡 （当变色行不通的时候，我们需要考虑另一个手段就是旋转）
 *                  1.3.2 红黑树的旋转
 *                       左旋 ： 右子节取代本节点
 *                             逆时针旋转两个节点，某一个节点被其右子节点取代，该节点成为右子节点的左子节点。
 *
 *                       右旋   左子节取代本节点
 *                             顺时针旋转两个节点，某一个节点被其左子节点取代，该节点成为左子节点的右子节点。
 *
 *      \
 *
 *
 *     2  红黑树的删除
 *               2.1 删除节点是根节点
 *                       直接删除根节点即可。
 *
 *               2.2  删掉节点的左子节点和右子节点都是为空
 *                         直接删除当前节点即可。
 *
 *               2.3 删除节点有一个子节点不为空
 *                         删除的是红色
 *                         删除的是黑色
 *                   需要重新调整结构  变色加旋转
 *
 *               2.4 删除节点两个子节点都不为空
 *                         第一种，前驱节点为黑色节点，同时有一个非空节点
 *                         第二种，前驱节点为黑色节点，同时子节点都为空
 *                         第三种，前驱节点为红色节点，同时子节点都为空
 *
 *
 *
 *
 *
 *
 *
 */
