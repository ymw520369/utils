/**
 * TimeHelper.java 2012-4-6
 */
package org.alan.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间帮助者类
 *
 * @author 杨明伟
 */
public final class TimeHelper {
    /**
     * 一小时的秒数
     */
    public final static int HOUR_SECOND = 3600;
    /**
     * 一天的秒数
     */
    public final static int DAY_SECOND = 3600 * 24;
    /**
     * 一周的秒数
     */
    public final static int WEEK_SECOND = 7 * 24 * 60 * 60;
    /**
     * 本地时间处理器
     */
    public static DateFormat LocaleDateFormat = null;
    /**
     * 时间格式,默认值为yyyy-MM-dd HH:mm:ss
     */
    public static String dateFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时区，默认为GMT+8
     */
    public static String timeZone = "GMT+8";

    /**
     * 根据传入的目标时间返回1970年1月1日到目标时间的秒数，如果传入的时间格式不对，则返回-1
     *
     * @param strDate 一个时间的字符串表示法
     * @return 返回1970年1月1日到目标时间的秒数，如果传入的时间格式不对，则返回-1
     */
    public static int getSecondTime(String strDate) {
        if (strDate == null || strDate.isEmpty() || "-1".equals(strDate)) {
            return -1;
        }
        try {
            Date date = getDateFormat().parse(strDate);
            return (int) (date.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 根据传入的目标时间返回1970年1月1日到目标时间的秒数，如果传入的时间格式不对，则返回-1
     * <p>
     * yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 一个时间的字符串表示法
     * @return 返回1970年1月1日到目标时间的秒数，如果传入的时间格式不对，则返回-1
     */
    public static int getSecondTimeBy(String strDate) {
        return getSecondTime(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据传入的目标时间返回1970年1月1日到目标时间的秒数，如果传入的时间格式不对，则返回-1
     * <p>
     * yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 一个时间的字符串表示法
     * @return 返回1970年1月1日到目标时间的毫秒数，如果传入的时间格式不对，则返回-1
     */
    public static long getTimeMillisBy(String strDate) {
        return getTimeMillis(strDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    /**
     * 获取时间格式化类
     *
     * @return
     */
    public static DateFormat getDateFormat() {
        if (LocaleDateFormat == null) {
            LocaleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                    new DateFormatSymbols());
            LocaleDateFormat.setTimeZone(TimeZone.getTimeZone(""));
        }
        return LocaleDateFormat;
    }

    /**
     * 根据传入的目标时间返回1970年1月1日到目标时间的秒数，如果传入的时间格式不对，则返回-1
     *
     * @param strDate 一个时间的字符串表示法
     * @return 返回1970年1月1日到目标时间的秒数，如果传入的时间格式不对，则返回-1
     */
    public static int getSecondTime(String strDate, String format) {
        if (strDate == null || strDate.isEmpty() || "-1".equals(strDate)) {
            return -1;
        }
        try {
            Date date = getDateFormat(format).parse(strDate);
            return (int) (date.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 根据传入的目标时间返回1970年1月1日到目标时间的秒数，如果传入的时间格式不对，则返回-1
     *
     * @param strDate 一个时间的字符串表示法
     * @return 返回1970年1月1日到目标时间的毫秒数，如果传入的时间格式不对，则返回-1
     */
    public static long getTimeMillis(String strDate, String format) {
        if (strDate == null || strDate.isEmpty() || "-1".equals(strDate)) {
            return -1;
        }
        try {
            Date date = getDateFormat(format).parse(strDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取时间格式化(yyyy-MM-dd HH:mm:ss)类
     *
     * @return
     */
    public static DateFormat getDateFormat(String format) {
        DateFormat LocaleDateFormat = new SimpleDateFormat(format,
                new DateFormatSymbols());
        LocaleDateFormat.setLenient(false);
        LocaleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return LocaleDateFormat;
    }

    /**
     * 根据两个时间判定是不是到新的一周了
     *
     * @param currentTime 当前时间
     * @param lastTime    目标比较的时间
     * @return
     */
    public static boolean isNewWeek(int currentTime, int lastTime) {

        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 将日历设定到当前时间
        calendar.setTimeInMillis((long) currentTime * 1000L);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int tempTime = (int) (calendar.getTimeInMillis() / 1000);
        if (tempTime > lastTime) {
            return true;
        }

        // // 当前的年份
        // int currentYear = calendar.get(Calendar.YEAR);
        // // 当前在一年中的第多少周
        // int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        //
        // // 将日历设定到上一次的时间
        // calendar.setTimeInMillis((long) lastTime * 1000L);
        // // 上一次的年份
        // int lastYear = calendar.get(Calendar.YEAR);
        // // 上一次的周数
        // int lastWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        // if (currentYear > lastYear || currentWeek > lastWeek) {
        // return true;
        // }

        return false;
    }

    /**
     * 判断两个时间是不是到了新的一天
     */
    public static boolean isNewDay(int currentTime, int lastTime) {

        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 将日历设定到当前时间
        calendar.setTimeInMillis((long) currentTime * 1000L);
        // 当前的年份
        int currentYear = calendar.get(Calendar.YEAR);
        // 当前在一年中的第多少周
        int currentWeek = calendar.get(Calendar.DAY_OF_YEAR);

        // 将日历设定到上一次的时间
        calendar.setTimeInMillis((long) lastTime * 1000L);
        // 上一次的年份
        int lastYear = calendar.get(Calendar.YEAR);
        // 上一次的周数
        int lastWeek = calendar.get(Calendar.DAY_OF_YEAR);

        if (currentYear > lastYear || currentWeek > lastWeek) {
            return true;
        }
        return false;

    }

    /**
     * 获取给定时间的格林时间，单位为秒
     *
     * @param currentTime 当前时间
     * @param week        一周中的某一天
     * @param hour        一天中的某一个小时（24小时制）
     * @param min         分
     * @return 给定时间的格林时间，单位为秒
     */
    public static int getThisWeekTime(int currentTime, int week, int hour,
                                      int min) {
        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 将日历设定到当前时间
        calendar.setTimeInMillis((long) currentTime * 1000L);
        calendar.set(Calendar.DAY_OF_WEEK, week + 1);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        return (int) ((calendar.getTimeInMillis()) / 1000);
    }

    /**
     * 打印给定的时间,输出格式为默认格式
     *
     * @param currentTime
     * @return
     */
    public static String getDate(int currentTime) {
        return getDate(currentTime * 1000L);
    }

    /**
     * 打印给定的时间,输出格式为默认格式
     *
     * @param currentTime
     * @return
     */
    public static String getDate(long currentTime) {
        Date date = new Date(currentTime);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,
                new DateFormatSymbols());
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }

    /**
     * 获取14位时间格式
     *
     * @param currentTime 当前时间
     * @return
     */
    public static String get14PositionDate(int currentTime) {
        Date date = new Date((long) currentTime * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss",
                new DateFormatSymbols());
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }

    public static String getDefaultDate(int secondTime) {
        Date date = new Date((long) secondTime * 1000L);
        return getDateFormat().format(date);
    }

    /**
     * 获取当天凌晨时间 秒
     */
    public static int getCurrentDateZeroSecondTime() {
        GregorianCalendar cd = new GregorianCalendar();
        cd.setTimeZone(TimeZone.getTimeZone(timeZone));
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        cd.add(GregorianCalendar.DATE, 0);
        int second = (int) (cd.getTimeInMillis() / 1000);
        return second;
    }

    /**
     * 获取当天整点时间
     */
    public static int getCurrentDateSecondTime(int hour, int min, int second) {
        GregorianCalendar cd = new GregorianCalendar();
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        cd.setTimeZone(TimeZone.getTimeZone(timeZone));
        cd.set(Calendar.HOUR_OF_DAY, hour);
        cd.set(Calendar.MINUTE, min);
        cd.set(Calendar.SECOND, second);
        cd.set(Calendar.MILLISECOND, 0);
        int returnSecond = (int) (cd.getTimeInMillis() / 1000);
        return returnSecond;
    }

    /**
     * 获取当天整点时间
     */
    public static int getSecondTime(int year, int month, int date, int hour,
                                    int min, int second) {
        GregorianCalendar cd = new GregorianCalendar();
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        cd.setTimeZone(TimeZone.getTimeZone(timeZone));
        cd.set(Calendar.YEAR, year);
        cd.set(Calendar.MONTH, month - 1);
        cd.set(Calendar.DATE, date);
        cd.set(Calendar.HOUR_OF_DAY, hour);
        cd.set(Calendar.MINUTE, min);
        cd.set(Calendar.SECOND, second);
        cd.set(Calendar.MILLISECOND, 0);
        int returnSecond = (int) (cd.getTimeInMillis() / 1000);
        return returnSecond;
    }

    /**
     * 获得给定时间明日凌晨时间
     *
     * @param currentTime
     * @return
     */
    public static int getTomorrowZeroSecondTime(int currentTime) {
        GregorianCalendar cd = new GregorianCalendar();
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        cd.setTimeZone(TimeZone.getTimeZone(timeZone));
        cd.setTimeInMillis((currentTime + 3600 * 24) * 1000l);
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        cd.add(GregorianCalendar.DATE, 0);
        int second = (int) (cd.getTimeInMillis() / 1000);
        return second;
    }

    public static int getNextDaySecondTime(int currentTime, int nextDayCount,
                                           int hour, int min, int sec) {
        GregorianCalendar cd = new GregorianCalendar();
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        cd.setTimeZone(TimeZone.getTimeZone(timeZone));
        cd.setTimeInMillis((currentTime + 3600L * 24L * nextDayCount) * 1000l);
        cd.set(Calendar.HOUR_OF_DAY, hour);
        cd.set(Calendar.MINUTE, min);
        cd.set(Calendar.SECOND, sec);
        cd.set(Calendar.MILLISECOND, 0);
        int second = (int) (cd.getTimeInMillis() / 1000);
        return second;
    }

    /**
     * 获取当周第一天凌晨 秒,yyyy-MM-dd格式
     */
    public static int getCurrentWeekFirstDateZeroSecondTime() {
        int time = (int) (System.currentTimeMillis() / 1000);
        int dayOfWeek = getWeekOfDate(time);
        int newSecond = time - ((dayOfWeek - 1) * 24 * 3600);
        GregorianCalendar cd = new GregorianCalendar();
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        cd.setTimeZone(TimeZone.getTimeZone(timeZone));
        cd.setTimeInMillis(newSecond * 1000L);
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        cd.add(GregorianCalendar.DATE, 0);
        int second = (int) (cd.getTimeInMillis() / 1000);
        return second;
    }

    /**
     * 打印当前时间，采用默认的年月日
     *
     * @return
     */
    public static String getDate() {
        Date date = new Date();
        return getDateFormat().format(date);
    }

    /**
     * 获取两个时间的日期差
     *
     * @param secondTime1
     * @param secondTime2
     * @return
     */
    public static int getDateDifference(int secondTime1, int secondTime2) {
        if (secondTime1 <= 0)
            return 0;
        GregorianCalendar cd1 = new GregorianCalendar();
        cd1.setTimeZone(TimeZone.getTimeZone(timeZone));
        cd1.setTimeInMillis((long) secondTime1 * 1000L);
        int day1 = cd1.get(Calendar.DAY_OF_YEAR);

        GregorianCalendar cd2 = new GregorianCalendar();
        cd2.setTimeZone(TimeZone.getTimeZone(timeZone));
        cd2.setTimeInMillis((long) secondTime2 * 1000L);
        int day2 = cd2.get(Calendar.DAY_OF_YEAR);
        return Math.abs(day2 - day1);
    }

    /**
     * 获取当前年份
     *
     * @param currentTime
     * @return
     */
    public static int getYear(int currentTime) {
        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 将日历设定到当前时间
        calendar.setTimeInMillis((long) currentTime * 1000L);
        // 当前的年份
        int currentYear = calendar.get(Calendar.YEAR);
        return currentYear;
    }

    /**
     * 获取当前月份
     *
     * @param currentTime
     * @return
     */
    public static int getMonthOfYear(int currentTime) {
        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 将日历设定到当前时间
        calendar.setTimeInMillis((long) currentTime * 1000L);
        // 当前的年份
        int currentmonth = calendar.get(Calendar.MONTH);
        return currentmonth + 1;
    }

    /**
     * 获取当前日份
     *
     * @param currentTime
     * @return
     */
    public static int getDateOfMonth(int currentTime) {
        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 将日历设定到当前时间
        calendar.setTimeInMillis((long) currentTime * 1000L);
        // 当前的年份
        int currentDate = calendar.get(Calendar.DATE);
        return currentDate;
    }

    /**
     * 获取当前时间在一年中的周
     *
     * @param currentTime
     * @return
     */
    public static int getWeek(int currentTime) {
        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 将日历设定到当前时间
        calendar.setTimeInMillis((long) currentTime * 1000L);
        // 当前的年份
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return currentWeek;
    }

    /**
     * 获取当天是周几
     */
    public static int getWeekOfDate(int currentTime) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};

        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis((long) currentTime * 1000L);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * @param currentTime
     * @return
     */
    public static int getHour(int currentTime) {
        Calendar calendar = Calendar
                .getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 将日历设定到当前时间
        calendar.setTimeInMillis((long) currentTime * 1000L);
        // 当前的年份
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        return currentHour;
    }

}
