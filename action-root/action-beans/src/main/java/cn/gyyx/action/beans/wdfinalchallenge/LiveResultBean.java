package cn.gyyx.action.beans.wdfinalchallenge;

public class LiveResultBean<T> {
	private boolean success;
	private String status;
	private T data;
	
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
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
