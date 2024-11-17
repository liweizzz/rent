package com.liwei.rent.common.utils;

import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.exception.RentException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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

    /**
     * 转换指定日期为date类型
     * @param str
     * @return
     */
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

    /**
     * 获取房租起始日期
     * @param localDate
     * @return
     */
    public static LocalDate getStartDay(LocalDate localDate){
        //当前日期的后一天
        return localDate.plusDays(1);
    }

    /**
     * 获取房租结束日期
     * @param localDate
     * @return
     */
    public static LocalDate getEndDay(LocalDate localDate){
        //如果是第一天，则取当月最后一天
        if(1 == localDate.getDayOfMonth()){
            return localDate.with(TemporalAdjusters.lastDayOfMonth());
        }
        //当月日期+一个月
        return localDate.plusMonths(1).minusDays(1);
    }

    /**
     * 指定年月，计算上月
     * @param date
     * @return
     */
    public static String calMonth(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(date, formatter);
        YearMonth previousMonth = yearMonth.minusMonths(1);
        return previousMonth.format(formatter);
    }

    /**
     * 获取报表sheet名称
     * @return
     */
    public static String getReportFieldName(String pattern){
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 格式化日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        // 获取当前月的第一天和最后一天
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);
        return firstDayOfMonth.format(formatter) + "-" + lastDayOfMonth.format(formatter);
    }

}
