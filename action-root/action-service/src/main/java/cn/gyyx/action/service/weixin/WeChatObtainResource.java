package cn.gyyx.action.service.weixin;

import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import cn.gyyx.action.beans.weixin.dictionary.ObtainResourceDictionary;
import cn.gyyx.action.beans.weixin.related.PictureSourceBean;
import cn.gyyx.action.beans.weixin.related.ResourceReturnBean;
import cn.gyyx.action.beans.weixin.related.TokenSuccessBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 获取微信服务器中的临时资源
 * @author matao
 *
 */
public class WeChatObtainResource {
	private static final Logger LOG = GYYXLoggerFactory.getLogger(WeChatObtainResource.class);
	/**
	 * 获取临时素材
	 * @param mediaId  媒体文件ID</br>
	 * @param type 在数据库中唯一表示 appid 与 secret （weixin_sysconfig_tb）</br>
	 * @param group 表示资源所在服务器文件夹（不可随意填写，需先在服务器端配置）</br>
	 */
	public ResourceReturnBean obtainResourceURL(String type,String mediaId,PictureSourceBean para){
		LOG.debug("start obtain resource!");
		ResourceReturnBean resultBean = new ResourceReturnBean("","");
		WeChatToken wechatToken = new WeChatToken();
		TokenSuccessBean token = wechatToken.wechatMakeToken(type);
		long exeTimes = System.currentTimeMillis();
		String result = RequestResource.get(gyyxUploadURL(para,token,mediaId));    //0失败  1成功  {"Status":0,"Message":"服务器异常"}
		LOG.debug("obtain resource result:"+result);
		if(result.contains(ObtainResourceDictionary.INTERFACE_RETURN＿STATUS)){
			JSONObject jsonobject = JSONObject.fromObject(result);
			String status = jsonobject.getString(ObtainResourceDictionary.INTERFACE_RETURN＿STATUS);
			String messageM = "";
			if(status.equals("1")){
				messageM = jsonobject.getString(ObtainResourceDictionary.INTERFACE_RETURN＿URL);
			}else{
				messageM = jsonobject.getString(ObtainResourceDictionary.INTERFACE_RETURN＿MSG);
			}
			resultBean.fillAll(status, messageM);
			LOG.debug("obtain times:"+(System.currentTimeMillis()-exeTimes));
			if(status.equals("0")){
				resultBean = tryAgain(mediaId,para,wechatToken);
				LOG.debug("obtain try again times:"+(System.currentTimeMillis()-exeTimes));
			}
		}else{
			resultBean.fillAll("0", "请稍后重试!");
			LOG.warn("interface.up error!");
		}
		LOG.debug("obtain resource :result:"+result);
		LOG.debug("finish obtain resource!");
		return resultBean;
	}
	public ResourceReturnBean obtainResourceURL(String mediaId,PictureSourceBean para,String token){
		LOG.debug("start obtain resource!");
		ResourceReturnBean resultBean = new ResourceReturnBean();
		String result = RequestResource.get(gyyxUploadURL(para,new TokenSuccessBean(token,"7200"),mediaId));
		if(result.contains(ObtainResourceDictionary.INTERFACE_RETURN＿STATUS)){
			JSONObject jsonobject = JSONObject.fromObject(result);
			String status = jsonobject.getString(ObtainResourceDictionary.INTERFACE_RETURN＿STATUS),message = jsonobject.getString(ObtainResourceDictionary.INTERFACE_RETURN＿MSG);
			resultBean.fillAll(status, message);
		}else{
			resultBean.fillAll("1", "请稍后重试!!");
		}
		LOG.debug("obtain resource :"+result);
		LOG.debug("finish obtain resource!");
		return resultBean;
	}
	
	private ResourceReturnBean tryAgain(String mediaId,PictureSourceBean para,WeChatToken wechatToken){
		TokenSuccessBean token = wechatToken.makeToken(para.getType());
		ResourceReturnBean resultBean = new ResourceReturnBean();
		String resultStr = RequestResource.get(gyyxUploadURL(para,token,mediaId));
		if(resultStr.contains(ObtainResourceDictionary.INTERFACE_RETURN＿STATUS)){
			JSONObject jsonobject = JSONObject.fromObject(resultStr);
			String status = jsonobject.getString(ObtainResourceDictionary.INTERFACE_RETURN＿STATUS),message = jsonobject.getString(ObtainResourceDictionary.INTERFACE_RETURN＿MSG);
			resultBean.fillAll(status, message);
		}else{
			resultBean.fillAll("1", "请稍后重试!!");
		}
		return resultBean;
	}
	/**
	 * 获取微信临时素材请求地址
	 * @param token
	 * @param mediaId
	 * @return
	 */
	private String wechatUploadPicURL(TokenSuccessBean token,String mediaId){
		StringBuilder wechatUrl = new StringBuilder(ObtainResourceDictionary.WECHAT_RESOURCE_PATH);
		wechatUrl.append(ObtainResourceDictionary.WECHAT_RESOURCE_TOKEN).append("=").append(token.getAccessToken())
		.append("&").append(ObtainResourceDictionary.WECHAT_RESOURCE_ID).append("=").append(mediaId);
		LOG.debug("request wechat service url :"+wechatUrl.toString());
		return wechatUrl.toString();
	}
	/**
	 * 获取调用GYYX上传地址
	 * @param para
	 * @param token
	 * @param mediaId
	 * @return
	 */
	private String gyyxUploadURL(PictureSourceBean para,TokenSuccessBean token,String mediaId){
		StringBuilder gyyxUrl = new StringBuilder(ObtainResourceDictionary.GYYX_RESOURCE_PATH);
		gyyxUrl.append(ObtainResourceDictionary.GYYX_PARA_GROUP).append("=").append(para.getGroup()).append("&").append(ObtainResourceDictionary.GYYX_PARA_WIDTH).append("=").append(para.getWidth())
		.append("&").append(ObtainResourceDictionary.GYYX_PARA_HEIGHT).append("=").append(para.getHeight())
		.append("&").append(ObtainResourceDictionary.GYYX_PARA_URL).append("=").append(URLEncoder.encode(wechatUploadPicURL(token,mediaId)));
		LOG.debug("request gyyx service url:"+gyyxUrl.toString());
		return gyyxUrl.toString();
	}
}
