package com.dj.boot.test.iotest;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.test.iotest
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-18-11-17
 */
public class SelectorTest {
    /**
     * 选择器（Selectors）
     * 选择器是提升 IO 性能的灵魂之一，它底层利用了多路复用 IO机制，让选择器可以监听多个 IO 连接，
     * 根据 IO 的状态响应到服务器端进行处理。通俗地说：选择器可以监听多个 IO 连接，
     * 而传统的 BIO 每个 IO 连接都需要有一个线程去监听和处理。
     *
     * 图中很明显的显示了在 BIO 中，每个 Socket 都需要有一个专门的线程去处理每个请求，
     * 而在 NIO 中，只需要一个 Selector 即可监听各个 Socket 请求，
     * 而且 Selector 并不是阻塞的，所以不会因为多个线程之间切换导致上下文切换带来的开销。
     *
     * 在 Java NIO 中，选择器是使用 Selector 类表示，Selector 可以接收各种 IO 连接，
     * 在 IO 状态准备就绪时，会通知该通道注册的 Selector，Selector 在下一次轮询时会发现该 IO 连接就绪，
     * 进而处理该连接。
     *
     * Selector 选择器主要用于网络 IO当中，在这里我会将传统的 BIO Socket 编程和使用 NIO 后的 Socket 编程作对比，
     * 分析 NIO 为何更受欢迎。首先先来了解 Selector 的基本结构。
     *
     * 重要方法	方法解析
     * open()	打开一个 Selector 选择器
     * int select()	阻塞地等待就绪的通道
     * int select(long timeout)	最多阻塞 timeout 毫秒，如果是 0 则一直阻塞等待，如果是 1 则代表最多阻塞 1 毫秒
     * int selectNow()	非阻塞地轮询就绪的通道
     * 在这里，你会看到 select() 和它的重载方法是会阻塞的，如果用户进程轮询时发现没有就绪的通道，操作系统有两种做法：
     *
     * 一直等待直到一个就绪的通道，再返回给用户进程
     * 立即返回一个错误状态码给用户进程，让用户进程继续运行，不会阻塞
     * 这两种方法对应了同步阻塞 IO 和 同步非阻塞 IO ，这里读者的一点小的观点，请各位大神批判阅读
     *
     * Java 中的 NIO 不能真正意义上称为 Non-Blocking IO，我们通过 API 的调用可以发现，
     * select() 方法还是会存在阻塞的现象，根据传入的参数不同，操作系统的行为也会有所不同，
     * 不同之处就是阻塞还是非阻塞，所以我更倾向于把 NIO 称为 New IO，因为它不仅提供了 Non-Blocking IO，
     * 而且保留原有的 Blocking IO 的功能。
     *
     * 了解了选择器之后，它的作用就是：监听多个 IO 通道，当有通道就绪时选择器会轮询发现该通道，
     * 并做相应的处理。那么 IO 状态分为很多种，我们如何去识别就绪的通道是处于哪种状态呢？
     * 在 Java 中提供了选择键（SelectionKey）。
     *
     * 选择键（SelectionKey）
     *
     * 在 Java 中提供了 4 种选择键：
     *
     * SelectionKey.OP_READ：套接字通道准备好进行读操作
     * SelectionKey.OP_WRITE：套接字通道准备好进行写操作
     * SelectionKey.OP_ACCEPT：服务器套接字通道接受其它通道
     * SelectionKey.OP_CONNECT：套接字通道准备完成连接
     * 在 SelectionKey 中包含了许多属性
     *
     * channel：该选择键绑定的通道
     * selector：轮询到该选择键的选择器
     * readyOps：当前就绪选择键的值
     * interesOps：该选择器对该通道感兴趣的所有选择键
     * 选择键的作用是：在选择器轮询到有就绪通道时，会返回这些通道的就绪选择键（SelectionKey），
     * 通过选择键可以获取到通道进行操作。
     *
     * 简单了解了选择器后，我们可以结合缓冲区、通道和选择器来完成一个简易的聊天室应用。
     *
     * 示例：简易的客户端服务器通信
     *
     * 先说明，这里的代码非常的臭和长，不推荐细看，直接看注释附近的代码即可。
     */
}
