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
@Component("secondJop")
public class SecondJop {

    public void execute()  {
        LogUtils.info("secondJop");
    }
}
