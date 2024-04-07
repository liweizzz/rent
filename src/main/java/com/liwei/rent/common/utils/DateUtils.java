package com.liwei.rent.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    private static final String YYYY_MM = "yyyy-MM";

    public static Date getDate(){
        return new Date();
    }

    /**
     * 获取当前时间的年月，YYYY-MM
     * @return
     */
    public static String getCurYearAndMonth(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern(YYYY_MM));
    }

}
