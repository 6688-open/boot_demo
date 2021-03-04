package com.dj.boot.handeltest.biz;

import com.dj.boot.handeltest.AbstractHandlerType;
import com.dj.boot.handeltest.HandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SendLocationHandler
 * @ProjectName: boot_demo
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-15-15-58
 */
@Slf4j
@Component
@HandlerType("1")
public class SendLocationHandler extends AbstractHandlerType {

    @Override
    public List<String> handle(String condition) {
        System.out.println("SendLocationHandler");
        List<String> list = new ArrayList<>();
        list.add("SendLocationHandler");
        list.add(condition);
        return list;
    }
}
