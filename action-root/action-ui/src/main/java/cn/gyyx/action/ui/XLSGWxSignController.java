/*------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.service.MobileOsInfoService;
import cn.gyyx.action.service.weixin.WeChatAttention;
import cn.gyyx.action.service.xlsgwxsign.XLSGWxSignService;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;

/* 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 驯龙三国微信签到功能
 */
@Controller
@RequestMapping(value = "/xlsgwxsign")
public class XLSGWxSignController{
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XLSGWxSignController.class);
	private XLSGWxSignService xLSGWxSignService = new XLSGWxSignService();
	private static final String WX_TYPE = "Xlsg";
	private static final String WX_SIGN_KEY = "Dfad124FAC518DF3";
	private WeChatAttention weChatAttention = new WeChatAttention();
	
	/**
	 * 默认页
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request,HttpServletResponse response) {
		String openId = null;
		try{
			// 是否是微信浏览器
			if(!checkIsWeiXin(request, response)){
				return "xlsgWxSign/index";
			}
			
			HashMap<String,Object> r = valideURLSign(request, response);
			if(r.get("success").toString() != "true"){
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(String.valueOf("<!doctype html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no'></head><body><div style='text-align: center;padding-top:36px;'><p style='margin:0 auto;margin-bottom: 30px;width:92px;height:92px;background:url(http://static.cn114.cn/action/nationalday/images/warn.png) no-repeat'></p><div style='margin-bottom: 25px;padding: 0 20px;'><h4 style='padding:0;margin:0;margin-bottom: 5px;font-weight: 400;font-size: 1.2rem;'>此链接地址无效!<h4></div></div></body></html>"));
        		response.getWriter().close();
        		return "xlsgWxSign/index";
			}
			
			openId = (String)r.get("openId");
			model.addAttribute("openId", openId);
			
			//获取用户当前积分值
			ResultBean<Integer> resultBean = xLSGWxSignService.getUserScore(openId);
			model.addAttribute("userScore", resultBean.getData());
		}catch(Exception e){
			logger.error("驯龙三国微信签到功能[go默认页Controller]异常,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		return "xlsgWxSign/index";
	}

	/***
	 * 积分商城页
	 */
	@RequestMapping("/toIntegralMall")
	public String toIntegralMall(Model model,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "openId") String openId) {
		String referer = request.getHeader("referer");
        if (referer == null || referer.length() <= 0) {
        	return "redirect:share";
        }
		try{
			logger.info("驯龙三国微信签到[去积分商城页],请求参数：openId{}",openId);
			// 是否是微信浏览器
			if(!checkIsWeiXin(request, response)){
				return "xlsgWxSign/integralMall";
			}
			model.addAttribute("openId", openId);
		}catch(Exception e){
			logger.error("驯龙三国微信签到功能[go积分商城页Controller]异常,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		return "xlsgWxSign/integralMall";
	}
	
	/***
	 * 获取用户当前积分
	 */
	@RequestMapping("/getUserScore")
	@ResponseBody
	public ResultBean<Integer> getUserScore(Model model,
			HttpServletRequest request,
			@RequestParam(value = "openId") String openId) {
		logger.info("驯龙三国微信签到[获取用户积分],请求参数：openId[{}]",openId);
		ResultBean<Integer> resultBean = new ResultBean<Integer>(false,"接口异常",0);
		try{
			resultBean = xLSGWxSignService.getUserScore(openId);
		}catch(Exception e){
			logger.error("驯龙三国微信签到功能[获取用户积分Controller]异常,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		logger.info("驯龙三国微信签到[获取用户积分],返回结果：" + resultBean);
		return resultBean;
	}
	
	/***
	 * 获取用户已兑换过的礼包码列表
	 */
	@RequestMapping("/getExchangeLogList")
	@ResponseBody
	public ResultBean<List<HashMap<String,String>>> getExchangeLogList(Model model,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "openId") String openId) {
		logger.info("驯龙三国微信签到[获取用户已兑换过的礼包码列表],请求参数：openId[{}]",openId);
		ResultBean<List<HashMap<String,String>>> resultBean = new ResultBean<List<HashMap<String,String>>>(false,"接口异常",null);
		try{
			resultBean = xLSGWxSignService.getExchangeLogList(openId);
		}catch(Exception e){
			logger.error("驯龙三国微信签到功能[获取用户已兑换过的礼包码列表Controller]异常,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		logger.info("驯龙三国微信签到[获取用户已兑换过的礼包码列表],返回结果：" + resultBean);
		return resultBean;
	}
	
	/***
	 * 签到
	 */
	@RequestMapping(value = "/sign",method = {RequestMethod.POST})
	@ResponseBody
	public ResultBean<HashMap<String, String>> sign(Model model,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "openId") String openId) {
		logger.info("驯龙三国微信签到[签到],请求参数：openId[{}]",openId);
		ResultBean<HashMap<String, String>> resultBean = new ResultBean<HashMap<String,String>>(false,"接口异常",null);
		try{
			boolean isAttention = weChatAttention.isAttention(WX_TYPE,openId);
			if(!isAttention){
				resultBean.setMessage("请先关注该微信号");
				return resultBean;
			}else{
				resultBean = xLSGWxSignService.sign(openId);
				//获取用户当前积分值
				ResultBean<Integer> userScoreRb = xLSGWxSignService.getUserScore(openId);
				resultBean.getData().put("curUserScore", userScoreRb.getData() + "");
			}
		}catch(Exception e){
			logger.error("驯龙三国微信签到功能[签到Controller]异常,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		logger.info("驯龙三国微信签到[签到],返回结果：" + resultBean);
		return resultBean;
	}
	
	/***
	 * 积分兑换礼包
	 */
	@RequestMapping(value = "/exchange",method = {RequestMethod.POST})
	@ResponseBody
	public ResultBean<String> exchange(Model model,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "openId") String openId,@RequestParam(value = "gid") String gid) {
		logger.info("驯龙三国微信签到[积分兑换礼包],请求参数：openId[{}],gid[{}]",openId,gid);
		ResultBean<String> resultBean = new ResultBean<String>(false,"接口异常","");
		try{
			boolean isAttention = weChatAttention.isAttention(WX_TYPE,openId);
			if(!isAttention){
				resultBean.setMessage("请先关注该微信号！");
				return resultBean;
			}else{
				// 获得手机操作系统类型：安卓、IOS
				String os = MobileOsInfoService.getInstance().get(request.getHeader("user-agent"));
				logger.info("驯龙三国-积分兑换礼包：openId=" + openId + ";gid=" + gid + ";os=" + os);
				resultBean = xLSGWxSignService.exchange(openId, gid, os);
				logger.info("驯龙三国-积分兑换礼包：openId=" + openId + ";gid=" + gid + ";resultBean=" + JSON.toJSONString(resultBean));
			}
		}catch(Exception e){
			logger.error("驯龙三国微信签到功能[积分兑换礼包Controller]异常,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		logger.info("驯龙三国微信签到[积分兑换礼包],返回结果：" + resultBean);
		return resultBean;
	}
	
	/***
	 * 微信请求url 验签名
	 */
	public HashMap<String,Object> valideURLSign(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", "false");
		String time = "0";
		String sign = "0";
		String openId = "";
    	if(request.getQueryString() != null){
    		//检查连接打开的时间
            String paramsStr = request.getQueryString();
            String uri = paramsStr.replace("%26", "&").replace("%3d", "=");
            
            String[] paramArray = uri.split("&");
            Map<String, String> paramMap = new HashMap<String, String>();
            
            for (int i = 0; i < paramArray.length; i++) {
            	paramMap.put(paramArray[i].split("=")[0].toLowerCase(), paramArray[i].split("=")[1]);
			}
            
            if(paramMap.containsKey("time")){
            	time = paramMap.get("time");
            }
            
            if(paramMap.containsKey("sign")){
            	sign = paramMap.get("sign");
            }        
            
            if(paramMap.containsKey("openid")){
            	openId = paramMap.get("openid");
            }
            
            String url = "OpenId=" + openId + "&time="
    				+ time;
            
            logger.info("驯龙三国微信签到MD5编码"+MD5.encode(url+WX_SIGN_KEY));
            
            if (MD5.encode(url+WX_SIGN_KEY).equals(sign)){
            	result.put("openId", openId);
            	result.put("success", "true");
            }
		}
        return result;
    }
	

	//页面跳转
	private boolean checkIsWeiXin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			String ua = request.getHeader("user-agent").toLowerCase();
			
			if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {//不是微信浏览器
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(String.valueOf("<!doctype html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no'></head><body><div style='text-align: center;padding-top:36px;'><p style='margin:0 auto;margin-bottom: 30px;width:92px;height:92px;background:url(http://static.cn114.cn/action/nationalday/images/warn.png) no-repeat'></p><div style='margin-bottom: 25px;padding: 0 20px;'><h4 style='padding:0;margin:0;margin-bottom: 5px;font-weight: 400;font-size: 1.2rem;'>请在微信客户端打开链接<h4></div></div></body></html>"));
				response.getWriter().close();
				return false;
			}
		}catch(Exception e){
			logger.error("检查是否是微信浏览器失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		return true;
	}
	
	/**
	 * 分享
	 */
	@RequestMapping("/share")
	public String share(Model model, HttpServletRequest request,HttpServletResponse response,String openId) {
		try{
			if(!checkIsWeiXin(request, response)){
				return "xlsgWxSign/sharePage";
			}
			
			model.addAttribute("openId", openId);
		}catch(Exception e){
			logger.error("驯龙三国微信签到功能[go分享页Controller]异常,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		return "xlsgWxSign/sharePage";
	}
	
}
