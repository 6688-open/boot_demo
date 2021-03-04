package com.dj.boot.webservice.webserver.impl;

import com.dj.boot.webservice.webserver.WebServiceGoodsDeliveryService;

import javax.jws.WebService;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.webservice
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2021-01-14-14-04
 */
//使用@WebService注解标注WebServiceI接口的实现类WebServiceImpl
@WebService
public class WebServiceGoodsDeliveryServiceImpl implements WebServiceGoodsDeliveryService {


    @Override
    public String transportGrantUserPermission(String userName) {
        return userName;
    }
}
