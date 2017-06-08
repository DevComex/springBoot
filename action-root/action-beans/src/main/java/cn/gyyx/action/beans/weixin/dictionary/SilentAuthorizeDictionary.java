package cn.gyyx.action.beans.weixin.dictionary;

public class SilentAuthorizeDictionary {
	public static final String SILENT_AUTHORIZE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	public static final String SILENT_AUTHORIZE_RET_FLAG = "openid";
	public static final String RETURN_ERRCODE = "errcode";
	public static final String RETURN_ERRMSG = "errmsg";
	
	public static final String RETURN_TOKEN = "access_token";
	public static final String RETURN_TIMELIMIT = "expires_in";
	public static final String RETURN_REFRESH_TOKEN = "refresh_token";
	public static final String RETURN_OPENID = "openid";
	public static final String RETURN_SCOP = "scope";
}
