package com.dj.boot.btp.common;

import com.dj.boot.btp.common.util.markutil.InBoundMarkEnum;
import com.dj.boot.btp.common.util.markutil.MarkUtils;
import org.junit.Test;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.btp.common.util
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-19-18-47
 */
public class MarkUtilsTest {


    @Test
    public void test1(){


        //设置标记位
        MarkUtils markUtils = new MarkUtils(); //初始化一个长度200 且都为0的字符串
        markUtils.inChar(InBoundMarkEnum.MARK_5_1);//根据入参bit位 的 value 修改源 字符串
        System.out.println(markUtils.toString());

        //获取判断标记位
        boolean askForPrice = markUtils.isMark(InBoundMarkEnum.MARK_5_1); //根据入参 bit位 的 value 判断 源字符串是否匹配
        boolean b = markUtils.isMark(InBoundMarkEnum.MARK_6_1);

        markUtils.inChar(InBoundMarkEnum.MARK_6_1);
        boolean b1 = markUtils.isMark(InBoundMarkEnum.MARK_6_1);

        System.out.println(askForPrice);
        System.out.println(b);


        //111
        //设置标记位
        MarkUtils markUtil = new MarkUtils("000010000000000000000000000000000000000000000"); //自定义初始化一个字符串  不足200位 补齐0到长度200
        boolean mark = markUtil.isMark(InBoundMarkEnum.MARK_5_1);
    }

}
