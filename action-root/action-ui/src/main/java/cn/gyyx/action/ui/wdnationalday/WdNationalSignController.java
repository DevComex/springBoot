package cn.gyyx.action.ui.wdnationalday;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ServerBean;
import cn.gyyx.action.beans.wdnationalday.WdNationaldayEnrollBean;
import cn.gyyx.action.ui.WeChatBaseController;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月25日 下午1:43:15
 * 描        述：首页/登录/报名/签到/
 */
@Controller
@RequestMapping("/wdnationalsign")
public class WdNationalSignController extends WeChatBaseController{
	private static final Logger logger = GYYXLoggerFactory.
			getLogger(WdNationalSignController.class);
	
	/**
	 * 首页 openId 页面传入，暂时不用获取
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request,HttpServletResponse response) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
			
		}catch(Exception e){
			logger.error("问道十年国庆微信活动[go首页]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "nationalDay/show_phpto";
	}
	
	/**
	 * 登录页
	 */
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request,HttpServletResponse response) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
			
		}catch(Exception e){
			logger.error("问道十年国庆微信活动[go登录页]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "nationalDay/login";
	}
	
	/**
	 * 3： 双线  2：电信  1：网通
	 * 获取服务器列表
	 */
	@RequestMapping("/serverlist")
	@ResponseBody
	public ResultBean<ServerBean> serverlist(@RequestParam("netType") int netType,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<ServerBean> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "活动已结束！", null);
		return resultBean;
	}
	
	/**
	 * 获取角色列表
	 */
	@RequestMapping("/rolelist")
	@ResponseBody
	public ResultBean<String> rolelist(@RequestParam(value = "OpenId") String openId,@RequestParam(value = "serverId") int serverId,
			@RequestParam(value = "validCode") String validCode,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "活动已结束！", null);
		return resultBean;
	}
	
	/**
	 * 签到页
	 */
	@RequestMapping("/signindex")
	public String goEnrol(@RequestParam("OpenId") String openId,Model model, HttpServletRequest request,HttpServletResponse response) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
			
		}catch(Exception e){
			logger.error("问道十年国庆微信活动[go报名页]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "nationalDay/sign_one";
	}
	
	/**
	 * 执行报名操作
	 */
	@ResponseBody
	@RequestMapping("/enroll")
	public ResultBean<WdNationaldayEnrollBean> enroll(@RequestParam(value = "serverId") int serverId,@RequestParam(value = "serverName") String serverName,@RequestParam(value = "OpenId") String openId,
			@RequestParam(value = "roleId") String roleId,@RequestParam(value = "roleName") String roleName,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<WdNationaldayEnrollBean> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "活动已结束！", null);
		return resultBean;
	}
	
	/**
	 * 获取用户信息
	 */
	@ResponseBody
	@RequestMapping("/userinfo")
	public ResultBean<WdNationaldayEnrollBean> userinfo(@RequestParam(value = "OpenId") String openId,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<WdNationaldayEnrollBean> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "活动已结束！", null);
		return resultBean;
	}
	
	/**
	 * 签到
	 */
	@ResponseBody
	@RequestMapping("/sign")
	public ResultBean<String> sign(@RequestParam(value = "OpenId") String openId,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "活动已结束！", null);
		return resultBean;
	}
	
	/**
	 * 获取签到记录
	 * 1111111000 前7个数字表示1-7号签到状态： 0表示已签到 1 表示可签到 2 表示不可签到  后三个数字表示：3 5 7天是否领取奖励
	 */
	@ResponseBody
	@RequestMapping("/signlist")
	public ResultBean<String> signlist(@RequestParam(value = "OpenId") String openId,Model model, HttpServletRequest request,HttpServletResponse response) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "活动已结束！", null);
		return resultBean;
	}
	
	//页面跳转
	private boolean checkIsWeiXin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			if (!isWixXinBrowser(request)) {// 是微信浏览器
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(String.valueOf("<!doctype html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no'></head><body><div style='text-align: center;padding-top:36px;'><p style='margin:0 auto;margin-bottom: 30px;width:92px;height:92px;background:url(http://static.cn114.cn/action/nationalday/images/warn.png) no-repeat'></p><div style='margin-bottom: 25px;padding: 0 20px;'><h4 style='padding:0;margin:0;margin-bottom: 5px;font-weight: 400;font-size: 1.2rem;'>请在微信客户端打开链接<h4></div></div></body></html>"));
				response.getWriter().close();
				return false;
			}
		}catch(Exception e){
			throw e;
		}
		return true;
	}
	
	private boolean isWixXinBrowser(HttpServletRequest request){
		String ua = request.getHeader("user-agent").toLowerCase();
		//return true;
		return !(ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1);
	}

}
