package cn.gyyx.action.beans.wdtombthreeteam;

import java.util.Date;

public class TombLoginInfoBean {


	 //主键编号
	 private int code;   
	 //活动编号
	 private int actionCode; 
	
	 //投票人电话
	 private String votterPhone;
	 
	 
	 private Date createTime;
	 
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	
	
	public String getVotterPhone() {
		return votterPhone;
	}
	public void setVotterPhone(String votterPhone) {
		this.votterPhone = votterPhone;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
}
