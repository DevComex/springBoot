package cn.gyyx.playwd.oa.common.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;


/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年8月26日 下午1:41:01
 * 描        述：
 */
public class BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 获取登录用户
	 */
	public OAUserInfoModel getOAUserInfoModel(HttpServletRequest request){
		OAUserInfoModel userInfoModel = new OAUserInfoModel();
		try {
			userInfoModel = OAUserRequest.getUserInfoByRequest(request);
		}catch (Exception e) {
			logger.error("playwd-oa[获取用户登录信息调用第三方接口]接口异常", e);
		}
		return userInfoModel;
	}
}
