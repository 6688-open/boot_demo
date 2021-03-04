package com.dj.boot.common.mapper.base;

import lombok.SneakyThrows;
import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateConvert
 * @Description TODO
 * @Author wj
 * @Date 2020/05/22 19:04
 * @Version 1.0
 **/
public class DateConvert {
    private static ThreadLocal<DateFormat> dataTimeThreadLocal = new ThreadLocal<DateFormat>() {
        @Override
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static ThreadLocal<DateFormat> dataThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public DateConvert() {
    }

    @SneakyThrows
    @StringToDate
    public static Date stringToDate(String str)  {
        if (str != null && !"".equals(str.trim())) {
            try {
                return ((DateFormat)dataThreadLocal.get()).parse(str);
            } catch (ParseException var2) {
                throw new Exception(str + "转换错误"+ var2);
            }
        } else {
            return null;
        }
    }

    @SneakyThrows
    @DateToString
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        } else {
            try {
                return ((DateFormat)dataThreadLocal.get()).format(date);
            } catch (Exception var2) {
                throw new Exception("时间转换错误"+var2);
            }
        }
    }

    @SneakyThrows
    @StringToDateTime
    public static Date stringToDateTime(String str) {
        if (str != null && !"".equals(str.trim())) {
            try {
                return ((DateFormat)dataTimeThreadLocal.get()).parse(str);
            } catch (ParseException var2) {
                throw new Exception(str + "转换错误" + var2);
            }
        } else {
            return null;
        }
    }

    @SneakyThrows
    @DateTimeToString
    public static String dateTimeToString(Date date) {
        if (date == null) {
            return null;
        } else {
            try {
                return ((DateFormat)dataTimeThreadLocal.get()).format(date);
            } catch (Exception var2) {
                throw new Exception("时间转换错误" + var2);
            }
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    @Qualifier
    public @interface DateTimeToString {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    @Qualifier
    public @interface StringToDateTime {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    @Qualifier
    public @interface DateToString {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    @Qualifier
    public @interface StringToDate {
    }
}
