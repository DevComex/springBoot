/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：马文斌
 * 联系方式：mawenbin@gyyx.cn 
 * 创建时间：2015年3月11日下午5:52
 * 版本号：v1.0
 * 本类主要用途叙述：Date工具
 */
package cn.gyyx.action.bll.jswswxsign;

import java.text.SimpleDateFormat;
import java.util.Date;



public class DateTools {
	public static final String pattern1 = "yyyy-MM-dd";
	
	public static String formatDate(Date date) {
        return formatDate(date,pattern1);
    }
	
	public static String formatDate(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
	
	/***
     * 计算传入日期的前一天
     * @param date
     *            传入日期
     * @return 传入日期的前一天的日期对象
     */
    public static Date getPreviousDate(Date date) {
        return getPreviousNDate(date, 1);
    }
	
	/***
     * 计算传入日期的后一天
     * @param date
     *            传入日期
     * @return 传入日期的后一天的日期对象
     */
    public static Date getNextDate(Date date) {
        return getNextNDate(date, 1);
    }

	
	/***
     * 计算传入日期的往后顺延N天以后的日期对象
     * @param date
     *            传入的日期对象
     * @param n
     *            往后顺延的天数
     * @return 顺延后的日期对象
     */
    public static Date getPreviousNDate(Date date, long n) {
        long time = date.getTime();
        // 用毫秒数来计算新的日期
        time = time - n * 24 * 60 * 60 * 1000;
        return new Date(time);
    }
    
    /***
     * 计算传入日期的往前推算N天的日期对象
     * @param date
     *            传入的日期对象
     * @param n
     *            往后顺延的天数
     * @return 往前推算后的日期对象
     */
    public static Date getNextNDate(Date date, long n) {
        long time = date.getTime();
        // 用毫秒数来计算新的日期
        time = time + n * 24 * 60 * 60 * 1000;
        return new Date(time);
    }
}
