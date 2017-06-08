package cn.gyyx.action.beans.cs2sign;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerMessage {
	@JsonProperty("Code")
	private String code;
	@JsonProperty("ServerName")
	private String serverName;
	@JsonProperty("IsOpen")
	private String isOpen;
	@JsonProperty("ServerIp")
	private String serverIp;
	@JsonProperty("ServerPort")
	private String serverPort;
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
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	
	
	
}
