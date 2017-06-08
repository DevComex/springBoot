package cn.gyyx.action.ui.wdwxmidyear;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.weixin.WeChatAttention;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 类:WdWxMidyearController<br />
 * 叙述:问道年中微信活动<br />
 * 注释日期:2016年7月8日<br />
 *
 * @author lizepu
 */
@Controller
@RequestMapping("/wxmidyear2")
public class WdWxMidyearController {
	private static final GYYXLogger  LOGError = GYYXLoggerFactory.getLogger(WdWxMidyearController.class);
	private final WeChatAttention weChatAttention = new WeChatAttention();
	
	private final boolean Debug=false;

	@RequestMapping("/dh")
	public String dh(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/dh";
	}

	@RequestMapping("/bestSellers")
	public String bestSellers(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/bestSellers";
	}

	@RequestMapping("/bestSellers2")
	public String bestSellers2(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/bestSellers2";
	}

	@RequestMapping("/bestSellers3")
	public String bestSellers3(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/bestSellers3";
	}

	@RequestMapping("/bestSellers4")
	public String bestSellers4(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/bestSellers4";
	}

	@RequestMapping("/fxinfo")
	public String fxinfo(HttpServletRequest request, HttpServletResponse response) {

		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/fxinfo";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/index";
	}

	@RequestMapping("/signUp")
	public String signUp(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/signUp";
	}

	@RequestMapping("/breakForecast")
	public String breakForecast(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return "wdmidyear/break_forecast";
	}


	/**
	 * 叙述:根据openId获得报名信息<br />
	 * 
	 * @param request
	 * @param openId
	 * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/getMainInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> getMainInfo(HttpServletRequest request, @RequestParam("openId") String openId, HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return result;
	}
	
	/**
	 * 叙述:根据openId获得排名信息<br />
	 * 
	 * @param request
	 * @param openId
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getMainRank",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object>  getMainRank(HttpServletRequest request,@RequestParam("openId") String openId,HttpServletResponse response){
		ResultBean<Object> result=new ResultBean<>(false,"谢谢参与，活动已结束",null);
		if(!wxCheck(request,response)){
			return null;
		}			
		if (weChatAttention.isAttention("Wd", openId)) {
		return result;
		} else {
			result.setIsSuccess(false);
			result.setMessage("未关注");
		}	    
		return result;
	}
	

	/**
	 * 叙述:报名接口<br />
	 * 
	 * @param request
	 * @param wxBean
	 * @param response
	 * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/uploadMainInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> uploadMainInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		// 是否是微信浏览器
		try {
			 if (!wxCheck(request, response)) {
			 return null;
			 }
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.setIsSuccess(false);
				result.setMessage("请先登录");
			} else {
				return result;
			}
		} catch (RuntimeException e) {
			result.setIsSuccess(false);
			result.setMessage("异常：" + e.getClass().getCanonicalName());
			result.setData(e);
		}
		return result;
	}

	/**
	 * 叙述:获得作品信息<br />
	 * 
	 * @param request
	 * @param queryBean
	 * @param response
	 * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/queryInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> queryInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		// 是否是微信浏览器
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		return result;
	}

	/**
	 * 叙述:获得排名信息<br />
	 * 
	 * @param page
	 * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/getScoreRank", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> getScoreRank() {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	/**
	 * 叙述:查询奖品信息<br />
	 * 
	 * @param request
	 * @param response
	 * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/queryGiftInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> queryGiftInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	/**
	 * 叙述:兑换奖品<br />
	 * 
	 * @param request
	 * @param response
	 * @param openId
	 * @param giftCode
	 * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/getGift", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> getGift(HttpServletRequest request, HttpServletResponse response, @RequestParam("openId") String openId, @RequestParam("giftCode") int giftCode) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	/**
	 * 叙述:更新地址信息<br />
	 * 
	 * @param request
	 * @param response
	 * @param openId
	 * @param address
	 * @param phone
	 * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/uploadAddressInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> uploadAddressInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("openId") String openId,
			@RequestParam("address") String address, @RequestParam("name") String name, @RequestParam("phone") String phone) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	/**
	 * 叙述:获得最新50个积分兑换用户数据<br />
	 * 
	 * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/getExchangedUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> getExchangedUser() {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	/**
	 * 叙述:用户抽奖<br />
	 * 
	 * @param openId
	 * @return ResultBean
	 */
	@RequestMapping(value = "/lottery", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> lottery(@RequestParam("openId") String openId) {
	    ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
	    return result;
	}

	// TODO 分割

	@RequestMapping(value = "/add/share", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> addShareInfo(HttpServletRequest request, @RequestParam("openId") String openId,
			HttpServletResponse response) {
		ResultBean<String> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		// 是否是微信浏览器

		 if (!wxCheck(request, response)) {
		 return null;
		 }

		if (!weChatAttention.isAttention("Wd", openId)) {
			result.setIsSuccess(false);
			result.setMessage("请先关注公众号");
			result.setData("NeedFollowPubNum");
			return result;
		}
		return result;

	}

	private boolean wxCheck(HttpServletRequest request, HttpServletResponse response) {
		boolean isPassCheck=true;
		if(Debug){
			return isPassCheck;
		}
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 是微信浏览器
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
				response.getWriter().close();
				return !isPassCheck;
			} catch (IOException e) {
//				LOG.info("Exception in wxCheck:" +e);
				LOGError.error(e);
			}
		}
		return isPassCheck;
	}

	@RequestMapping(value = "/notice/status", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<Object> getIsNotice(HttpServletRequest request, @RequestParam("openId") String openId, HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		// 是否是微信浏览器

		 if (!wxCheck(request, response)) {
		 return null;
		 }

		if (weChatAttention.isAttention("Wd", openId)) {
			result.setIsSuccess(true);
			result.setMessage("已关注");
		} else {
			result.setIsSuccess(false);
			result.setMessage("未关注");
		}
		return result;
	}

	@RequestMapping(value = "/query/Info", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<Object> queryInfoSingle(HttpServletRequest request, @RequestParam("id") int id, HttpServletResponse response) {
		// 是否是微信浏览器

		 if (!wxCheck(request, response)) {
		 return null;
		 }
		if (id <= 0) {
			return null;
		}
		ResultBean<Object> resultBean = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return resultBean;
	}

	@RequestMapping(value = "/check/action/date", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<Object> isActionDate(HttpServletRequest request, HttpServletResponse response) {
		 if (!wxCheck(request, response)) {
		 return null;
		 }
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	@ExceptionHandler({ BindException.class })
	@ResponseBody
	public ResultBean<Integer> exception(BindException e) {
		ResultBean<Integer> bandResultBean = new ResultBean<Integer>();
		bandResultBean.setSuccess(false);
		BindingResult bindingRes = e.getBindingResult();
		FieldError fr = bindingRes.getFieldError();
		bandResultBean.setMessage("数据验证错误:" + fr.getDefaultMessage());
		LOGError.error(e);
		return bandResultBean;
	}
}