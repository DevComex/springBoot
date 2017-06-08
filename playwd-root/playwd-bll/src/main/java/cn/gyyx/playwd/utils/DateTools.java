package cn.gyyx.playwd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 功能：对日期操作的公共方法类
 * 
 */
public class DateTools {
	private static final Logger logger = GYYXLoggerFactory.getLogger(DateTools.class);
    public static final String TIMES_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyyMMdd";
    
    
    /***
     * 格式化日期的方法
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static Date parseDateForPattern(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(df.format(date));
        } catch (ParseException e) {
        	logger.error("格式化时间失败",e);
        }
        return null;
    }
    

    /***
     * 格式化日期的方法
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获得 yyyy-mm-dd格式的时间字符串
     */
    public static String getNewDate() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获得 yyyy-mm格式的时间字符串
     * 
     * @return
     */
    public static String getNowYYMM() {
        return formatDate(new Date(), "yyyy-MM");
    }

    /**
     * 利用传入的日期，返回上一个月当天的日期对象
     */
    @SuppressWarnings("deprecation")
	public static Date getPreviousMonth(Date current) {
        if (current.getMonth() != 1) {
            // 如果不是一月份，则直接减去一天
            return new Date(current.getYear(), current.getMonth() - 1, current.getDate());
        } else {
            // 如果是一月份，则需要在年上减去一天，月变为12
            return new Date(current.getYear() - 1, 12, current.getDate());
        }
    }
    
    /**
     * 利用传入的日期，返回下一个月的1号
     */
    public static Date nextMonthFirstDate(Date current) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(current);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
    
