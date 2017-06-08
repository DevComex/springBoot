/*************************************************
       Copyright ©, 2015, GY Game
       Author: huangguoqiang
       Created: 2015年12月09日 
       Note：记录注册前浏览地址及注册地址 实体类
************************************************/
package cn.gyyx.action.beans.common;

import java.util.Date;


public class NoteURLBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 用户id
	*/
	private int userID;
	/** 
	* 用户账号
	*/
	private String account;
	/** 
	* 当前页地址
	*/
	private String nowURL;
	/** 
	* 推荐页地址
	*/
	private String referrerURL;
	/** 
	* 注册时间
	*/
	private Date regTime;
	/** 
	* 用户登陆IP
	*/
	private String drawIP;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNowURL() {
		return nowURL;
	}
	public void setNowURL(String nowURL) {
		this.nowURL = nowURL;
	}
	public String getReferrerURL() {
		return referrerURL;
	}
	public void setReferrerURL(String referrerURL) {
		this.referrerURL = referrerURL;
	}
	
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getDrawIP() {
		return drawIP;
	}
	public void setDrawIP(String drawIP) {
		this.drawIP = drawIP;
	}
	
}