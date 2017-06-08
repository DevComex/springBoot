package cn.gyyx.playwd.agent;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import cn.gyyx.core.DataFormat;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.agent.GameModuleServerInfoResultBean;
import cn.gyyx.playwd.utils.HttpTools;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月14日 下午1:26:27
 * 描        述：调用game.module.gyyx.cn的接口
 */
@Component
public class GameModuleAgent {
	Logger logger = GYYXLoggerFactory.getLogger(getClass());
	
	private static final String DOMAIN = "game.module.gyyx.cn";
	private static final String SERVER_URL = "http" + "://" + DOMAIN;
	private static final String KEY = "123456";
	
	//获取服务器信息
	private static final String requestUrl_01 = "/game/wendao/server/{0}";
	
	/**
	 * 获取服务器信息
	 * @param classification
	 * @return
	 * @throws Exception
	 */
	public GameModuleServerInfoResultBean getWdServerInfo(int serverId){
		try {
			long timestamp = System.currentTimeMillis() / 1000L;
			String url = MessageFormat.format(requestUrl_01, serverId) + "?timestamp=" + timestamp;
			String md5 = MD5.encode(url + KEY);
			String signUrl = SERVER_URL+ url + "&sign_type=MD5"+ "&sign=" + md5;
			logger.info("调用接口发送请求开始["+DOMAIN+"]:getWdServerInfo,请求链接:"+signUrl,"请求方式:get");
			String response = HttpTools.get(signUrl);
			logger.info("调用接口发送请求结束["+DOMAIN+"]:getWdServerInfo,返回值:"+response);
			GameModuleServerInfoResultBean result = DataFormat.jsonToObj(response, GameModuleServerInfoResultBean.class);
			return result;
		} catch (Exception e) {
			GameModuleServerInfoResultBean s = new GameModuleServerInfoResultBean();
			s.setErrorMessage(e.getMessage());
			logger.error("请求接口：["+DOMAIN+"]sendGiftToGame接口异常",e);
		}
		return null;
	}

}
