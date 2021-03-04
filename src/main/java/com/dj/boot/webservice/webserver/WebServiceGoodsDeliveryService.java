package com.dj.boot.webservice.webserver;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.webservice
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2021-01-14-14-03
 */
//使用@WebService注解标注WebServiceI接口


import javax.jws.WebMethod;
import javax.jws.WebService;
@WebService
public interface WebServiceGoodsDeliveryService {
      //使用@WebMethod注解标注WebServiceI接口中的方法
      @WebMethod
      String transportGrantUserPermission(String userName);

}
