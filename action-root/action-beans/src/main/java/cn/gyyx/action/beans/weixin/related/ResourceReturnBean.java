package cn.gyyx.action.beans.weixin.related;

public class ResourceReturnBean {
	private String source;    //状态 1成功 0失败
	private String message;    //信息
	
	
	public ResourceReturnBean() {
	}
	public ResourceReturnBean(String source, String message) {
		this.source = source;
		this.message = message;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void fillAll(String source, String message){
		this.source = source;
		this.message = message;
	}
	
}
