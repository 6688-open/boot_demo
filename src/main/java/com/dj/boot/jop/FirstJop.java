package com.dj.boot.jop;

import com.dj.boot.common.util.LogUtils;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

/**
 * @ClassName First
 * @Description TODO
 * @Author wj
 * @Date 2019/12/25 11:48
 * @Version 1.0
 **/
@DisallowConcurrentExecution
@Component("firstJop")
public class FirstJop {

    public void execute() {
        //执行相关业务逻辑
        LogUtils.info("firstJop");
    }
}
