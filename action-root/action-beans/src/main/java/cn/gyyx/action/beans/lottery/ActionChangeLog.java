package cn.gyyx.action.beans.lottery;

import java.util.Date;

public class ActionChangeLog {
	private int code;
	private double oldProbability;
	private double newProbability;
	private Date updateDate;
	private String account;
	private int actionCode;
	private int number;
	
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getOldProbability() {
		return oldProbability;
	}
	public void setOldProbability(double oldProbability) {
		this.oldProbability = oldProbability;
	}
	public double getNewProbability() {
		return newProbability;
	}
	public void setNewProbability(double newProbability) {
		this.newProbability = newProbability;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
}
