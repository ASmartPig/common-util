package com.itest.developer.utils;

import com.google.common.base.Preconditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import static java.time.ZoneId.systemDefault;

public class DateUtil {
    /**
     * 一天的时间的毫秒数
     */
    public static final long ONE_DAY_TIME = 86400000L;

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_YEAR_MONTH_PATTERN = "yyyy-MM";
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        } else if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }
    }

    /***
     * >0未来 =0 今天 <0过去
     * @param date
     * @return
     * @throws DateTimeException 时间不合法
     */
    public static int judgeDay(Date date) {
        Preconditions.checkNotNull(date);
        LocalDate localDate = toLocalDate(date);
        LocalDate now = LocalDate.now();
        return localDate.compareTo(now);
    }

    /**
     * Date 转 LocalDate
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), systemDefault());
    }


    /**
     * Long 转 date
     * @param time
     * @return
     */
    public static Date getDate(Long time) {
        Date day = new Date();
        if (time != null && time > 0) {
            day = new Date(time);
        }
        return day;
    }

    /**
     * 判断给定的时间距离今天的时间天数
     *
     * @param time
     * @return
     */
    public static int distanceToday(long time) {
        if (time > startOfToday() && time < endOfToday()) {
            return 0;
        }
        if (time < startOfToday()) {
            return (int) ((time - startOfToday()) / ONE_DAY_TIME) - 1;
        }

        return (int) ((time - startOfToday()) / ONE_DAY_TIME);

    }


    /**
     * 开始时间
     * @param date
     * @return
     */
    public static long startOfDay(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return localDateTime.atZone(systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 结束时间
     * @param day
     * @return
     */
    public static long endOfDay(Date day) {
        LocalDateTime localDateTime = toLocalDateTime(day)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999);
        return localDateTime.atZone(systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取今天的开始时间
     *
     * @return
     */
    public static long startOfToday() {
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        return startTime.getTimeInMillis();
    }


    /**
     * 获取今天的结束时间
     *
     * @return
     */
    public static long endOfToday() {
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(new Date());
        startTime.set(Calendar.HOUR_OF_DAY, 23);
        startTime.set(Calendar.MINUTE, 59);
        startTime.set(Calendar.SECOND, 59);
        startTime.set(Calendar.MILLISECOND, 999);
        return startTime.getTimeInMillis();
    }






    /**
     * 获取两天的日期差
     *
     * @return
     */
    public static int getDayDateToDate(Long time,Long currentTime) {
        Long startDay = startOfDay(new Date(currentTime));
        Long endDay = endOfDay(new Date(currentTime));
        if (time > startDay && time < endDay) {
            return 0;
        }
        if (time < startDay) {
            return (int) ((time - startDay) / ONE_DAY_TIME) - 1;
        }

        return (int) ((time - startDay) / ONE_DAY_TIME);

    }

    /**
     * 该日该月第一秒
     * @return
     */
    public static long startOfMonth(){
        LocalDateTime lastDayOfMonth = LocalDateTime.now()
                .with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return lastDayOfMonth.atZone(systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 该日该月第一秒
     * @param day
     * @return
     */
    public static long startOfMonth(Date day){
        LocalDateTime lastDayOfMonth = toLocalDateTime(day)
                .with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return lastDayOfMonth.atZone(systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 该日该月第一秒
     * @param day
     * @return
     */
    public static long startOfMonth(LocalDate day){
        LocalDateTime lastDayOfMonth = day.atStartOfDay()
                .with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return lastDayOfMonth.atZone(systemDefault()).toInstant().toEpochMilli();
    }

    /***
     * 本月最后一秒
     * @return
     */
    public static long endOfMonth(){
        LocalDateTime lastDayOfMonth = LocalDateTime.now()
                .with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999);
        return lastDayOfMonth.atZone(systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 某日该月最后一秒
     * @param day
     * @return
     */
    public static long endOfMonth(Date day){
        LocalDateTime lastDayOfMonth = toLocalDateTime(day)
                .with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999);
        return lastDayOfMonth.atZone(systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 某日该月最后一秒
     * @param day
     * @return
     */
    public static long endOfMonth(LocalDate day){
        LocalDateTime lastDayOfMonth = day.atStartOfDay()
                .with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999);
        return lastDayOfMonth.atZone(systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 本年的最初时间
     * @return
     */
    public static long startOfYear() {
        Calendar startTime = Calendar.getInstance();
        int year=startTime.get(Calendar.YEAR);
        String end=year-1+"-12-31 23:59:59";
        Date temp=DateUtil.parseStringToDate(end, "yyyy-MM-dd HH:mm:ss");
        return temp.getTime();
    }

    /**
     * 本年的最末时间
     * @Autor liaoze
     * @return
     */
    public static long endOfYear() {
        Calendar startTime = Calendar.getInstance();
        int year=startTime.get(Calendar.YEAR);
        String end=year+1+"-12-31 23:59:59";
        Date temp=DateUtil.parseStringToDate(end, "yyyy-MM-dd HH:mm:ss");
        return temp.getTime();
    }


    public static Date parseStringToDate(String date, String format) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sm = new SimpleDateFormat(format);

            try {
                return sm.parse(date);
            } catch (ParseException var4) {
                return null;
            }
        }
    }
    /**
     * 判断传入时间是否为今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(long time) {

        return time >= startOfToday() && time <= endOfToday();
    }


}
