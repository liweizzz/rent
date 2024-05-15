package com.liwei.rent.common.utils;

import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.exception.RentException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtils {
    private static final String YYYY_MM = "yyyy-MM";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM);

    public static Date getDate(){
        return new Date();
    }

    /**
     * 获取当前时间的年月，YYYY-MM
     * @return
     */
    public static String getYearAndMonthAsStr(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern(YYYY_MM));
    }

    public static Date getYearAndMonthAsDate(){
        Date date ;
        try {
            date = sdf.parse(getYearAndMonthAsStr());
        } catch (ParseException e) {
            log.error("日期转换错误");
            throw new RentException(ErrorCodeEnum.SYSTEM_ERROR);
        }
        return date;
    }

    public static Date getYearAndMonthAsDate(String str){
        Date date ;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            log.error("日期转换错误");
            throw new RentException(ErrorCodeEnum.SYSTEM_ERROR);
        }
        return date;
    }

    /**
     * 获取指定日期，yyyy-MM-dd
     * @param localDateTime
     * @return
     */
    public static String getStrDateTime(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern(YYYY_MM_DD));
    }

}
