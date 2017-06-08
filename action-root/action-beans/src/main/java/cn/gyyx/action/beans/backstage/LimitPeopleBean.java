package cn.gyyx.action.beans.backstage;

public class LimitPeopleBean {
	private int code;
	//开项报告Code
	private int limitCode;
	//人员Id
	private int personId;
	//人员Name
	private String personName;
	private int deleteFlag;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getLimitCode() {
		return limitCode;
	}
	public void setLimitCode(int limitCode) {
		this.limitCode = limitCode;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
