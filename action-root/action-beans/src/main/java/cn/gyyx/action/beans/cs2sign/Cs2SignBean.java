package cn.gyyx.action.beans.cs2sign;

import java.util.Date;

public class Cs2SignBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 签到日期
	*/
	private Date signDate;
	/** 
	* 关联信息账号
	*/
	private String account;
	/** 
	* 设置 主键
	*/
	public void setCode(int code){
 		this.code = code;
	}
	/** 
	* 获得 主键
	*/
	public int getCode(){
		return this.code;
	}
	/** 
	* 设置 签到日期
	*/
	public void setSignDate(Date signDate){
 		this.signDate = signDate;
	}
	/** 
	* 获得 签到日期
	*/
	public Date getSignDate(){
		return this.signDate;
	}
	/** 
	* 设置 关联信息主键
	*/
	public void setAccount(String account){
 		this.account = account;
	}
	/** 
	* 获得 关联信息主键
	*/
	public String getAccount(){
		return this.account;
	}
}