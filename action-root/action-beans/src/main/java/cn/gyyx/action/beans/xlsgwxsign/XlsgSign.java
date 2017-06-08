/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.beans.xlsgwxsign;

import java.util.Date;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能-签到主表
 */
public class XlsgSign {
	private Integer code;//主键
	private String openId;//用户ID
	private Date lastSignDate;//最近签到日期 
	private Integer signNumber;//连续签到的次数
	private Integer lastMonthSignNumber;//保留上月定时清0的历史
	private Date lastMonthUpdateTime;//记录更新时间
	
	private String signDateStr;//字符串时间
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getLastSignDate() {
		return lastSignDate;
	}
	public void setLastSignDate(Date lastSignDate) {
		this.lastSignDate = lastSignDate;
	}
	public Integer getSignNumber() {
		return signNumber;
	}
	public void setSignNumber(Integer signNumber) {
		this.signNumber = signNumber;
	}
	public String getSignDateStr() {
		return signDateStr;
	}
	public void setSignDateStr(String signDateStr) {
		this.signDateStr = signDateStr;
	}
	public Integer getLastMonthSignNumber() {
		return lastMonthSignNumber;
	}
	public void setLastMonthSignNumber(Integer lastMonthSignNumber) {
		this.lastMonthSignNumber = lastMonthSignNumber;
	}
	public Date getLastMonthUpdateTime() {
		return lastMonthUpdateTime;
	}
	public void setLastMonthUpdateTime(Date lastMonthUpdateTime) {
		this.lastMonthUpdateTime = lastMonthUpdateTime;
	}
}
