package cn.gyyx.action.beans.common;


public class ActionCommonFormBean {
	public static interface Codeable {
		public void setCode(int code);
		public int getCode();
	}
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 活动代码
	*/
	private int actionCode;
	/**
	 * 上传者
	 */
	private int accountCode;
	/** 
	* json表单
	*/
	private String jsonStr;
	public void setCode(int code){
 		this.code = code;
	}
	public int getCode(){
		return this.code;
	}
	public void setActionCode(int actionCode){
 		this.actionCode = actionCode;
	}
	public int getActionCode(){
		return this.actionCode;
	}
	public int getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(int accountCode) {
		this.accountCode = accountCode;
	}
	public void setJsonStr(String jsonStr){
 		this.jsonStr = jsonStr;
	}
	public String getJsonStr(){
		return this.jsonStr;
	}
}