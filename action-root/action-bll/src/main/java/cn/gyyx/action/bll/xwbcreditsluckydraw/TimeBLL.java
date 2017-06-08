/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月7日
 * @版本号：V1.214
 * @本类主要用途描述：时间计算业务
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class TimeBLL {
	 private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	 /**
	  * 获得当前月--开始日期
	  * @param date
	  * @return
	  */
    public String getMinMonthDate() {   
         Calendar calendar = Calendar.getInstance();   
         calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); 
         return dateFormat.format(calendar.getTime())+" 00:00:00";
    }
    /**
     * 获得当前月--结束日期
     * @param date
     * @return
     */
    public String getMaxMonthDate(){   
         Calendar calendar = Calendar.getInstance();   
         calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
         return dateFormat.format(calendar.getTime())+" 23:59:59";
	}
    
	/**
	 * 获得当前日期与本周一相差的天数
	 * @return
	 */
    private  int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    } 
    /**
     * 获得当前周- 周一的日期
     * @return
     */
    public String getCurrentMonday() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        String preMonday = dateFormat.format(monday)+" 00:00:00";
        return preMonday;
    }
    
    /**
     * 获得当前周- 周日  的日期
     * @return
     */
    public String getPreviousSunday() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus +6);
        Date monday = currentDate.getTime();
        String preMonday = dateFormat.format(monday)+" 23:59:59";
        return preMonday;
    }
    /**
     * 获得当月的天数
     * @return
     */
    public int getMonthDays(){
    	Calendar c = Calendar.getInstance();
    	int year =c.get(Calendar.YEAR); ;
    	int month =  c.get(Calendar.MONTH) + 1; 
    	Calendar c1 = Calendar.getInstance();
		c1.set(year, month - 1, 1);
		Calendar c2 = Calendar.getInstance();
		c2.set(year, month, 1);
		long c2s = c2.getTimeInMillis();
		long c1s = c1.getTimeInMillis();
		long day = 1 * 24 * 60 * 60 * 1000;
		long days = (c2s - c1s) / day;
		return (int)days;
    }
}
