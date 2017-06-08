package cn.gyyx.playwd.agent;

import java.net.URLEncoder;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import cn.gyyx.core.DataFormat;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.agent.ActionModuleSendGiftToGameResultBean;
import cn.gyyx.playwd.utils.DateTools;
import cn.gyyx.playwd.utils.HttpTools;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月14日 下午1:26:27
 * 描        述：调用action.module.gyyx.cn的接口
 */
@Component
public class ActionModuleAgent {
	Logger logger = GYYXLoggerFactory.getLogger(getClass());
	
	private static final String DOMAIN = "action.module.gyyx.cn";
	private static final String SERVER_URL = "http" + "://" + DOMAIN;
	private static final String KEY = "123456";
	
	//发送虚拟物品到游戏
	private static final String requestUrl_01 = "/V1/ToGame/ClientGameViaAccount";
	
	/**
	 * 发送礼包到游戏
	 * @param classification
	 * @return
	 * @throws Exception
	 */
	public ActionModuleSendGiftToGameResultBean sendGiftToGame(String account,String giftPackage,String memo,
			int gameId,String serverIp,int serverPort,String sourceType,String orderId){
		try {
			String expiredTime = DateTools.formatDate(
					DateTools.getNextNYear(new Date(), 1),
					"yyyyMMddHHmmss");
			long timestamp = System.currentTimeMillis() / 1000L;
			String url = requestUrl_01 + "?account=" + account + "&expiredTime=" + expiredTime + 
					"&GameId=" + gameId + "&giftPackage=" + giftPackage + "&memo=" + memo + 
					"&orderId=" + orderId + "&ServerIP=" + serverIp + "&ServerPort=" + serverPort + 
					"&sourceType=" + sourceType + "&timestamp=" + timestamp;
			String md5 = MD5.encode(url + KEY);
			String signUrl = SERVER_URL + requestUrl_01 + "?account=" + account + "&expiredTime=" + expiredTime + 
					"&GameId=" + gameId + "&giftPackage=" + URLEncoder.encode(giftPackage, "UTF-8") + 
					"&memo=" + URLEncoder.encode(memo, "UTF-8") + 
					"&orderId=" + orderId + "&ServerIP=" + serverIp + "&ServerPort=" + serverPort + 
					"&sourceType=" + sourceType + "&timestamp=" + timestamp + "&sign=" + md5 + "&sign_type=MD5";
			logger.info("调用接口发送请求开始["+DOMAIN+"]:sendGiftToGame,请求链接:"+signUrl,"请求方式:get");
			String response = HttpTools.get(signUrl);
			logger.info("调用接口发送请求结束["+DOMAIN+"]:sendGiftToGame,返回值:"+response);
			ActionModuleSendGiftToGameResultBean result = DataFormat.jsonToObj(response, ActionModuleSendGiftToGameResultBean.class);
			return result;
		} catch (Exception e) {
			logger.error("请求接口：["+DOMAIN+"]sendGiftToGame接口异常",e);
			ActionModuleSendGiftToGameResultBean bean = new ActionModuleSendGiftToGameResultBean();
			bean.setSuccess(false);
			bean.setMessage(e.getMessage());
			return bean;
		}
	}

}
