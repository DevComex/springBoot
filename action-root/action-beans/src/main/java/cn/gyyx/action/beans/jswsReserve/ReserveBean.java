/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2016年1月20日
 * 版本号：v1.0
 * 本类主要用途叙述：绝世武神预约分享-用户预约信息
 */
package cn.gyyx.action.beans.jswsReserve;

import java.util.Date;

public class ReserveBean {

	private int code;
	private String phoneNum;
	private Date reserveTime;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getPhone_num() {
		return phoneNum;
	}
	public void setPhone_num(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Date getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(Date reserveTime) {
		this.reserveTime = reserveTime;
	}
	
	@Override
	public String toString() {
		return "ReserveBean [code=" + code + ", phoneNum=" + phoneNum
				+ ", reserveTime=" + reserveTime + "]";
	}
	
}
