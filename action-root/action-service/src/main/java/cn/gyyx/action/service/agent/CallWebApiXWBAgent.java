/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月14日
 * @版本号：V1.214
 * @本类主要用途描述:调取发送物品接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.agent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.RemoteException;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SendPrizeResult;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class CallWebApiXWBAgent {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CallWebServiceAgent.class);
	/**
	 * 发送炫舞吧物品
	 * @param gameId
	 * @param userInfo
	 * @param giftPackage
	 * @param expiredTime
	 * @param memo
	 * @param serverInfo
	 * @return
	 * @throws RemoteException
	 */
	public static SendPrizeResult giveXWBPresents(int gameId, String account,
			String giftPackage, String expiredTime, String memo,
			ServerInfoBean serverInfo) throws RemoteException {
		SendPrizeResult sendPrizeResult = null;
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String sign = getMD5Sign(gameId,account,
				giftPackage, expiredTime, memo,
				serverInfo,
				 timestamp);

	  String url=null;
	try {
		url = "http://action.module.gyyx.cn/V1/ToGame/ClientGameViaAccount?account=" + account
				+ "&giftPackage=" + URLEncoder.encode(giftPackage,"utf-8") + "&expiredTime=" + expiredTime+ "&memo=" + URLEncoder.encode(memo,"utf-8") + 
				"&GameID=" + gameId+ "&ServerIP=" + serverInfo.getValue().getServerIp() + "&ServerPort=" 
				+ serverInfo.getValue().getServerPort()+ "&timestamp=" + timestamp
						+ "&sign_type=" + sign_type + "&sign=" + sign;
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}

		logger.info("sign:"+sign);
		logger.info("url1:"+ url);
		//url = url.replace("#", "%23");
		
		logger.info("url2:"+ url);
		RestClient client = new RestClient();
		org.apache.wink.client.Resource resource = client.resource(url);
		ClientResponse response = resource.get();
		if (response != null) {
			String result = response.getEntity(String.class);
			logger.info("result:"+result);
			net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(result);
			String message = jsonobject.getString("Message");
			String status = jsonobject.getString("Status");
			String isSuccess = jsonobject.getString("IsSuccess");
			sendPrizeResult = new SendPrizeResult();
			sendPrizeResult.setMessage(message);
			sendPrizeResult.setStatus(status);
			sendPrizeResult.setSuccess(Boolean.parseBoolean(isSuccess));
		}
		return sendPrizeResult;
	}
	private static String getMD5Sign(int gameId, String account,
			String giftPackage, String expiredTime, String memo,
			ServerInfoBean serverInfo,
			long timestamp) {
		
		String integtationURL ="/V1/ToGame/ClientGameViaAccount?account="+account+"&expiredTime="+expiredTime+"&GameID="+gameId;
		
		if(giftPackage != null && !"".equals(giftPackage)){
			integtationURL = integtationURL + "&giftPackage="+giftPackage;
		}
		if(memo != null && !"".equals(memo) ){
			integtationURL = integtationURL + "&memo="+memo;
		}
		if(serverInfo.getValue().getServerIp() != null && !"".equals(serverInfo.getValue().getServerIp())){
			integtationURL = integtationURL + "&ServerIP="+serverInfo.getValue().getServerIp();
		}
		if(serverInfo.getValue().getServerPort() != null && !"".equals(serverInfo.getValue().getServerPort())){
			integtationURL = integtationURL + "&ServerPort="+serverInfo.getValue().getServerPort();
		}
		integtationURL = integtationURL + "&timestamp="+timestamp+ "123456";
		logger.info("integtationURL:"+integtationURL);
	
		return MD5.encode(integtationURL);
	}
}
