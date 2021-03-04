package com.dj.boot.configuration.init.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

public class DisClient {

    public static void main(String[] args) throws Exception {
        DisClient client = new DisClient ();
        //1 获取zookeeper链接
        client.getConnect();
        // 注册监听
        client.getChlidren();
        //业务逻辑处理
        client.business();
    }



    private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private int sessionTimeOut = 2000;
    private ZooKeeper zooKClient;

    private void getConnect() throws Exception {

        zooKClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getChlidren();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getChlidren() throws  Exception{
        //拿到路径下的所有节点
        List<String> nodeList = zooKClient.getChildren("/servers", true);
        //服务器主机节点名称集合
        List<String> hosts = new ArrayList<>();
        nodeList.forEach(node -> {
            // /servers/ 路径下  +  节点名称server0000000001  server0000000002  server0000000003
            try {
                //data 该节点下的数据
                byte[] data = zooKClient.getData("/servers/" + node, false, null);
                hosts.add(new String(data));
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        //打印所有主机名称到控制台
        System.out.println(hosts);
    }



    private void business() throws Exception {
        Thread.sleep(Long.MAX_VALUE);
    }

}
