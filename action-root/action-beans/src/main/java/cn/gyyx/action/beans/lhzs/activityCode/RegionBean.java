package cn.gyyx.action.beans.lhzs.activityCode;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionBean {
	@JsonProperty("IsSuccess")
	private String isSuccess = "";
	@JsonProperty("Message")
	private String message = "";
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
