package cn.gyyx.action.beans.common;

import java.util.Date;

public class ActionCommonSignLogBean {
	private Integer code;//主键
	private Integer sid;//签到主表ID
	private String account;//用户标识
	private Date signTime;//签到时间
	private int actionCode;//活动ID
	private String remark; //标注
	
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
}
