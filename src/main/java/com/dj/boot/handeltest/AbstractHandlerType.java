package com.dj.boot.handeltest;


import java.util.List;

/**
 * @description: 抽象处理器
 * @author: wj
 * @create: 2020/03/21 23:02
 */
public abstract class AbstractHandlerType {
    //abstract public List<TransportApartPointDto> handle(LogisticsTrackCondition condition);
    abstract public List<String> handle(String condition);
}
