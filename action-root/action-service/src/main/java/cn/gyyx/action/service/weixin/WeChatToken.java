package cn.gyyx.action.service.weixin;

import java.util.Date;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import cn.gyyx.action.beans.weixin.dictionary.TokenDictionary;
import cn.gyyx.action.beans.weixin.related.TokenParaBean;
import cn.gyyx.action.beans.weixin.related.TokenSuccessBean;
import cn.gyyx.action.bll.weixin.related.TokenConfigBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.memcache.GyyxStringTranscoder;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;

/**
 * 获取token
 * @author matao
 *
 */
public class WeChatToken {
	private static final Logger LOG = GYYXLoggerFactory.getLogger(WeChatToken.class);
	private TokenConfigBLL tokenConfigBLL = new TokenConfigBLL();
	
	/**
	 * 获取token
	 * @param type 标注数据库中 appid 与 secret （weixin_sysconfig_tb）
	 * @return
	 */
	public synchronized TokenSuccessBean makeToken(String type){
		LOG.debug("start make token!");
		TokenSuccessBean resultBean = getTokenFromCache(type);
		long exeTimes = System.currentTimeMillis();
		String result = RequestResource.get(getTokenURL(type));
		LOG.debug("token result:"+result);
		try{
			JSONObject jsonobject = JSONObject.fromObject(result);
			if(result.contains(TokenDictionary.TOKEN_RETURN_TOKENAME)){
				String token = jsonobject.getString(TokenDictionary.TOKEN_RETURN_TOKENAME);
				resultBean.fillMsg(token, jsonobject.getString(TokenDictionary.TOKEN_RETURN_LIMITTIME));
				if(setTokenToCache(type,token)){
					LOG.debug("success to take token! token:"+token+"--time:"+new Date());
				}else{
					LOG.debug("try again (set token to memcache)!");
					setTokenToCache(type,token);
				}
			}else{
				if(result.contains("45009")){
					LOG.error("serious!!cound not get token from wechat because already achieve limit !");
				}
				LOG.error("get token fail ! errcode:"+jsonobject.getString("errcode")+"---errmsg:"+jsonobject.getString("errmsg"));
				resultBean.fillMsg("", "");
			}
		}catch(Exception e){
			LOG.warn("get token from wechat error!");
			resultBean.fillAll("", "7200", true);
		}
		LOG.debug("make token times:"+(System.currentTimeMillis()-exeTimes));
		LOG.debug("finishe make token!");
		return resultBean;
	}
	/**
	 * 先从memcache上获取在从微信获取
	 * @param type
	 * @return
	 */
	public synchronized TokenSuccessBean wechatMakeToken(String type){
		LOG.debug("get token from wechat started!");
		TokenSuccessBean resultBean = getTokenFromCache(type);
		if(resultBean.getIsSuccess()){
			return resultBean;
		}else{
			resultBean = makeToken(type);
		}
		LOG.debug("get token from wechat finished!");
		return resultBean;
	}
	
	
//----------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 向memcache中添加值
	 * @param token
	 * @return
	 */
	private boolean setTokenToCache(String type,String token){
		boolean result = new XMemcachedClientAdapter(TokenDictionary.TOKEN_NAMW_CACHE).set(String.format(TokenDictionary.TOKEN_KEYNAME_CACHE, type.toLowerCase()), 600, token,new GyyxStringTranscoder());
		LOG.debug("set memecache result:"+result+"--token:"+token);
		return result;

	}
	/**
	 * 从memcache中获取数据
	 * @return
	 */
	private TokenSuccessBean getTokenFromCache(String type){
		TokenSuccessBean result = new TokenSuccessBean("","7200",false);
		String token = new XMemcachedClientAdapter(TokenDictionary.TOKEN_NAMW_CACHE).get(String.format(TokenDictionary.TOKEN_KEYNAME_CACHE, type.toLowerCase()), new GyyxStringTranscoder());
		LOG.debug("get token from memcache!:"+"*"+token+"*");
		if(null == token||"".equals(token)){
			result.fillAll("", "7200",false);
		}else{
			result.fillAll(token, "7200", true);
		}
		return result;
	}
	
	private String getTokenURL(String type){
		TokenParaBean tokenPara = tokenConfigBLL.getTokenPara(type);
		StringBuilder url = new StringBuilder(TokenDictionary.TOKEN_PATH);
		url.append("&").append(TokenDictionary.TOKEN_PARA_APPID).append("=").append(tokenPara.getAppid())
		.append("&").append(TokenDictionary.TOKEN_PARA_SECRET).append("=").append(tokenPara.getSecret());
		return url.toString();
	}
	
}
