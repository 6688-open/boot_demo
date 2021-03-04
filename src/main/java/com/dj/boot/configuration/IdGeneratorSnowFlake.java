package com.dj.boot.configuration;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ext.wangjia
 */
@Component
@Slf4j
public class IdGeneratorSnowFlake {
    /**
     * 终端ID
     */
    private long workerId = 0;
    /**
     * 数据中心ID
     */
    private long dataCenterId = 0;

    private Snowflake snowflake = IdUtil.createSnowflake(workerId, dataCenterId);


    @PostConstruct
    public void  init () {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前工作的workerId {}",  workerId);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("当前工作的workerId  获取失败",  e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }



    public synchronized long snowFlakeId(){
        return snowflake.nextId();
    }

    public synchronized long snowFlakeId(long workerId, long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }


    public static void main(String[] args) {
        long l = new IdGeneratorSnowFlake().snowFlakeId();
        System.out.println(l);
    }


}
