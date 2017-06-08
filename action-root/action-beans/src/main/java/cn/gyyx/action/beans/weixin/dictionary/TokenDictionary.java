package cn.gyyx.action.beans.weixin.dictionary;

public class TokenDictionary {
	//微信相关：----------------------------------------------------------------------------------------------------------------
	public static final String TOKEN_PATH="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	public static final String TOKEN_PARA_APPID="appid" ;
	public static final String TOKEN_PARA_SECRET="secret";
	public static final String TOKEN_RETURN_TOKENAME="access_token";
	public static final String TOKEN_RETURN_LIMITTIME="expires_in";
	//memcache相关：----------------------------------------------------------------------------------------------------------------
	public static final String TOKEN_NAMW_CACHE="OldValidateCodeCache2";
	//public static final String TOKEN_KEYNAME_CACHE="weixin$token" ;
	public static final String TOKEN_KEYNAME_CACHE="weixin$%s$token" ;//1229改成按照type
}
