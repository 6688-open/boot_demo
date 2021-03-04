package com.dj.boot.jop;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName HttpClientController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/3 17:40
 * @Version 1.0
 **/
@Component
public class HttpClientTesk {

    /**
     * 定时增量
     */
//    @Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * ? ")
//    public void flushIndexDelta() {
//        try {
//            HttpClientUtil.sendHttpRequest("http://localhost:8081/solr/slorCoreDJmallProduct/dataimport?command=delta-import", HttpClientUtil.HttpRequestMethod.GET, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



//    @Scheduled(cron = "*/6 * * * * ?")
//    public void test() {
//        System.out.println( new Date());
//    }
}
