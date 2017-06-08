package cn.gyyx.action.beans.common;

import java.util.Date;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年11月28日 下午7:11:11
 * 描        述：
 */
public class ActionUserLoginLog {
	private int code;
	private String account;
	private String userId;
	private String loginId;
	private Date loginTime;
	private String loginIp;
	private int activityCode;
	
	int pageSize;
	int pageIndex;
	String loginTimeStr;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public int getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(int activityCode) {
		this.activityCode = activityCode;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getLoginTimeStr() {
		return loginTimeStr;
	}
	public void setLoginTimeStr(String loginTimeStr) {
		this.loginTimeStr = loginTimeStr;
	}
	
}
