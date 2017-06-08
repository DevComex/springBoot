package cn.gyyx.playwd.beans.agent;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionModuleSendGiftToGameResultBean {
	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsSuccess")
	private boolean success;

	@JsonProperty("Status")
	private String status;

	@JsonProperty("Data")
	private String data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
