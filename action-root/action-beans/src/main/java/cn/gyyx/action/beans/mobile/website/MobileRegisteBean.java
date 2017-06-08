package cn.gyyx.action.beans.mobile.website;

import java.util.Date;

public class MobileRegisteBean {
	private int code;
	private String phone;
	private String password;
	private String ip;
	private int isDeleted;
	private int isObtain;    //是否领取奖品
	private Date date;    //注册时间
	private String type;    //注册类型
	
	public MobileRegisteBean(){
		
	}
	
	public MobileRegisteBean(String phone, String password, String ip,
			int isDeleted, int isObtain, Date date, String type) {
		this.phone = phone;
		this.password = password;
		this.ip = ip;
		this.isDeleted = isDeleted;
		this.isObtain = isObtain;
		this.date = date;
		this.type = type;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getIsObtain() {
		return isObtain;
	}
	public void setIsObtain(int isObtain) {
		this.isObtain = isObtain;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
