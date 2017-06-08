package cn.gyyx.action.beans.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerBean {
	@JsonProperty("status")
	private String status;
	@JsonProperty("data")
	private ServerMessage [] data;
	
	
	
	public ServerBean() {
	}
	
	
	public ServerBean(String status) {
		this.status = status;
	}


	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ServerMessage[] getData() {
		return data;
	}
	public void setData(ServerMessage[] data) {
		this.data = data;
	}


	
}
