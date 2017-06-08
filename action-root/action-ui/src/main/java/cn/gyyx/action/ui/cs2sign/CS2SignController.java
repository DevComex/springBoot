/*************************************************
       Copyright ©, 2016, GY Game
       Author: chenpeilan
       Created: 2016年5月11日
       Note：创世2每日签到活动
 ************************************************/
package cn.gyyx.action.ui.cs2sign;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.cs2sign.Cs2SignInfoBean;
import cn.gyyx.action.beans.cs2sign.ServerBean;
import cn.gyyx.action.service.CaptchaValidate;
import cn.gyyx.action.service.cs2Sign.CS2GameAgent;
import cn.gyyx.action.service.cs2Sign.CS2SignService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: CS2SignController
 * @Description: 创世2签到活动
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年5月11日 上午10:36:33
 *
 */
@Controller
@RequestMapping(value = "/cs2sign")
public class CS2SignController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CS2SignController.class);
	private CS2GameAgent cs2GameAgent = new CS2GameAgent();
	private CS2SignService service = new CS2SignService();
	/**
	 * 
	 * @Title: toIndex
	 * @Description: 活动首页
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/index")
	public String toIndex(Model model, HttpServletRequest request) {
		return "cs2Cb/index";
	}

	/**
	 * @Title: getRegion
	 * @Description: 获取游戏大区
	 * @param @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/getServers", method = RequestMethod.POST)
	private ResultBean<ServerBean> getServers(HttpServletRequest request) {
		ResultBean<ServerBean> resultBean = new ResultBean<>();
		ServerBean result = cs2GameAgent.getServers();
		resultBean.setProperties(true, "success", result);
		return resultBean;
	}

	/**
	 * 
	 * @Title: getRoles
	 * @Description: 根据区服获取角色
	 * @param @param gameId
	 * @param @param serverIP
	 * @param @param serverPort
	 * @param @param account
	 * @param @return
	 * @return ResultBean<String>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getRoles", method = RequestMethod.POST)
	private ResultBean<Object> getRoles(
			@RequestParam(value = "serverCode")int serverCode,@RequestParam("captcha")String captcha,HttpServletRequest request) {
		CaptchaValidate validate = new CaptchaValidate();
		if(validate.checkCaptcha(captcha, request) == 0) {
			UserInfo user = SignedUser.getUserInfo(request);
			if(user != null) {
				ResultBean<JSONObject> serverResult = cs2GameAgent.getServerByCode("chuangshi2", serverCode);
				if(serverResult.getIsSuccess()) {
					JSONObject serverObject = serverResult.getData();
					return cs2GameAgent.getRoles(serverObject.getString("ServerIp"), serverObject.getString("ServerPort"), user.getAccount());
				} else {
					logger.info("getRoles fail");
					return new ResultBean<Object>(false, "网络超时," + serverResult.getMessage() ,null);
				}
			} else {
				return new ResultBean<Object>(false, "请先登录",null);
			}
		} else {
			return new ResultBean<Object>(false, "验证码错误",null);
		}
		
	}

	@RequestMapping(value = "/getSignInfo", method = RequestMethod.POST)
	@ResponseBody public ResultBean<Cs2SignInfoBean> getSignInfo(HttpServletRequest request) {
		ResultBean<Cs2SignInfoBean> result = new ResultBean<Cs2SignInfoBean>();
		UserInfo user = SignedUser.getUserInfo(request);
		if(user != null) {
			service.getSignInfo(user, result);
		} else {
			result.setIsSuccess(false);
			result.setMessage("请先登录");
		}
		return result;
	}
	
	@RequestMapping(value = "/bindInfo", method = RequestMethod.POST)
	@ResponseBody public ResultBean<Object> bindInfo(
			HttpServletRequest request,
			@RequestParam("serverCode")int serverCode,
			@RequestParam("character")String character
			) {
		ResultBean<Object> result = new ResultBean<Object>();
		UserInfo user = SignedUser.getUserInfo(request);
		if(user != null) {
			ResultBean<JSONObject> serverResult = cs2GameAgent.getServerByCode("chuangshi2", serverCode);
			if(serverResult.getIsSuccess()) {
				JSONObject serverObject = serverResult.getData();
				service.bindInfo(user, serverObject.getString("ServerName"),serverObject.getString("ServerIp"),serverObject.getString("ServerPort"), character, result);
			} else {
				result.setIsSuccess(false);
				result.setMessage("网络访问超时," + serverResult.getMessage());
			}
		} else {
			result.setIsSuccess(false);
			result.setMessage("请先登录");
		}
		return result;
	}
	
	@RequestMapping(value = "/sign", method = RequestMethod.POST)
	@ResponseBody public ResultBean<Object> sign(HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<Object>();
		UserInfo user = SignedUser.getUserInfo(request);
		if(user != null) {
			service.sign(user, result);
		} else {
			result.setIsSuccess(false);
			result.setMessage("请先登录");
		}
		return result;
	}
}
