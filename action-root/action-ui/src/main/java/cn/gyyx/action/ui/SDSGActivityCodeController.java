package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.service.sdsg.activityCode.SDSGActivityCodeService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @Description: 神道三国抢激活码活动
 * @author lizhihai
 * @date 2016年11月07日 
 */
@Controller
@RequestMapping("/sdsg")
public class SDSGActivityCodeController {
	private SDSGActivityCodeService activityCodeService = new SDSGActivityCodeService();
	private static final Logger logger = GYYXLoggerFactory.getLogger(SDSGActivityCodeController.class);
	public static final int ACTIONCODE = 405;
	public static final int GAMEID = 28;
	
	/**
	 * 抽激活码页面  
	 */
	@RequestMapping("/step1Index")
	private String stepOneIndex(HttpServletRequest request, Model model) {
		return "SDSGActivityCode/step1";
	}

	/**
	 * 激活页面 
	 */
	@RequestMapping("/step2Index")
	private String stepTwoIndex(HttpServletRequest request, Model model) {
		return "SDSGActivityCode/step2";
	}

	/**
	 * 查看激活情况页面 
	 */
	@RequestMapping("/step3Index")
	private String stepThreeIndex(HttpServletRequest request, Model model) {
		return "SDSGActivityCode/step3";
	}

	/**
	 * 检查用户登录状态 
	 */
	@RequestMapping(value = "/checkLoginStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<UserLotteryBean> checkLoginStatus(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "登录超时", null);
		} 
		try {
			UserLotteryBean lotterbean = new UserLotteryBean();
			int lotteryNum = activityCodeService.lotteryNum(ACTIONCODE, userInfo);
			String cardCode = activityCodeService.searchActivityCode(userInfo);
			String account = userInfo.getAccount();
			model.addAttribute("lotteryNum", lotteryNum);
			model.addAttribute("account", account);
			model.addAttribute("cardCode", cardCode);
			lotterbean.setCardCode(cardCode);
			lotterbean.setLotterytime(lotteryNum);
			result.setProperties(true, "已登录", lotterbean);
			return result;
		} catch (Exception e) {
			logger.error("神道三国抢激活码[检查用户登录状态 ]接口异常", e);
			return new ResultBean<>(false, "网络异常", null);
		}
	}

	/**
	 * 抽激活码 
	 */
	@ResponseBody
	@RequestMapping(value = "/lottery", method = RequestMethod.POST)
	private ResultBean<UserLotteryBean> lottery(HttpServletRequest request) {
		ResultBean<UserLotteryBean> result = new ResultBean<>();
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "登录超时", null);
		}
		try {
			result = activityCodeService.lottery( ACTIONCODE, "byChance",userInfo);
			return result;
		} catch (Exception e) {
			logger.error("神道三国抢激活码[抽激活码 ]接口异常", e);
			return new ResultBean<>(false, "网络异常", null);
		}
	}

	/**
	 * 获取游戏区服
	 */
	@ResponseBody
	@RequestMapping(value ="/getRegion",method = RequestMethod.POST)
	private ResultBean<String> getRegion(HttpServletRequest request) {
		String result = "";
		ResultBean<String> resultBean = new ResultBean<String>();
		 try {
			 result = activityCodeService.getRegion();
			 resultBean.setProperties(true, "获取区服成功", result);
			 return resultBean;
		 } catch (Exception e) {
			 logger.error("神道三国抢激活码[获取游戏区服 ]接口异常", e);
			 return new ResultBean<>(false, "网络异常", null);
		 }
	}

	/**
	 * 激活操作 
	 */
	@ResponseBody
	@RequestMapping(value = "/step2", method = RequestMethod.POST)
	private ResultBean<String> stepTwo(HttpServletRequest request, String serverID, String serverName,
		String activityCode) {
		ResultBean<String> result = new ResultBean<String>();
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "登录超时", "");
		}
		try {
			result = activityCodeService.stepTwo(userInfo, ACTIONCODE, serverID, serverName, activityCode);
			return result;
		} catch (Exception e) {
			logger.error("神道三国抢激活码[查看激活情况]接口异常", e);
			return new ResultBean<>(false, "网络异常", null);
		}
	}

	/**
	 *  查看激活情况
	 */
	@ResponseBody
	@RequestMapping(value = "/step3", method = RequestMethod.POST)
	private ResultBean<String> stepThree(HttpServletRequest request, HttpServletResponse response, String checkcode) {
		ResultBean<String> result = new ResultBean<String>();
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "登录超时", "");
		}
		if (!new Captcha(request, response).equals(checkcode)) {
			return new ResultBean<>(false, "您的验证码填写不正确", "");
		}
		try {
			result = activityCodeService.stepThree(GAMEID, userInfo.getUserId());
			return result;
		} catch (Exception e) {
			 logger.error("神道三国抢激活码[查看激活情况]接口异常", e);
			 return new ResultBean<>(false, "网络异常", null);
		}
	}
}
