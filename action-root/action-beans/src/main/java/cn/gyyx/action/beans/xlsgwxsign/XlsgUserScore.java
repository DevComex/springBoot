/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.beans.xlsgwxsign;

import java.util.Date;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能-用户积分表
 */
public class XlsgUserScore {
	private Integer code;//主键
	private String openId;//用户ID
	private Integer curIntergral;//当前积分
	private Integer lastMonthIntergral;//当前积分
	private Date jobLastUpdateTime;//job 最后更新积分时间
	
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
	public Integer getCurIntergral() {
		return curIntergral;
	}
	public void setCurIntergral(Integer curIntergral) {
		this.curIntergral = curIntergral;
	}
	public Integer getLastMonthIntergral() {
		return lastMonthIntergral;
	}
	public void setLastMonthIntergral(Integer lastMonthIntergral) {
		this.lastMonthIntergral = lastMonthIntergral;
	}
	public Date getJobLastUpdateTime() {
		return jobLastUpdateTime;
	}
	public void setJobLastUpdateTime(Date jobLastUpdateTime) {
		this.jobLastUpdateTime = jobLastUpdateTime;
	}
}
