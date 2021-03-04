package com.dj.boot.configuration.init.zookeeper;

import org.apache.zookeeper.*;

import java.util.List;

public class DisServer {
    public static void main(String[] args) throws Exception {
        DisServer server = new DisServer();
        //1  链接zookeeper集群
        server.getConnect();

        //注册节点
        server.register(args[0]);
        //业务逻辑
        server.business();

    }



    //ZK 集群 连接超时时间
    private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private int sessionTimeOut = 2000;
    private ZooKeeper zooKClient;
    private void getConnect() throws Exception {

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




    private void register(String hostName) throws Exception {
        /** 1 创建路径
         * 2 向路径上写的数据
         * 3 访问安全权限控制
         * 4 哪一类节点 两大类
         */
        // 节点名称server0000000001  server0000000002  server0000000003
        String path = zooKClient.create("/servers/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostName + "is online");
    }






    private void business() throws Exception {
        Thread.sleep(Long.MAX_VALUE);
    }
}
