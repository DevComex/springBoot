package cn.gyyx.action.beans.mobile.website;

public class InterfaceReturnBean {
	private int httpStatus;
	private String message;
	
	public InterfaceReturnBean(){
		
	}
	public InterfaceReturnBean(int httpStatus,String message){
		this.httpStatus = httpStatus;
		this.message = message;
	}
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void fillAll(int httpStatus,String message){
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
