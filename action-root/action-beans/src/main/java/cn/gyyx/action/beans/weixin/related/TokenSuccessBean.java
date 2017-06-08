package cn.gyyx.action.beans.weixin.related;

public class TokenSuccessBean {
	private boolean isSuccess;    //已获取token添加至memcache,判断是否获取成功
	private String accessToken;
	private String expiresIn;
	public TokenSuccessBean(){
		
	}
	
	public TokenSuccessBean(String accessToken, String expiresIn) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}
	public TokenSuccessBean(String accessToken, String expiresIn,boolean isSuccess) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.isSuccess = isSuccess;
	}

	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public boolean isNull(){
		boolean result = true;
		if(accessToken==null){
			result = true;
		}else{
			result = false;
		}
		return result;
	}
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public void fillMsg(String accessToken,String expiresIn){
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}
	public void fillAll(String accessToken,String expiresIn,boolean isSuccess){
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.isSuccess = isSuccess;
	}
}
