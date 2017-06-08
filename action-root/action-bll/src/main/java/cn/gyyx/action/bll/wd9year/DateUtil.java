/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：马文斌
 * 联系方式：mawenbin@gyyx.cn 
 * 创建时间：2015年3月11日下午5:52
 * 版本号：v1.0
 * 本类主要用途叙述：Date工具
 */
package cn.gyyx.action.bll.wd9year;

import java.util.Calendar;
import java.util.Date;

/**
 * Date工具
 * */
public class DateUtil {
	/**
	 * 获取当前时间的前（后）几天
	 * @param	days 天数
	 * */
	public static Date getDate(int days){
		if(days == 0){
			return new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	/**
	 * 设置金天的时间
	 * */
	public static Date setTodayTime(int hour, int minute, int second){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}
}
