package cn.gyyx.action.beans.common;

import java.util.Date;

public class ActionCommonSignBean {
	private Integer code;//主键
	private Integer actionCode;
	private String account;//用户账号或其他用户标识
	private Date lastSignDate;//最近签到日期   
	private Integer signNumber;//签到的次数
	private Integer continuousCount;//连续签到总次数
	private String signDateStr;
	
	public Integer getActionCode() {
		return actionCode;
	}
	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}
	public String getSignDateStr() {
		return signDateStr;
	}
	public void setSignDateStr(String signDateStr) {
		this.signDateStr = signDateStr;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public Integer getContinuousCount() {
		return continuousCount;
	}
	public void setContinuousCount(Integer continuousCount) {
		this.continuousCount = continuousCount;
	}
	@Override
	public String toString() {
		return "ActionCommonSignBean [code=" + code + ", actionCode=" + actionCode + ", account=" + account
				+ ", lastSignDate=" + lastSignDate + ", signNumber=" + signNumber + ", continuousCount="
				+ continuousCount + ", signDateStr=" + signDateStr + "]";
	}
}
