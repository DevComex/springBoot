/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月7日
 * @版本号：
 * @本类主要用途描述：日历信息
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

public class CalendarInfoBean {
	// 当月第一天星期
	private Integer weekOfFirstDay;
	// 当月天数
	private Integer monthDays;
	
	private Integer nowDay;
	// 当月签到列表
	private int dayArray[];
	//当月日期
	private String nowdate;

	public Integer getWeekOfFirstDay() {
		return weekOfFirstDay;
	}

	public void setWeekOfFirstDay(Integer weekOfFirstDay) {
		this.weekOfFirstDay = weekOfFirstDay;
	}

	public Integer getMonthDays() {
		return monthDays;
	}

	public void setMonthDays(Integer monthDays) {
		this.monthDays = monthDays;
	}

	public int[] getDayArray() {
		return dayArray;
	}

	public void setDayArray(int[] dayArray) {
		this.dayArray = dayArray;
	}

	public Integer getNowDay() {
		return nowDay;
	}

	public void setNowDay(Integer nowDay) {
		this.nowDay = nowDay;
	}

	public String getNowdate() {
		return nowdate;
	}

	public void setNowdate(String nowdate) {
		this.nowdate = nowdate;
	}
	
	

}
