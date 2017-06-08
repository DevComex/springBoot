package cn.gyyx.playwd.beans.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerMessage {
	@JsonProperty("Code")
	private String code;
	@JsonProperty("ServerName")
	private String serverName;
	@JsonProperty("IsOpen")
	private String isOpen;
	
	
	public ServerMessage() {
		
	}
	public ServerMessage(String code, String serverName, String isOpen) {
		this.code = code;
		this.serverName = serverName;
		this.isOpen = isOpen;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	
}
