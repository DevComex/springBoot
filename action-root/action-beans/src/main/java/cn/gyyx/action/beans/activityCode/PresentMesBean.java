package cn.gyyx.action.beans.activityCode;

public class PresentMesBean {
	private int code ;
	private String actionCode;    //活动编号
	private String presentMes;    //礼物码
	private String importType;    //类型：手机号 、邮箱 ....
	private String importMessage;    //输入的内容
	private boolean isReceive;    //是否被领取
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getPresentMes() {
		return presentMes;
	}
	public void setPresentMes(String presentMes) {
		this.presentMes = presentMes;
	}
	public String getImportType() {
		return importType;
	}
	public void setImportType(String importType) {
		this.importType = importType;
	}
	public String getImportMessage() {
		return importMessage;
	}
	public void setImportMessage(String importMessage) {
		this.importMessage = importMessage;
	}
	public boolean isReceive() {
		return isReceive;
	}
	public void setReceive(boolean isReceive) {
		this.isReceive = isReceive;
	}
	
	
}
