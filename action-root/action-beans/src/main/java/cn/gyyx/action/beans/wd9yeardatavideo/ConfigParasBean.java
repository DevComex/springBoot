package cn.gyyx.action.beans.wd9yeardatavideo;

public class ConfigParasBean {
	private int code;
	private String itemDesc;
	private String errorCode;
	public ConfigParasBean(){
		
	}
	public ConfigParasBean( String errorCode ){
		this.errorCode = errorCode;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
