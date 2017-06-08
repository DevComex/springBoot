package cn.gyyx.action.beans.weixin.related;

public class SilentAuthorizeBean {
	private String token;
	private String timeLimit;
	private String refreshToken;
	private String openid;
	private String scope;
	
	
	public SilentAuthorizeBean() {
	}
	public SilentAuthorizeBean(String token, String timeLimit,
			String refreshToken, String openid, String scope) {
		this.token = token;
		this.timeLimit = timeLimit;
		this.refreshToken = refreshToken;
		this.openid = openid;
		this.scope = scope;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
}
