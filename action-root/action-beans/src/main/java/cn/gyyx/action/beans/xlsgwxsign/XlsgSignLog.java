/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.beans.xlsgwxsign;

import java.util.Date;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能-签到日志表
 */
public class XlsgSignLog {
	private Integer code;//主键
	private Integer sid;//签到主表ID
	private String openId;//用户ID
	private Date signTime;//签到时间
	private Integer obtainedIntegral;//获取的积分值
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public Integer getObtainedIntegral() {
		return obtainedIntegral;
	}
	public void setObtainedIntegral(Integer obtainedIntegral) {
		this.obtainedIntegral = obtainedIntegral;
	}
}
