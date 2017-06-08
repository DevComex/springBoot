/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月16日下午3:44:59
 * 版本号：v1.0
 * 本类主要用途叙述：用户抽奖记录bean
 */



package cn.gyyx.action.beans.lottery;

import java.util.Date;

public class UserRecordBean {
	//主键
	private int code;
	//用户主键
	private int userCode;
	//用户账号
	private String userAccount;
	//抽奖ip
	private String ip;
	//活动主键
	private int actionCode;
	//时间
	private Date time;
	//类型
	private String type;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UserRecordBean(int code, int userCode, String userAccount,
			String ip, int actionCode, Date time, String type) {
		this.code = code;
		this.userCode = userCode;
		this.userAccount = userAccount;
		this.ip = ip;
		this.actionCode = actionCode;
		this.time = time;
		this.type = type;
	}
	public UserRecordBean() {
	}
	
	

}
