package cn.gyyx.action.beans.common;

import java.util.Date;

public class ActionWXjifenChangeLogBean {

	private int code;
	private int actionCode;
	private String openId;
	private int jifen;
	private String changeType;
	private Date changeTime;
	private String remark;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getAction_code() {
		return actionCode;
	}
	public void setAction_code(int action_code) {
		this.actionCode = action_code;
	}
	public String getOpenid() {
		return openId;
	}
	public void setOpenid(String openid) {
		this.openId = openid;
	}
	public int getJifen() {
		return jifen;
	}
	public void setJifen(int jifen) {
		this.jifen = jifen;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ActionWXjifenChangeLogBean [code=" + code + ", actionCode=" + actionCode + ", openId=" + openId
				+ ", jifen=" + jifen + ", changeType=" + changeType + ", changeTime=" + changeTime + ", remark="
				+ remark + "]";
	}
	
}
