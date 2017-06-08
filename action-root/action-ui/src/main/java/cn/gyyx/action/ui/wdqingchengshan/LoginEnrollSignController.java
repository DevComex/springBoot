package cn.gyyx.action.ui.wdqingchengshan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ServerBean;
import cn.gyyx.action.beans.wdqingchengshan.SignLogBean;
import cn.gyyx.action.service.wdqingchengshan.WDQcsEnrollAndSignService;
import cn.gyyx.action.ui.BaseController;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月25日 下午1:43:15
 * 描        述：首页/登录/报名/签到/
 */
@Controller
@RequestMapping("/wdqingchengshan")
public class LoginEnrollSignController extends BaseController {
	private static final Logger logger = GYYXLoggerFactory.
			getLogger(LoginEnrollSignController.class);
	
	private WDQcsEnrollAndSignService wDQcsEnrollAndSignService = new WDQcsEnrollAndSignService();
	
	/**
	 * 3： 双线  2：电信  1：网通
	 * 获取服务器列表
	 */
	@RequestMapping("/serverlist")
	@ResponseBody
	public ResultBean<ServerBean> serverlist(@RequestParam("netType") int netType,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<ServerBean> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "获取服务器列表异常", null);
		try{
			return wDQcsEnrollAndSignService.serverlist(netType);
		}catch(Exception e){
			logger.error("问道青城山活动[获取区组列表]异常",e);
		}
		return resultBean;
	}
	
	/**
	 * 获取角色列表
	 */
	@RequestMapping("/rolelist")
	@ResponseBody
	public ResultBean<String> rolelist(@RequestParam(value = "Account") String account,@RequestParam(value = "serverId") int serverId,
			@RequestParam(value = "validCode") String validCode,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "获取角色列表异常", null);
		try{
			logger.info("rolelist serverid:{},validCode:{}",serverId,validCode);
			
			if (StringUtils.isBlank(validCode)) {
				resultBean.setMessage("验证码不能为空");
				return resultBean;
			}
			
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				resultBean.setIsSuccess(false);
				resultBean.setMessage("请先登录！");
				return resultBean;
			} 
			
			if (!new Captcha(request, response).equals(validCode)) {
				resultBean.setProperties(false, "很抱歉，您的验证码填写不正确", null);
				return resultBean;
			}
			
	//		return wDQcsEnrollAndSignService.rolelist(userInfo.getAccount(),account,serverId);
		}catch(Exception e){
			logger.error("问道青城山活动[获取角色列表]异常",e);
		}
		return resultBean;
	}
	
	/**
	 * 执行报名操作
	 */
	/*@ResponseBody
	@RequestMapping("/enroll")
	public ResultBean<WdNationaldayEnrollBean> enroll(@RequestParam(value = "serverId") int serverId,@RequestParam(value = "serverName") String serverName,@RequestParam(value = "Account") String account,
			@RequestParam(value = "roleId") String roleId,@RequestParam(value = "roleName") String roleName,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<WdNationaldayEnrollBean> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "报名失败", null);
		try{
			logger.info("enroll info : {}",MessageFormat.format("serverId:{0},serverName:{1},openId:{2},roleId:{3},roleName:{4}", serverId+"",serverName,account+"",roleId+"",roleName));
			
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				resultBean.setIsSuccess(false);
				resultBean.setMessage("请先登录！");
				return resultBean;
			} 
			
			if (serverId <= 0) {
				resultBean.setMessage("服务器ID不正确");
				return resultBean;
			}
			if (StringUtils.isEmpty(serverName)) {
				resultBean.setMessage("服务器名称不正确");
				return resultBean;
			}
			if (StringUtils.isEmpty(account)) {
				resultBean.setMessage("account不能为空");
				return resultBean;
			}
			if (StringUtils.isEmpty(roleId)) {
				resultBean.setMessage("roleID不正确");
				return resultBean;
			}
			if (StringUtils.isEmpty(roleName)) {
				resultBean.setMessage("角色名称不能为空");
				return resultBean;
			}
			
			return wDQcsEnrollAndSignService.enroll(userInfo.getUserId(),userInfo.getAccount(),serverId,serverName,account,roleId,roleName,request);
		}catch(Exception e){
			logger.error("问道青城山活动[报名接口]异常",e);
		}
		return resultBean;
	}
	
	
	 // 获取用户信息
	
	@ResponseBody
	@RequestMapping("/userinfo")
	public ResultBean<WdNationaldayEnrollBean> userinfo(@RequestParam(value = "Account") String account,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<WdNationaldayEnrollBean> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "获取用户信息失败", null);
		try{
			logger.info("userinfo info : opendId {}",account);
			
			if (StringUtils.isEmpty(account)) {
				resultBean.setMessage("openId不能为空");
				return resultBean;
			}
			
			return wDQcsEnrollAndSignService.getUserInfo(account);
		}catch(Exception e){
			logger.error("问道青城山活动[获取用户信息失败]异常",e);
		}
		return resultBean;
	} */
	
	/**
	 * 签到
	 */
	@ResponseBody
	@RequestMapping(value = "/sign")
	public ResultBean<String> sign(@RequestParam("activityId") int activityId,
			HttpServletRequest request, HttpServletResponse response) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "签到失败", null);
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("LoginEnrollSignController->sign->please login.");
				resultBean.setStateCode(100);
				return resultBean;
			}
			
			return wDQcsEnrollAndSignService.sign(activityId, userInfo.getAccount(), this.getIp(request));
		}catch(Exception e){
			logger.error("问道青城山活动[签到失败]异常",e);
		}
		
		return resultBean;
	}
	
	// 获得签到记录
	@ResponseBody
	@RequestMapping(value = "/sign/get", method = RequestMethod.GET)
	public ResultBean<SignLogBean> getSignInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultBean<SignLogBean> result = new ResultBean<SignLogBean>();
		result.setIsSuccess(false);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("LoginEnrollSignController->getSignInfo->please login.");
				result.setStateCode(100);
				return result;
			}
			
			return wDQcsEnrollAndSignService.getSignInfo(userInfo.getAccount());
		}
		catch(Exception e) {
			logger.error("问道青城山活动获取签到异常",e);
		}
		
		return result;
	}

}
