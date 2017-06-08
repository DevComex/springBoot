package cn.gyyx.playwd.agent;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.utils.HttpTools;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月14日 下午1:26:27
 * 描        述：调用interface.account.gyyx.cn的接口
 */
@Component
public class InterfaceAccountAgent {
	Logger logger = GYYXLoggerFactory.getLogger(getClass());
	
	private static final String DOMAIN = "interface.account.gyyx.cn";
	private static final String SERVER_URL = "http" + "://" + DOMAIN;
	private static final String KEY = "123456";
	
	//是否激活服务器
	private static final String requestUrl_01 = "/game/activelog/2/{0}/{1}/{2}/{3}";
	
	/**
	 * 是否激活服务器
	 */
	public Map<String,String> isActive(String account,int serverCode,
			String serverIp,String serverPort){
		Map<String,String> result = new HashMap<>();
		try {
			result.put("success", "false");
			long timestamp = System.currentTimeMillis() / 1000L;
			String url = MessageFormat.format(requestUrl_01, account, serverCode+"",
					serverIp,serverPort)+"?timestamp=" + timestamp;
			String md5 = MD5.encode(url + KEY);
			String signUrl = SERVER_URL+ url + "&sign_type=MD5"+ "&sign=" + md5;
			logger.info("调用接口发送请求开始["+DOMAIN+"]:isActive,请求链接:"+signUrl,"请求方式:get");
			String response = HttpTools.get(signUrl);
			logger.info("调用接口发送请求结束["+DOMAIN+"]:isActive,返回值:"+response);
			result.put("response", response);
			result.put("success", response.contains("ActiveTime")+"");
			return result;
		} catch (Exception e) {
			result.put("response", e.getMessage());
			logger.error("请求接口：["+DOMAIN+"]isActive接口异常",e);
		}
		return result;
	}

}
