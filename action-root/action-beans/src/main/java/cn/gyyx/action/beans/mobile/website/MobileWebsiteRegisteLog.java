package cn.gyyx.action.beans.mobile.website;

public class MobileWebsiteRegisteLog {
	private int code;
	private String phone;
	private int registeCode;
	
	
	public MobileWebsiteRegisteLog() {
	}
	public MobileWebsiteRegisteLog(String phone, int registeCode) {
		this.phone = phone;
		this.registeCode = registeCode;
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
	public int getRegisteCode() {
		return registeCode;
	}
	public void setRegisteCode(int registeCode) {
		this.registeCode = registeCode;
	}
	
}
