package com.dj.boot.service.async.impl;

import com.dj.boot.common.util.LogUtils;
import com.dj.boot.service.async.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName AsyncServiceImpl
 * @Description TODO
 * @Author wj
 * @Date 2019/12/27 17:46
 * @Version 1.0
 **/
@Service
public class AsyncServiceImpl implements AsyncService {
    @Async
    @Override
    public String testAsync() {
        try {
            Thread.sleep(10000);
            LogUtils.info("testAsync");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Async";
    }
}
