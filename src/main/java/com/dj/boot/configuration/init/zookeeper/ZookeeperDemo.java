package com.dj.boot.configuration.init.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZookeeperDemo {

    //链接 不要空格   访问的端口号
    //ZK 集群 连接超时时间
    private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private int sessionTimeOut = 2000;
    private ZooKeeper zooKClient;

    @Before
    public void init () throws IOException {
        zooKClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                //监听事件
                //获取哪个目录下节点的变化  并是否监听节点的变化
                try {
                    List<String> list = zooKClient.getChildren("/", false);
                    list.forEach(a -> System.out.println());

                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 创建节点
     */
    @Test
    public void createNode () throws KeeperException, InterruptedException {


        /** 1 创建路径
         * 2 向路径上写的数据
         * 3 访问安全权限控制
         * 4 哪一类节点 两大类
         */
        String path = zooKClient.create("/path", "date".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);

    }


    /**
     * 获取子节点 并监控节点的变化
     */
    @Test
    public void getDataAndWatch () throws KeeperException, InterruptedException {
        //获取哪个目录下节点的变化  并是否监听
        List<String> list = zooKClient.getChildren("/", false);
        list.forEach(a -> System.out.println());

        Thread.sleep(Long.MAX_VALUE);

    }


    /**
     * 判断节点是否存在
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void exist () throws KeeperException, InterruptedException {

        Stat exists = zooKClient.exists("/path2", false);
        System.out.println(exists);


    }

}
