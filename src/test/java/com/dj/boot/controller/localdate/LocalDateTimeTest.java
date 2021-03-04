package com.dj.boot.controller.localdate;

import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.common.util.LogUtils;
import com.dj.boot.pojo.User;
import com.dj.boot.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LocalDateTimeTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/2 14:50
 * @Version 1.0
 **/
public class LocalDateTimeTest extends BootDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Override
    public void run() {
        LogUtils.info("LocalDateTimeTest   starting ...........");

        DateTimeFormatter pattern1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DateTimeFormatter pattern2 = DateTimeFormatter.ofPattern("HH:mm:ss");

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<User> list = userService.list();
        list.forEach(user -> {
            String format = pattern.format(user.getCreateTime());
            user.setCreateTimeStr(format);
        });




        //时间类型 转成 字符串类型
        String format = pattern1.format(LocalDate.now());
        LogUtils.info("LocalDate转成String类型的时间" + format);
        String format1 = pattern2.format(LocalTime.now());
        LogUtils.info("LocalTime转成String类型的时间" + format1);
        String format2 = pattern.format(LocalDateTime.now());
        LogUtils.info("LocalDateTime转成String类型的时间" + format2);


        //字符串类型  转时间类型
        LocalDateTime localDateTime = LocalDateTime.parse("2018-06-01 10:12:05",pattern);
        LogUtils.info("String类型的时间转成LocalDateTime："+localDateTime);

        LocalDate localDate = LocalDate.parse("2018-06-01", pattern1);
        LogUtils.info("String类型的时间转成localDate："+localDate);

        LocalTime localTime = LocalTime.parse("10:12:05", pattern2);
        LogUtils.info("String类型的时间转成localTime："+localTime);


        //增加一年
        LocalDateTime localDateTime1 = localDateTime.plusYears(1);
        LocalDateTime localDateTime2 = localDateTime.plus(1, ChronoUnit.YEARS);
        //减少一月
        LocalDateTime localDateTime3 = localDateTime.minusMonths(1);
        LocalDateTime minus = localDateTime.minus(1, ChronoUnit.MONTHS);



        int year = localDateTime.getYear();
        int dayOfMonth = localDateTime.getDayOfMonth();



        //取差值
        LocalDateTime localDateTime4 = LocalDateTime.now();

        long between = ChronoUnit.MINUTES.between(localDateTime, localDateTime4);


        Duration duration = Duration.between(localDateTime, localDateTime4);
        long l = duration.toMinutes();
        long l1 = duration.toHours();
        long l2 = duration.toDays();


        //localDateTime  两个日期相差的天数
        long day = ChronoUnit.DAYS.between(localDateTime, localDateTime4);
        LogUtils.info(l2+"");
        LogUtils.info(day+"");



        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 2);

        Date time = calendar.getTime();


        long days = TimeUnit.MILLISECONDS.toDays(time.getTime() - date.getTime());
        LogUtils.info(days+"");

    }
}
