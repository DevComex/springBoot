package cn.gyyx.action.beans.activityCode;

public class PresentLogParaBean {
	private String actionCode="";
	private int actionCodeInt;
	private String account;    //账号就是..输入的手机号..或者邮箱..或者其他的东西
	private String ip;
	private PresentMesBean presengMes;
	
	public PresentLogParaBean() {
	}
	public PresentLogParaBean(String actitvityCode, String account) {
		this.actionCode = actitvityCode;
		this.account = account;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public PresentMesBean getPresengMes() {
		return presengMes;
	}
	public void setPresengMes(PresentMesBean presengMes) {
		this.presengMes = presengMes;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public int getActionCodeInt() {
		if(actionCode.equals("")){
			return actionCodeInt;
		}else{
			this.actionCodeInt = Integer.parseInt(this.actionCode);
			return this.actionCodeInt;
		}
	}
	public void setActionCodeInt(int activityCodeInt ) {
		this.actionCodeInt = activityCodeInt;
	}
}
