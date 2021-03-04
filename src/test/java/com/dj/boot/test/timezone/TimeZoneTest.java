package com.dj.boot.test.timezone;

import com.dj.boot.common.util.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName aaa
 * @Description TODO
 * @Author wj
 * @Date 2020/1/6 18:35
 * @Version 1.0
 **/
public class TimeZoneTest {
    public static void main(String[] args) {
        Date date = new Date();
        int i = date.getTimezoneOffset();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        BigDecimal bigDecimal = new BigDecimal(-11);
        int count = bigDecimal.multiply(new BigDecimal(60)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, count);
        String format = simpleDateFormat.format(calendar.getTime());
        String substring = format.substring(0, 10).replace("-", "/");
        LogUtils.info(substring);

        String timeZone = "UTC+8";
        praseFormat(timeZone);
    }


    public static String praseFormat (String timeZone) {
        if (StringUtils.isNotEmpty(timeZone) &&  timeZone.contains("UTC")) {
            String t = timeZone.substring(4, timeZone.length());
            String zooe = "";
            if (t.contains(".")) {
                String[] split = t.split("\\.");
                String h = split[0];
                String m = split[1];
                String m1 = "0."+m;
                BigDecimal decimal = new BigDecimal(m1).multiply(new BigDecimal("60")).setScale(0, BigDecimal.ROUND_HALF_UP);
                String string = decimal.toString();
                if (h.length() == 1) {
                    h = "0"+h;
                }
                zooe = h+":"+string;
            } else {
                if (t.length() == 1) {
                    zooe = "0"+t+":"+"00";
                } else {
                    zooe = t+":"+"00";
                }
            }
            if (timeZone.contains("+")) {
                timeZone = "UTC+"+zooe;
            } else {
                timeZone = "UTC-"+zooe;
            }
            return timeZone;  //"UTC+08:00"
        }
        return "";

    }
}
