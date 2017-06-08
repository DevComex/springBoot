package cn.gyyx.action.beans.wdtoplevel;

public class AppointmentBean {
	int code;
	String account;
	
	String area;
	String serverName;
	String characterName;
	String type;
	public AppointmentBean(){}
	public AppointmentBean(AddAppointmentBean addBean){
		this.area = addBean.area;
		this.serverName = addBean.serverName;
		this.characterName = addBean.characterName;
		this.type = addBean.getTypeInString();
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
