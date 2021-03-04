package com.dj.boot.webservice.webserver;

import com.dj.boot.webservice.webserver.impl.WebServiceGoodsDeliveryServiceImpl;

import javax.xml.ws.Endpoint;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.webservice
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2021-01-14-14-05
 */
public class WebServicePublish {
    public static void main(String[] args) {
        //定义WebService的发布地址，这个地址就是提供给外界访问Webervice的URL地址，URL地址格式为：http://ip:端口号/xxxx
        //String address = "http://192.168.1.100:8989/";这个WebService发布地址的写法是合法的
        //String address = "http://192.168.1.100:8989/Webservice";这个WebService发布地址的是合法的
        String address = "http://127.0.0.1:8088/WS_Server/Webservice/WebServiceGoodsDeliveryService";
        //使用Endpoint类提供的publish方法发布WebService，发布时要保证使用的端口号没有被其他应用程序占用
        Endpoint.publish(address , new WebServiceGoodsDeliveryServiceImpl());
        System.out.println("发布webservice成功!");


        //访问
        //   http://127.0.0.1:8088/WS_Server/Webservice/WebServiceGoodsDeliveryService?wsdl


        //CXF 生成客户端
        //wsdl2java -encoding utf-8 -d D:\zn\back http://127.0.0.1:8088/WS_Server/Webservice/WebServiceGoodsDeliveryService?wsdl
    }




}
