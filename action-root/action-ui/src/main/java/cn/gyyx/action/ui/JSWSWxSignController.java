/*------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.ui;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.MobileOsInfoService;
import cn.gyyx.action.service.jswswxsign.JSWSWxSignService;
import cn.gyyx.action.service.weixin.WeChatAttention;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/* 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能
 */
@Controller
@RequestMapping(value = "/jswswxsign")
public class JSWSWxSignController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(JSWSWxSignController.class);
	private JSWSWxSignService jSWSWxSignService = new JSWSWxSignService();
	private static final String WX_TYPE = "JswsSyzxz";
	private static final String WX_SIGN_KEY = "Dfad124FAC518DF3";
	private WeChatAttention weChatAttention = new WeChatAttention();
	
	/**
	 * 默认页
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request,HttpServletResponse response) {
		PrintWriter write = null;
		String openId = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
			
			HashMap<String,Object> r = valideURLSign(request, response);
			openId = (String)r.get("openId");
			
			model.addAttribute("openId", openId);
			
			//获取用户当前积分值
			ResultBean<Integer> resultBean = jSWSWxSignService.getUserScore(openId);
			model.addAttribute("userScore", resultBean.getData());
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[go默认页Controller]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "jswsWxSign/index";
	}

	/***
	 * 积分商城页
	 */
	@RequestMapping("/toIntegralMall")
	public String toIntegralMall(Model model,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "openId") String openId) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			logger.info("绝世武神微信签到[去积分商城页],请求参数：openId{}",openId);
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
					
			//获取积分商品兑换礼包列表
			//ResultBean<List<Gift>> resultBean = jSWSWxSignService.getGiftList();
			//model.addAttribute("giftList", resultBean.getData());
			model.addAttribute("openId", openId);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[go积分商城页Controller]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "jswsWxSign/integralMall";
	}
	
	/***
	 * 获取用户当前积分
	 */
	@RequestMapping("/getUserScore")
	@ResponseBody
	public ResultBean<Integer> getUserScore(Model model,
			HttpServletRequest request,
			@RequestParam(value = "openId") String openId) {
		logger.info("绝世武神微信签到[获取用户积分],请求参数：openId[{}]",openId);
		ResultBean<Integer> resultBean = new ResultBean<Integer>();
		
		resultBean.setIsSuccess(false);
		resultBean.setMessage("操作失败！");
		try{
			// 是否是微信浏览器
			boolean checkIsWeiXin = checkIsWeiXin(request,resultBean);
			if(!checkIsWeiXin){
				return resultBean;
			}
			resultBean = jSWSWxSignService.getUserScore(openId);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[获取用户积分Controller]异常",e);
		}
		logger.info("绝世武神微信签到[获取用户积分],返回结果：" + resultBean);
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
		logger.info("绝世武神微信签到[获取用户已兑换过的礼包码列表],请求参数：openId[{}]",openId);
		ResultBean<List<HashMap<String,String>>> resultBean = new ResultBean<List<HashMap<String,String>>>();
		resultBean.setIsSuccess(false);
		resultBean.setMessage("操作失败！");
		try{
			// 是否是微信浏览器
			boolean checkIsWeiXin = checkIsWeiXin(request,resultBean);
			if(!checkIsWeiXin){
				return resultBean;
			}
			resultBean = jSWSWxSignService.getExchangeLogList(openId);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[获取用户已兑换过的礼包码列表Controller]异常",e);
		}
		logger.info("绝世武神微信签到[获取用户已兑换过的礼包码列表],返回结果：" + resultBean);
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
		logger.info("绝世武神微信签到[签到],请求参数：openId[{}]",openId);
		ResultBean<HashMap<String, String>> resultBean = new ResultBean<HashMap<String,String>>();
		resultBean.setIsSuccess(false);
		resultBean.setMessage("操作失败！");
		try{
			// 是否是微信浏览器
			boolean checkIsWeiXin = checkIsWeiXin(request,resultBean);
			if(!checkIsWeiXin){
				return resultBean;
			}
			//参数校验			
			boolean isCheck = signParamCheck(openId, resultBean);
			if(isCheck){
				boolean isAttention = weChatAttention.isAttention(WX_TYPE,openId);
				if(!isAttention){
					resultBean.setMessage("请先关注该微信号！");
				}else{
					resultBean = jSWSWxSignService.sign(openId);
					//获取用户当前积分值
					ResultBean<Integer> userScoreRb = jSWSWxSignService.getUserScore(openId);
					resultBean.getData().put("curUserScore", userScoreRb.getData() + "");
				}
			}
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[签到Controller]异常",e);
		}
		logger.info("绝世武神微信签到[签到],返回结果：" + resultBean);
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
		logger.info("绝世武神微信签到[积分兑换礼包],请求参数：openId[{}],gid[{}]",openId,gid);
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setIsSuccess(false);
		resultBean.setMessage("操作失败！");
		try{
			// 是否是微信浏览器
			boolean checkIsWeiXin = checkIsWeiXin(request,resultBean);
			if(!checkIsWeiXin){
				return resultBean;
			}
			//参数校验			
			boolean isCheck = exchangeParamCheck(openId,gid, resultBean);
			if(isCheck){
				boolean isAttention = weChatAttention.isAttention(WX_TYPE,openId);
				if(!isAttention){
					resultBean.setMessage("请先关注该微信号！");
				}else{
					// 获得手机操作系统类型：安卓、IOS
					String os = MobileOsInfoService.getInstance().get(request.getHeader("user-agent"));
					logger.info("绝世武神-积分兑换礼包：openId=" + openId + ";gid=" + gid + ";os=" + os);
					if (StringUtils.isEmpty(os)) {
						// 没有识别出手机操作系统
						resultBean.setMessage("请使用安卓或者苹果手机！");
					}
					else if (os.equalsIgnoreCase("ios") || os.equalsIgnoreCase("android")) {
						// 苹果手机或者安卓手机
						resultBean = jSWSWxSignService.exchange(openId, gid, os);
					}
					else {
						// 其他手机
						resultBean.setMessage("只支持安卓或者苹果手机！");
					}
					logger.info("绝世武神-积分兑换礼包：openId=" + openId + ";gid=" + gid + ";resultBean=" + JSON.toJSONString(resultBean));
				}
			}
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[积分兑换礼包Controller]异常",e);
		}
		logger.info("绝世武神微信签到[积分兑换礼包],返回结果：" + resultBean);
		return resultBean;
	}
	
	/***
	 * 微信请求url 验签名
	 * @throws Exception 
	 */
	public HashMap<String,Object> valideURLSign(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HashMap<String,Object> result = new HashMap<String,Object>();
		boolean res = false;
		String time = "0";
		String sign = "0";
		String message = "此链接地址无效！";
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
            
            if (CheckSign(url,WX_SIGN_KEY,sign)){
            	long timestamp = System.currentTimeMillis() / 1000;
                if (Integer.parseInt(time) <= 0 || timestamp - Integer.parseInt(time) > 3600){	
                	message =  "链接地址已过有效期，请重新获取！";
                }else{
                	res = true;
                	result.put("openId", openId);
                }
            }
		}
    	if(!res){
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(String.valueOf("<h1>"+message+"</h1>"));
			response.getWriter().close();
    	}
        return result;
    }
	
	/**
	 * 验证签名算法
	 * @param url
	 * @param signKey
	 * @param sign
	 * @return
	 */
	private static boolean CheckSign(String url, String signKey ,String sign) {
		logger.info("绝世武神微信签到MD5编码"+MD5.encode(url+signKey));
        return MD5.encode(url+signKey).equals(sign);
    }
	

	@SuppressWarnings("rawtypes")
	private boolean signParamCheck(String openId,
			ResultBean resultBean) {
		boolean res = false;
		if (StringUtils.isBlank(openId)) {
			resultBean.setMessage("缺少必要参数！");
			return res;
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean exchangeParamCheck(String openId,String gid,
			ResultBean resultBean) {
		boolean res = false;
		if (StringUtils.isBlank(openId) || StringUtils.isBlank(gid)) {
			resultBean.setMessage("缺少必要参数！");
			return res;
		}
		return true;
	}
	
	//页面跳转
	private void checkIsWeiXin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			if (!isWixXinBrowser(request)) {// 是微信浏览器
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
				response.getWriter().close();
			}
		}catch(Exception e){
			throw e;
		}
	}
	//ajax 跳转
	@SuppressWarnings("rawtypes")
	private boolean checkIsWeiXin(HttpServletRequest request,ResultBean resultBean) {
		boolean res = false;
		if (!isWixXinBrowser(request)) {// 是微信浏览器
			resultBean.setMessage("此功能只能在微信浏览器中使用！");
			return res;
		}
		return true;
	}
	
	private boolean isWixXinBrowser(HttpServletRequest request){
		String ua = request.getHeader("user-agent").toLowerCase();
		return !(ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1);
		//return true;
	}
	
	/**
	 * 分享
	 */
	@RequestMapping("/share")
	public String share(Model model, HttpServletRequest request,HttpServletResponse response,String openId) {
		PrintWriter write = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			// 是否是微信浏览器
			checkIsWeiXin(request, response);
			/*
			HashMap<String,Object> r = valideURLSign(request, response);
			openId = (String)r.get("openId");
			*/
			model.addAttribute("openId", openId);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[go分享页Controller]异常",e);
			if(write != null){
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		return "jswsWxSign/sharePage";
	}
	
}
