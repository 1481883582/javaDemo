package com.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LocalDateTimeUtil {
    public static final String DATE_YYYYMM_PATTERN = "yyyyMM";
    public static final String DATE_YEAR_MONTH_PATTERN = "yyyy-MM";
    public static final String DATE_YEAR_PATTERN = "yyyy";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SHORTTIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_PATTERN_2 = "yyyy/MM/dd";
    public static final String DATE_POINT_PATTERN = "yyyy.MM.dd";
    public static final String DATE_POINT2_PATTERN = "yyyy.MM";
    public static final String DATE_YYYYMMDDHHmm = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_HHmmss = "HH:mm:ss";
    public static final String DATE_mdHHmmss = "MM-dd HH:mm:ss";

    //获取当前时间的LocalDateTime对象
    //LocalDateTime.now();

    //根据年月日构建LocalDateTime
    //LocalDateTime.of();

    //比较日期先后
    //LocalDateTime.now().isBefore(),
    //LocalDateTime.now().isAfter(),
    private static List<String> startWeekStr = new ArrayList<>();

    private static List<Long> startWeekMillis = new ArrayList<>();

    /**
     * 对象转换为指定格式字符串【yyyy-MM-dd HH:mm:ss】
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatStr(Date date, String pattern) {
        return "【"+format(date, pattern)+"】";
    }


    /**
     * 对象转换为指定格式字符串
     *
     * @param calendar
     * @param pattern
     * @return
     */
    public static String format(Calendar calendar, String pattern) {
        return format(calendar.getTime(), pattern);
    }

    /**
     * 对象转换为指定格式字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 之前7天的格式化时间 xxxx-xx-xx 00:00:00
     * @return
     */
    public static List<String> startWeekFormatTime(){
        if(Strings.isNotEmpty(startWeekStr)){
            return startWeekStr;
        }
        synchronized (LocalDateTimeUtil.class){
            if(Strings.isNotEmpty(startWeekStr)){
                return startWeekStr;
            }
            LocalDateTime nowDate = LocalDateTimeUtil.getDayStart(LocalDateTime.now());
            for(int i=0; i>=-7 ;i--){
                LocalDateTime startWeekDate = LocalDateTimeUtil.plus(nowDate, i, ChronoUnit.DAYS);
                String formatTime = LocalDateTimeUtil.formatTime(startWeekDate, DATE_TIME_PATTERN);
                startWeekStr.add(formatTime);

            }
            startWeekStr=startWeekStr.stream().sorted().collect(Collectors.toList());
            return startWeekStr;
        }
    }

    /**
     * 之前7天的时间戳 xxxx-xx-xx 00:00:00
     * @return
     */
    public static List<Long> startWeekMillis(){
        if(Strings.isNotEmpty(startWeekMillis)){
            return startWeekMillis;
        }
        synchronized (LocalDateTimeUtil.class){
            if(Strings.isNotEmpty(startWeekMillis)){
                return startWeekMillis;
            }
            LocalDateTime nowDate = LocalDateTimeUtil.getDayStart(LocalDateTime.now());
            for(int i=0; i>=-7 ;i--){
                LocalDateTime startWeekDate = LocalDateTimeUtil.plus(nowDate, i, ChronoUnit.DAYS);
                Long milliByTime = LocalDateTimeUtil.getMilliByTime(startWeekDate);
                startWeekMillis.add(milliByTime);
            }
            startWeekMillis=startWeekMillis.stream().sorted().collect(Collectors.toList());
            return startWeekMillis;
        }
    }

    /**
     * Date转换为LocalDateTime
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     * @param time
     * @return
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期的毫秒
     * @param time
     * @return
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的秒
     * @param time
     * @return
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间的指定格式
     * @param time
     * @param pattern
     * @return
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式
     * @param pattern
     * @return
     */
    public static String formatNow(String pattern) {
        return  formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field){
        return time.minus(number,field);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     * @param startTime
     * @param endTime
     * @param field  单位(年月日时分秒)
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();
        return field.between(startTime, endTime);
    }

    /**
     * 获取一天的开始时间，2017,7,22 00:00
     * @param time
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * 获取一天的结束时间，2017,7,22 23:59:59.999999999
     * @param time
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }
}
