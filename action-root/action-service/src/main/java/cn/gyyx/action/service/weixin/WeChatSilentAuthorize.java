package cn.gyyx.action.service.weixin;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.weixin.dictionary.SilentAuthorizeDictionary;
import cn.gyyx.action.beans.weixin.related.SilentAuthorizeBean;
import cn.gyyx.action.beans.weixin.related.TokenParaBean;
import cn.gyyx.action.bll.weixin.related.TokenConfigBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 静默授权
 * @author matao
 *
 */
public class WeChatSilentAuthorize {
	private static final Logger LOG = GYYXLoggerFactory.getLogger(WeChatSilentAuthorize.class);
	private TokenConfigBLL tokenConfigBLL = new TokenConfigBLL();
	/**
	 * 通过code获取Openid
	 * @param code 静默授权时微信返回
	 * @return
	 */
	public ResultBean<SilentAuthorizeBean> getOpenidByCode(String code,String type){
		LOG.debug("start to get openid by code !");
		ResultBean<SilentAuthorizeBean> resultBean = new ResultBean<>();
		String result = RequestResource.get(getOpenidByCodeURL(type, code));
		LOG.debug("get openid by code :"+result);
		if(result.contains(SilentAuthorizeDictionary.SILENT_AUTHORIZE_RET_FLAG)){
			JSONObject json = JSONObject.fromObject(result);
			resultBean.setProperties(true, "true", new SilentAuthorizeBean(
					json.getString(SilentAuthorizeDictionary.RETURN_TOKEN),json.getString(SilentAuthorizeDictionary.RETURN_TIMELIMIT)
				   ,json.getString(SilentAuthorizeDictionary.RETURN_REFRESH_TOKEN),json.getString(SilentAuthorizeDictionary.RETURN_OPENID)
				   ,json.getString(SilentAuthorizeDictionary.RETURN_SCOP)));
		}else{
			resultBean.setProperties(false, "false", null);
			LOG.error("get openid by code erro!errcode:"+result);
		}
		LOG.debug("finish to get openid by code !");
		return resultBean;
	}
	
	private String getOpenidByCodeURL(String code,String type){
		TokenParaBean tokenPara = tokenConfigBLL.getTokenPara(type);
		String url =  String.format(SilentAuthorizeDictionary.SILENT_AUTHORIZE_URL, tokenPara.getAppid(),tokenPara.getSecret(),code);
		LOG.debug("get openid by code -- url:"+url);
		return url;
	}
	
}
