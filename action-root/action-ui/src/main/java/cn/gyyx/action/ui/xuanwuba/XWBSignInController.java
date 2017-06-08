/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：V1.214
 * @本类主要用途描述:签到页控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.xuanwuba;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CalendarInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PlayerInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ResultSignBasicInfo;
import cn.gyyx.action.beans.xwbcreditsluckydraw.RewardReceiveLogBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SignPrizeBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PlayerinfoBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.SignPrizeBll;
import cn.gyyx.action.service.xuanwuba.XWBService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping(value = "/xuanwuba")
public class XWBSignInController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XWBSignInController.class);
	private XWBService xWBService = new XWBService();
	private SignPrizeBll signPrizeBll = new SignPrizeBll();
	private PlayerinfoBLL playerinfoBLL = new PlayerinfoBLL();
	private int actionCode = 288;

	/**
	 * 签到首页页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/signIndex", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String signIndex(Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		String dateStr = sdf.format(d);
		String month = dateStr
				.substring(0, dateStr.lastIndexOf("-"))
				.substring(
						dateStr.substring(0, dateStr.lastIndexOf("-")).length() - 2);
		if(month.length() == 2 && Integer.parseInt(month) < 10){
			month = month.substring(1, 2);
		}
		String yearMonth = dateStr.substring(0, dateStr.lastIndexOf("-"));
		logger.info("炫舞吧XWBSignInController-signIndex-首页显示月份：{}"+month+"，当前时间:{}"+dateStr);
		logger.info("炫舞吧XWBSignInController-signIndex-下拉菜单默认值：{}"+yearMonth+"，当前时间:{}"+dateStr);
		model.addAttribute("month", month);
		model.addAttribute("yearMonth", yearMonth);
		return "xuanwuba/sign";
	}

	/**
	 * 签到首页登陆信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/signAccountInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String signAccountInfo(HttpServletRequest request) {
		ResultBean<ResultSignBasicInfo> result = new ResultBean<ResultSignBasicInfo>();
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);
			logger.info("炫舞吧XWBSignInController-signAccountInfo-首页登录信息：{}" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		// 获得该用户的信息
		result = xWBService.signPageAccountInfo(userInfo.getAccount());
		logger.info("炫舞吧XWBSignInController-signAccountInfo-首页登录信息：{}" + result.getMessage());
		return DataFormat.objToJson(result);
	}

	/**
	 * 签到首页签到奖励信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/signRewardInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String signRewardInfo(HttpServletRequest request) {
		ResultBean<ResultSignBasicInfo> result = new ResultBean<ResultSignBasicInfo>();
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);
			logger.info("炫舞吧XWBSignInController-signRewardInfo-签到奖励信息：{}" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		// 获得该用户的信息
		result = xWBService.signPageRewardInfo(userInfo.getAccount());
		logger.info("炫舞吧XWBSignInController-signRewardInfo-签到奖励信息：{}" + result.getMessage());
		return DataFormat.objToJson(result);
	}

	/**
	 * 签到
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/signIn", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String signIn(HttpServletRequest request, String accountDate) {
		ResultBean<String> result = new ResultBean<String>();
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);
			logger.info("炫舞吧XWBSignInController-signIn-签到登录状态：{}" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		// 判断用户选择的日期是否为今天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		String date = sdf.format(new Date());
		if (!date.equals(accountDate)) {
			result.setProperties(false, "签到失败", "用户选择的日期并不是今天" + date);
			logger.info("炫舞吧XWBSignInController-signIn-签到当前时间：{}" + date+"，前台时间:{}"+accountDate);
			return DataFormat.objToJson(result);
		}
		result = xWBService.signIn(userInfo.getAccount());
		logger.info("炫舞吧XWBSignInController-signIn-签到返回信息：{}" + result.getMessage());
		return DataFormat.objToJson(result);
	}

	/**
	 * 领取奖励之前判断用户是否存在服务器以及角色性别的判定
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkPlayerInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkPlayerInfo(HttpServletRequest request) {
		ResultBean<PlayerInfoBean> result = new ResultBean<PlayerInfoBean>();
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);
			logger.info("炫舞吧XWBSignInController-checkPlayerInfo-登录超时：{}" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		// 获取该用户的信息；如果信息为空或性别、服务器名为空，则返回相应的提示信息
		PlayerInfoBean playerInfoBean = playerinfoBLL.getInfoByAccount(userInfo
				.getAccount());
		if (playerInfoBean == null) {
			result.setProperties(false, "用户没有填写个人数据", playerInfoBean);
			logger.info("炫舞吧XWBSignInController-checkPlayerInfo-领取奖励之前判断用户是否存在：{}" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		if (playerInfoBean.getRoleSex() == null
				|| playerInfoBean.getServerName() == null) {
			result.setProperties(false, "用户填写个人数据不完善", playerInfoBean);
			logger.info("炫舞吧XWBSignInController-checkPlayerInfo-领取奖励之前判断用户是否存在：{}" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		result.setProperties(true, "", playerInfoBean);
		return DataFormat.objToJson(result);
	}

	/**
	 * 获取签到日历信息
	 * 
	 * @author fanyongliang
	 * @param account
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "/getCalendarInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCalendarInfo(Integer year, Integer month,
			HttpServletRequest request) {
		ResultBean<CalendarInfoBean> calendarInfoBeanResult = new ResultBean<CalendarInfoBean>();
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		String account = "";
		// 获取失败，返回为空
		if (userInfo != null) {
			account = userInfo.getAccount();
			logger.info("炫舞吧积分活动___XWBSignInController获取签到日历信息,用户账号:" + account);
		}
		logger.info("炫舞吧积分活动___XWBSignInController获取签到日历信息,参数account:"
				+ account + ",year:" + year + ",month:" + month);
		calendarInfoBeanResult = xWBService.getCalendarInfo(account, year,
				month);
		logger.info("炫舞吧积分活动___XWBSignInController获取签到日历信息,返回JSON数据:"
				+ DataFormat.objToJson(calendarInfoBeanResult));
		return DataFormat.objToJson(calendarInfoBeanResult);
	}

	/**
	 * 根据奖励类型获取用户奖励
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/getSignPrizeByType", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getSignPrizeByType(String prizeType,
			HttpServletRequest request) {
		Calendar a = Calendar.getInstance();  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
		
		ResultBean<List<SignPrizeBean>> signPrizeBeanResult = new ResultBean<List<SignPrizeBean>>(
				false, "获取奖励信息失败", null);
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			signPrizeBeanResult.setProperties(false, "很抱歉，您的登陆超时", null);
			logger.info("message" + signPrizeBeanResult.getMessage());
			return DataFormat.objToJson(signPrizeBeanResult);
		}

		ResultBean<ResultSignBasicInfo> getRewardResult = xWBService
				.signPageRewardInfo(userInfo.getAccount());

		// 判断是否已经领取过
		boolean useFlag = false;
		List<RewardReceiveLogBean> receiveList = getRewardResult.getData()
				.getReceiveList();
		if (receiveList != null) {
			for (RewardReceiveLogBean rewardReceiveLogBean : receiveList) {
				System.out.println(rewardReceiveLogBean.getRewardType());
				if (prizeType.equals(rewardReceiveLogBean.getRewardType())) {
					useFlag = true;
				}
			}
		}
		if (useFlag) {
			signPrizeBeanResult.setProperties(false, "已经领取过！", null);
			logger.info("message" + signPrizeBeanResult.getMessage());
			return DataFormat.objToJson(signPrizeBeanResult);
		}

		// 判断是否可以领取
		boolean canFlag = false;
		int canFlagNum = 0;
		switch (prizeType) {
		case "twoDaysReward":
			canFlagNum = 2;
			if (getRewardResult.getData().getTwoDaysReward()) {
				canFlag = true;
			}
			break;
		case "fiveDaysReward":
			canFlagNum = 5;
			if (getRewardResult.getData().getFiveDaysReward()) {
				canFlag = true;
			}
			break;
		case "tenDaysReward":
			canFlagNum = 10;
			if (getRewardResult.getData().getTenDaysReward()) {
				canFlag = true;
			}
			break;
		case "fifteenDaysReward":
			canFlagNum = 15;
			if (getRewardResult.getData().getFifteenDaysReward()) {
				canFlag = true;
			}
			break;
		case "allDaysReward":
			canFlagNum = maxDate;
			if (getRewardResult.getData().getAllDaysReward()) {
				canFlag = true;
			}
			break;
		}
		String sex = playerinfoBLL.getSexByAccount(userInfo.getAccount());
		List<SignPrizeBean> list = signPrizeBll.getSignPrizeByType(
				prizeType, sex);
		List<SignPrizeBean> listAll = signPrizeBll.getSignPrizeByType(
				prizeType, "无");
		for (SignPrizeBean signPrizeBean : listAll) {
			list.add(signPrizeBean);
		}
		if (canFlag) {
			logger.info("炫舞吧积分活动___XWBSignInController获取签到日历信息,性别:" + sex);
			signPrizeBeanResult.setProperties(true, "获取奖励信息成功", list);
			return DataFormat.objToJson(signPrizeBeanResult);
		} else {
			signPrizeBeanResult.setProperties(false, "当月累计签到达到"+canFlagNum+"天才能领取奖励哦！", list);
			logger.info("message" + signPrizeBeanResult.getMessage());
			return DataFormat.objToJson(signPrizeBeanResult);
		}

	}

	/**
	 * 用户领取奖励，发送物品
	 * 
	 * @param prizeType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSignReward", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getSignReward(String prizeType, HttpServletRequest request) {
		ResultBean<String> result = new ResultBean<String>(false, "发送物品未知错误",
				"");
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时!", null);
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
		}
		// 判断此用户是否有资格领取此奖励
		ResultBean<ResultSignBasicInfo> getRewardResult = xWBService
				.signPageRewardInfo(userInfo.getAccount());
		// 如果有资格，则领取奖励
		if (getRewardResult.getIsSuccess()) {
			boolean flag = false;
			switch (prizeType) {
			case "twoDaysReward":
				if (getRewardResult.getData().getTwoDaysReward()) {
					flag = true;
				}
				;
				break;
			case "fiveDaysReward":
				if (getRewardResult.getData().getFiveDaysReward()) {
					flag = true;
				}
				;
				break;
			case "tenDaysReward":
				if (getRewardResult.getData().getTenDaysReward()) {
					flag = true;
				}
				;
				break;
			case "fifteenDaysReward":
				if (getRewardResult.getData().getFifteenDaysReward()) {
					flag = true;
				}
				;
				break;
			case "allDaysReward":
				if (getRewardResult.getData().getAllDaysReward()) {
					flag = true;
				}
				;
				break;
			}
			if (flag) {
				PlayerInfoBean playerBean = playerinfoBLL
						.getInfoByAccount(userInfo.getAccount());
				if (playerBean == null) {
					result.setProperties(false, "领取失败!", "个人信息为空!");
					return DataFormat.objToJson(result);
				}
				// 发送物品
				result = xWBService.sendReward(playerBean.getServerId(),
						playerBean.getServerName(), userInfo, prizeType);
			} else {
				result.setProperties(false, "用户没有资格领取奖励!", "用户没有资格领取奖励!");
				return DataFormat.objToJson(result);
			}
		} else {
			result.setProperties(false, "获取用户奖励资格失败!", "获取用户奖励资格失败!");
			return DataFormat.objToJson(result);
		}
		return DataFormat.objToJson(result);
	}

}
