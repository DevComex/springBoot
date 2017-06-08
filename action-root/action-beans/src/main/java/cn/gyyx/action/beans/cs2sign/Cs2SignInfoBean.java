package cn.gyyx.action.beans.cs2sign;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Cs2SignInfoBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 上次签到日期
	*/
	private Date signLast;
	/** 
	* 连续签到天数
	*/
	private int signContinuity;
	/** 
	* 用户账号
	*/
	private String account;
	/** 
	* 用户账号code
	*/
	private int userCode;
	
	/**
	 * 绑定信息
	 */
	private List<Cs2BindInfoBean> bind;
	/**
	 * 今日是否签到 - 非持久化项
	 */
	private boolean signToday = false;
	
	public boolean isSignToday() {
		return signToday;
	}
	public void setSignToday(boolean signToday) {
		this.signToday = signToday;
	}
	public List<Cs2BindInfoBean> getBind() {
		return bind == null?new LinkedList<Cs2BindInfoBean>():bind;
	}
	public void setBind(List<Cs2BindInfoBean> bind) {
		this.bind = bind;
	}
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
	* 设置 上次签到日期
	*/
	public void setSignLast(Date signLast){
 		this.signLast = signLast;
	}
	/** 
	* 获得 上次签到日期
	*/
	public Date getSignLast(){
		return this.signLast;
	}
	/** 
	* 设置 连续签到天数
	*/
	public void setSignContinuity(int signContinuity){
 		this.signContinuity = signContinuity;
	}
	/** 
	* 获得 连续签到天数
	*/
	public int getSignContinuity(){
		return this.signContinuity;
	}
	/** 
	* 设置 用户账号
	*/
	public void setAccount(String account){
 		this.account = account;
	}
	/** 
	* 获得 用户账号
	*/
	public String getAccount(){
		return this.account;
	}
	/** 
	* 设置 用户账号code
	*/
	public void setUserCode(int userCode){
 		this.userCode = userCode;
	}
	/** 
	* 获得 用户账号code
	*/
	public int getUserCode(){
		return this.userCode;
	}
}