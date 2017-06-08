package cn.gyyx.action.beans.userregistlog;

import java.util.Date;

public class UserRegistLogBean {
	private int code;
	private String accountName;//账号名称
	private String accountType;//账号类型
	private String registSource;//注册来源 活动ID
	private Date createDate;    //注册时间
	
	public UserRegistLogBean(){
		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getRegistSource() {
		return registSource;
	}

	public void setRegistSource(String registSource) {
		this.registSource = registSource;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
