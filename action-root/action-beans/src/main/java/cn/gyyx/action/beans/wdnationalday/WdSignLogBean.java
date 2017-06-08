package cn.gyyx.action.beans.wdnationalday;

import java.util.Date;

public class WdSignLogBean {
   private int code;
   private int actionCode;
   private int  prizesCode;
   private int userId;
   private Date sendTime;
   private String remark ;
   private int prizesStatus;
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
public int getPrizesCode() {
	return prizesCode;
}
public void setPrizesCode(int prizesCode) {
	this.prizesCode = prizesCode;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public Date getSendTime() {
	return sendTime;
}
public void setSendTime(Date sendTime) {
	this.sendTime = sendTime;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public int getPrizesStatus() {
	return prizesStatus;
}
public void setPrizesStatus(int prizesStatus) {
	this.prizesStatus = prizesStatus;
}
   
   
}