    /**
     * 利用传入的日期，返回上一个月的1号
     */
    public static Date previousMonthFirstDate(Date current) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(current);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /** 利用传入日期，返回一个上个月日期的yyyyMM的String表达式 */
    public static String getPreviousMonths(Date current) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(getPreviousMonth(current));
    }

    /***
     * 获取传入日期下一年的日期对象
     */
    @SuppressWarnings("deprecation")
	public static Date getNextYear(Date date) {
        return new Date(date.getYear() + 1, date.getMonth(), date.getDate());
    }

    /***
     * 获取传入日期下n年的日期对象
     */
    @SuppressWarnings("deprecation")
	public static Date getNextNYear(Date date, int n) {
        return new Date(date.getYear() + n, date.getMonth(), date.getDate());
    }

    /***
     * 计算传入日期的往后顺延N天以后的日期对象
     */
    public static Date getPreviousNDate(Date date, long n) {
        long time = date.getTime();
        // 用毫秒数来计算新的日期
        time = time - n * 24 * 60 * 60 * 1000;
        return new Date(time);
    }

    /***
     * 计算传入日期的往前推算N天的日期对象
     */
    public static Date getNextNDate(Date date, long n) {
        long time = date.getTime();
        // 用毫秒数来计算新的日期
        time = time + n * 24 * 60 * 60 * 1000;
        return new Date(time);
    }

    /***
     * 计算传入日期的前一天
     */
    public static Date getPreviousDate(Date date) {
        return getPreviousNDate(date, 1);
    }

    /***
     * 计算传入日期的后一天
     */
    public static Date getNextDate(Date date) {
        return getNextNDate(date, 1);
    }

    /**
     * 字符串转换成日期 "yyyy-MM-dd HH:mm:ss"
     */
    public static Date strToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
        	logger.error("格式化时间失败",e);
        }
        return date;
    }

    /**
     * 字符串转换成日期 "yyyy-MM-dd"
     */
    public static Date parse(String str, String formatStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.parse(str);
        } catch (Exception e) {
        	logger.error("格式化时间失败",e);
        }
        return null;
    }
    
    /**
     * 字符串转换成日期 "yyyy-MM-dd"
     */
    public static Date parse(String str) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(str);
        } catch (Exception e) {
        	logger.error("格式化时间失败",e);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     */
    public static Date parse(String str, String pattern, Date defaultValue) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(str);
        } catch (Exception e) {
        	logger.error("格式化时间失败",e);
            return defaultValue;
        }
    }

    /**
     * 根据传入的日期计算此日期与下一年该日相差的天数
     */
    public static int getDaysCountForYear(Date startDate) {
        // 获取传入日期下一年的日期对象
        Date nextYear = getNextYear(startDate);
        // 计算天数
        return getDaysCount(startDate, nextYear);
    }

    /***
     * 根据起始日期、终止日期计算天数
     */
    public static int getDaysCount(Date startDate, Date endDate) {
        return getDaysCount(startDate, 0, endDate, 0);
    }

    /**
     * 天数 [)
     * @param smdate
     * @param bdate
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /***
     * 根据年数返回所包含的天数
     */
    public static int getDaysFromYear(int year) {
        // 判断是平年还是闰年
        if ((year % 400 == 0) || (year % 100 != 0) && (year % 4 == 0)) {
            return 366;
        } else
            return 365;

    }

    /***
     * 根据起始日期、起始时间、终止日期、终止时间计算天数
     */
    public static int getDaysCount(Date startDate, int startHour, Date endDate, int endHour) {
        // 根据起始日期计算起始的毫秒
        long startTime = startDate.getTime();
        // 根据终止日期计算终止的毫秒
        long endTime = endDate.getTime();
        // 通过起始毫秒和终止毫秒的差值，计算天数
        int dayCount = (int) ((endTime - startTime) / (24 * 60 * 60 * 1000) + 1);

        if (endHour <= startHour) {
            if (startHour == 24 && endHour == 0) {
                dayCount = dayCount - 2;
            } else {
                dayCount = dayCount - 1;
            }
        }
        return dayCount;

    }

    public static int getDaysCount(Date startDate, long startHour, Date endDate, long endHour) {
        // 根据起始日期计算起始的毫秒
        long startTime = startDate.getTime();
        // 根据终止日期计算终止的毫秒
        long endTime = endDate.getTime();
        // 通过起始毫秒和终止毫秒的差值，计算天数
        int dayCount = (int) ((endTime - startTime) / (24 * 60 * 60 * 1000) + 1);

        if (endHour <= startHour) {
            if (startHour == 24 && endHour == 0) {
                dayCount = dayCount - 2;
            } else {
                dayCount = dayCount - 1;
            }
        }
        return dayCount;

    }

    /***
     * 根据起始日期、终止日期计算月数
     */
    public static int getMonthsCount(Date startDate, Date endDate) {
        return getMonthsCount(startDate, 0, endDate, 0);
    }

    /***
     * 根据起始日期、起始时间、终止日期、终止时间计算天数
     */
    @SuppressWarnings("deprecation")
	public static int getMonthsCount(Date startDate, int startHour, Date endDate, int endHour) {
        // 年份差
        int yearDiff = endDate.getYear() - startDate.getYear();
        // 月份差
        int monthDiff = endDate.getMonth() - startDate.getMonth();
        // 总的月数量
        int monthCount = monthDiff + yearDiff * 12;
        // 天数差
        long dayDiff = endDate.getDate() - startDate.getDate();
        // 不足一个月按一个月计算
        if (dayDiff > 0 || (dayDiff == 0 && endHour > startHour)) {
            monthCount = monthCount + 1;
        }

        return monthCount;

    }

    @SuppressWarnings("deprecation")
	public static int getMonthsCount(Date startDate, long startHour, Date endDate, long endHour) {
        // 年份差
        int yearDiff = endDate.getYear() - startDate.getYear();
        // 月份差
        int monthDiff = endDate.getMonth() - startDate.getMonth();
        // 总的月数量
        int monthCount = monthDiff + yearDiff * 12;
        // 天数差
        long dayDiff = endDate.getDate() - startDate.getDate();
        // 不足一个月按一个月计算
        if (dayDiff > 0 || (dayDiff == 0 && endHour > startHour)) {
            monthCount = monthCount + 1;
        }
        return monthCount;
    }

    /***
     * 获取当前年份
     */
    public static int getCurrentYear() {

        return Integer.parseInt(new SimpleDateFormat("yyyy").format(System.currentTimeMillis()));
    }

    /***
     * 根据日期获得年份
     */
    public static int getYear(Date date) {

        return Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
    }

    /***
     * 根据日期获得月份
     */
    public static int getMonth(Date date) {

        return Integer.parseInt(new SimpleDateFormat("MM").format(date));
    }

    /***
     * 根据日期获得日份
     */
    public static int getDay(Date date) {

        return Integer.parseInt(new SimpleDateFormat("dd").format(date));
    }

    /***
     * 根据起始日期、终止日期计算年数
     */
    public static int getYearsCount(Date startDate, Date endDate) {
        return getYearsCount(startDate, 0, endDate, 0);
    }

    /***
     * 根据起始日期、起始时间、终止日期、终止时间计算天数
     */
    public static int getYearsCount(Date startDate, int startHour, Date endDate, int endHour) {
        int yearCount = 0;

        // 获取之间的月数
        int monthCount = getMonthsCount(startDate, startHour, endDate, endHour);

        yearCount = monthCount / 12;

        if (monthCount % 12 != 0) {
            yearCount++;
        }

        // 返回年数
        return yearCount;
    }

    public static int getYearsCount(Date startDate, long startHour, Date endDate, long endHour) {
        int yearCount = 0;

        // 获取之间的月数
        int monthCount = getMonthsCount(startDate, startHour, endDate, endHour);

        yearCount = monthCount / 12;

        if (monthCount % 12 != 0) {
            yearCount++;
        }

        // 返回年数
        return yearCount;
    }

    /**
     * 根据日期获取总小时数
     */
    public static int getHoursCount(Date date) {
        return getHoursCount(date, 0);
    }

    /**
     * 根据日期和小时数，获取总小时数
     */
    public static int getHoursCount(Date date, Integer hour) {
        long hourCount = hour;
        hourCount += date.getTime() / 3600 / 1000;
        return (int) hourCount;
    }

    /***
     * 比较两个日期的大小
     */
    public static int compareDate(Date startDate, Date endDate) {
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        if (startTime < endTime) {
            return -1;
        }
        if (startTime == endTime) {
            return 0;
        }
        return 1;
    }

    /***
     * 比较两个日期的大小
     */
    public static int compareDate(String startDate, String endDate) {
        return compareDate(strToDate(startDate), strToDate(endDate));
    }

    /***
     * 根据起始日期、起始时间、终止日期、终止时间计算月数，计算的是去掉多余天数的月数
     */
    @SuppressWarnings("deprecation")
	public static int getMinMonthsCount(Date startDate, int startHour, Date endDate, int endHour) {
        // 年份差
        int yearDiff = endDate.getYear() - startDate.getYear();
        // 月份差
        int monthDiff = endDate.getMonth() - startDate.getMonth();
        // 总的月数量
        int monthCount = monthDiff + yearDiff * 12;
        // 天数差
        long dayDiff = endDate.getDate() - startDate.getDate();
        // 如果天数差小于0或者天数差等于零（并且终止小时小于起始小时），那么月数减一。
        if (dayDiff < 0 || (dayDiff == 0 && endHour < startHour)) {
            monthCount = monthCount - 1;
        }
        return monthCount;

    }

    /***
     * 根据起始日期、起始时间、终止日期、终止时间计算天数，计算的是相差月后余下的天数
     */
    @SuppressWarnings("deprecation")
	public static int getOtherDayCount(Date startDate, int startHour, Date endDate, int endHour) {
        // 相差的天数
        int dayCount = 0;
        // 时间变量
        long newStartTime = 0;
        // 终保日期getTime变量
        long endTime = 0;
        // 计算日相差
        int dayDiff = endDate.getDate() - startDate.getDate();
        // 如果日相差等于0，那endHour > startHour,那么返回一天，如：2010-10-10：02到2010-10-10:09
        if (dayDiff == 0) {
            if (endHour > startHour) {
                return 1;
            }
            // 如果日相差小于0
        } else if (dayDiff < 0) {
            // 如果startHour < endHour，如：2010-09-18:02------2010-10-10:08
            if (startHour < endHour) {
                Date newStartDate = new Date(endDate.getYear(), endDate.getMonth() - 1, startDate.getDate());
                newStartTime = newStartDate.getTime();
                endTime = endDate.getTime();
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000) + 1);
                // 如果startHour = endHour，如：2010-09-18:02------2010-10-10:02
            } else if (startHour == endHour) {
                Date newStartDate = new Date(endDate.getYear(), endDate.getMonth() - 1, startDate.getDate());
                newStartTime = newStartDate.getTime();
                endTime = endDate.getTime();
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000));
                // 如果startHour > endHour
            } else {
                Date newStartDate = new Date(endDate.getYear(), endDate.getMonth() - 1, startDate.getDate());
                newStartTime = newStartDate.getTime();
                endTime = endDate.getTime();
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000) + 1);
                // 如：2010-09-18:24------2010-10-10:00
                if (startHour == 24 && endHour == 0) {
                    dayCount = dayCount - 2;
                    // 如：2010-09-18:14------2010-10-10:10
                } else {
                    dayCount = dayCount - 1;
                }
            }
            // 日相差大于0
        } else if (dayDiff > 0) {
            // 如果startHour < endHour
            if (startHour > endHour) {
                Date newStartDate = new Date(endDate.getYear(), endDate.getMonth(), startDate.getDate());
                newStartTime = newStartDate.getTime();
                endTime = endDate.getTime();
                // 如：2010-10-10:08------2010-10-18:02
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000));
                // 如：2010-10-10:24------2010-10-18:00
                if (startHour == 24 && endHour == 0) {
                    dayCount = dayCount - 1;
                }
                // 如果startHour = endHour，如：2010-10-10:08------2010-10-18:08
            } else if (startHour == endHour) {
                Date newStartDate = new Date(endDate.getYear(), endDate.getMonth(), startDate.getDate());
                newStartTime = newStartDate.getTime();
                endTime = endDate.getTime();
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000));
                // 如果startHour < endHour，如：2010-10-10:02------2010-10-18:08
            } else {
                Date newStartDate = new Date(endDate.getYear(), endDate.getMonth(), startDate.getDate());
                newStartTime = newStartDate.getTime();
                endTime = endDate.getTime();
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000) + 1);
            }
        }
        return dayCount;
    }

    /***
     * 根据起始日期、起始时间、终止日期、终止时间计算天数，计算的是相差月后余下的天数
     */
    public static int getOtherDays(Date startDate, long startHour, Date endDate, long endHour) {
        Calendar scl = Calendar.getInstance();
        scl.setTime(startDate);
        Calendar ecl = Calendar.getInstance();
        ecl.setTime(endDate);
        int startday = scl.get(Calendar.DAY_OF_MONTH);
        int endYear = ecl.get(Calendar.YEAR);
        int endMonth = ecl.get(Calendar.MONTH) + 1;
        int endday = ecl.get(Calendar.DAY_OF_MONTH);

        // 相差的天数
        int dayCount = 0;
        // 时间变量
        long newStartTime = 0;
        // 终保日期getTime变量
        long endTime = 0;
        // 计算日相差
        int dayDiff = endday - startday;
        // 如果日相差等于0，那endHour > startHour,那么返回一天，如：2010-10-10：02到2010-10-10:09
        if (dayDiff == 0) {
            if (endHour > startHour) {
                return 1;
            }
            // 如果日相差小于0
        } else if (dayDiff < 0) {
            scl.set(endYear, endMonth - 2, startday);
            newStartTime = scl.getTimeInMillis();
            endTime = ecl.getTimeInMillis();
            // 如果startHour < endHour，如：2010-09-18:02------2010-10-10:08
            if (startHour < endHour) {
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000) + 1);
                // 如果startHour = endHour，如：2010-09-18:02------2010-10-10:02
            } else if (startHour == endHour) {
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000));
                // 如果startHour > endHour
            } else {
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000) + 1);
                // 如：2010-09-18:24------2010-10-10:00
                if (startHour == 24 && endHour == 0) {
                    dayCount = dayCount - 2;
                    // 如：2010-09-18:14------2010-10-10:10
                } else {
                    dayCount = dayCount - 1;
                }
            }
            // 去除最后一个月的天数
            if (endMonth == 5 || endMonth == 7 || endMonth == 10 || endMonth == 12) {
                dayCount = dayCount % 30;
            } else if (endMonth == 1 || endMonth == 2 || endMonth == 4 || endMonth == 6 || endMonth == 8
                    || endMonth == 9 || endMonth == 11) {
                dayCount = dayCount % 31;
            } else if (endMonth == 3 && endYear % 4 == 0 && endYear % 10 != 0) {
                if (startday > 29) {
                    dayCount = dayCount % 31;
                } else {
                    dayCount = dayCount % 29;
                }
            } else if (endMonth == 3) {
                if (startday > 28) {
                    dayCount = dayCount % 31;
                } else {
                    dayCount = dayCount % 28;
                }
            }

            // 日相差大于0
        } else if (dayDiff > 0) {
            scl.set(endYear, endMonth - 1, startday);
            newStartTime = scl.getTimeInMillis();
            endTime = ecl.getTimeInMillis();
            // 如果startHour < endHour
            if (startHour > endHour) {
                // 如：2010-10-10:08------2010-10-18:02
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000));
                // 如：2010-10-10:24------2010-10-18:00
                if (startHour == 24 && endHour == 0) {
                    dayCount = dayCount - 1;
                }
                // 如果startHour = endHour，如：2010-10-10:08------2010-10-18:08
            } else if (startHour == endHour) {
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000));
                // 如果startHour < endHour，如：2010-10-10:02------2010-10-18:08
            } else {
                dayCount = (int) ((endTime - newStartTime) / (24 * 60 * 60 * 1000) + 1);
            }
            // 去除最后一个月的天数
            if (endMonth == 1 || endMonth == 3 || endMonth == 5 || endMonth == 7 || endMonth == 8 || endMonth == 10
                    || endMonth == 12) {
                dayCount = dayCount % 31;
            } else if (endMonth == 4 || endMonth == 6 || endMonth == 9 || endMonth == 11) {
                dayCount = dayCount % 30;
            } else if (endMonth == 2 && endYear % 4 == 0 && endYear % 10 != 0) {
                dayCount = dayCount % 29;
            } else if (endMonth == 2) {
                dayCount = dayCount % 28;
            }
        }
        return dayCount;
    }

    /**
     * 根据起始日期、终止日期计算月数
     */
    @SuppressWarnings("deprecation")
	public static double getTLGMonthsCount(Date startDate, int startHour, Date endDate, int endHour) {
        // 年份差
        int yearDiff = endDate.getYear() - startDate.getYear();
        // 月份差
        int monthDiff = endDate.getMonth() - startDate.getMonth();
        // 总的月数量
        double monthCount = monthDiff + yearDiff * 12;
        // 天数差
        long dayDiff = endDate.getDate() - startDate.getDate();
        // 不足一个月按半个月计算
        if (dayDiff > 0 || (dayDiff == 0 && endHour > startHour)) {
            monthCount = monthCount + 0.5;
        }

        return monthCount;

    }

    /**
     * 根据起始日期、终止日期计算月数
     */
    public static double getTLGMonthsCount(Date startDate, Date endDate) {
        return getTLGMonthsCount(startDate, 12, endDate, 12);
    }

    /**
     * 得到当前日期-格式YYYY-MM-dd 返回String类型
     */
    @SuppressWarnings("unused")
	public static String getNowDateString() throws Exception {
        String dateString = "";
        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = Calendar.getInstance().get(Calendar.MONTH) + "";
        Integer monthTemp = Integer.parseInt(month) + 1;
        String monthReslut = "";
        if (monthTemp < 10) {
            monthReslut = "0" + (monthTemp + "");
        } else {
            monthReslut = monthTemp + "";
        }
        String day = Calendar.getInstance().get(Calendar.DATE) + "";
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        String hour = Calendar.getInstance().get(Calendar.HOUR) + "";
        String minitus = Calendar.getInstance().get(Calendar.MINUTE) + "";
        String second = Calendar.getInstance().get(Calendar.SECOND) + "";
        // String milliSecond =
        // Calendar.getInstance().get(Calendar.MILLISECOND)+"";
        dateString = year + "年" + monthReslut + "月" + day + "日";
        return dateString;
    }

    /**
     * 得到当前日期-格式YYYY-MM-dd 返回String类型
     */
    public static String getStrNowDate(String pattern) throws Exception {
        String dateString = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        dateString = sdf.format(date);
        return dateString;
    }

    /**
     * 得到当前日期-格式YYYY-MM-dd 返回Date类型
     */
    @SuppressWarnings("unused")
	public static Date getNowDate() throws Exception {
        Date date = null;
        String dateString = "";
        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = Calendar.getInstance().get(Calendar.MONTH) + "";
        Integer monthTemp = Integer.parseInt(month) + 1;
        String monthReslut = "";
        if (monthTemp < 10) {
            monthReslut = "0" + (monthTemp + "");
        } else {
            monthReslut = monthTemp + "";
        }
        String day = Calendar.getInstance().get(Calendar.DATE) + "";
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        String hour = Calendar.getInstance().get(Calendar.HOUR) + "";
        String minitus = Calendar.getInstance().get(Calendar.MINUTE) + "";
        String second = Calendar.getInstance().get(Calendar.SECOND) + "";
        // String milliSecond =
        // Calendar.getInstance().get(Calendar.MILLISECOND)+"";
        dateString = year + "-" + monthReslut + "-" + day;
        date = strToDate(dateString);
        return date;
    }

    /**
     * 处理格林日期的方法
     */
    public static String GreenDatesToStr(String greenDates, String pattern) throws Exception {
        String date = "";
        if (!StringTools.isBlank(greenDates)) {
            int s = greenDates.indexOf("CST");
            if (s > -1) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                Date d = sdf.parse(greenDates);
                sdf = new SimpleDateFormat(pattern);
                date = sdf.format(d);

            }
        }
        return date;
    }

    public static String formatTime(long spend) {
        String p = "%dh %dm %ds %dms";
        long h = spend / 3600000;
        long m = (spend % 3600000) / 60000;
        long s = ((spend % 3600000) % 60000) / 1000;
        long ms = ((spend % 3600000) % 60000) % 1000;

        return String.format(p, h, m, s, ms);
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(TIMES_PATTERN);
        return simpleDateTimeFormat.format(date);
    }
    
    public static String getWeek(Date date){
    	if(date == null){
    		return "";
    	}
    	String[] str = {"日","一","二","三","四","五","六"};
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date); 
    	int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
    	return str[intWeek];
    }
    
    /**
	 * 得到距离一天结束的秒数
	 */
	public static Integer getUntilDayEndSeconds() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date nowDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.add(Calendar.DATE, 1);
			Date tempDate;
			tempDate = sdf.parse(sdf.format(calendar.getTime()));
			return (int) ((tempDate.getTime() - nowDate.getTime()) / 1000);
		} catch (ParseException e) {
			logger.error("格式化时间失败",e);
		}
		return 0;
	}

    public static void main(String[] args) {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	Date date = sdf.parse("2015-07-30");
        	Date date1 = sdf.parse("2016-08-01");
        	System.out.println(getWeek(date));
        	
        	System.out.println(daysBetween(date,date1));
        } catch (Exception e) {
        	logger.error("格式化时间失败",e);
        }
    }
}
