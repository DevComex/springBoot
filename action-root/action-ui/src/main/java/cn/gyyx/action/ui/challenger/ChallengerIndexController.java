package cn.gyyx.action.ui.challenger;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.ChallenterDeathLifeRankBean;
import cn.gyyx.action.beans.challenger.ChallenterInfoBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveRadioBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveTimeRankBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.challenger.ChallenterLiveBll;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.service.challenger.ChallengerConstant;
import cn.gyyx.action.service.challenger.ChallenterLiveRadioService;
import cn.gyyx.action.service.challenger.ChallenterLiveService;
import cn.gyyx.action.service.challenger.LotteryService;
import cn.gyyx.action.service.challenger.UserInfoService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @Description: 问道挑战新世界首页控制器
 * @author 成龙
 * @date 2016年7月12日 下午19:00:00
 */
@Controller
@RequestMapping(value = "/challenger")
public class ChallengerIndexController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChallengerIndexController.class);
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	UserInfoService userInfoService = new UserInfoService();
	private LotteryService lotteryService = new LotteryService();
	private ChallenterLiveRadioService challenterLiveRadioService = new ChallenterLiveRadioService();
	private ChallenterLiveService challenterLiveService = new ChallenterLiveService();
	//private ChallenterLiveService challenterLiveService = new ChallenterLiveService();
	private ChallenterLiveBll challenterLiveBll = new ChallenterLiveBll();
	private int actionCode = 375;

	/**
	 * 活动首页
	 */
	@RequestMapping("/index")
	public String toIndex(Model model, HttpServletRequest request) {
		return "challenger/index";
	}

	/**
	 * 抽奖页
	 */
	@RequestMapping("/lotteryIndex")
	public String toLotteryIndex(Model model, HttpServletRequest request) {
		// 直播排行
//		List<ChallenterLiveBean> listChallenterLiveBeans = challenterLiveBll
//				.getTopNumChallenterLiveBean(5, "pass");
//		model.addAttribute("listChallenterLiveBeans", listChallenterLiveBeans);
//		ResultBean<List<ChallenterLiveBean>> result = challenterLiveService
//				.getTopNumChallenterLiveBean(5);
		// 直播排行 后台数据配置
		List<ChallenterLiveTimeRankBean> liveTimeRankList = challenterLiveService
				.getTopFiveChallenterLiveTimeRank();
		model.addAttribute("liveTimeRankList", liveTimeRankList);
		model.addAttribute("pageType","lottery");
		return "challenger/lotteryIndex";
	}

	/**
	 * 点击报名参赛
	 */
	@RequestMapping(value = "/entry", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> entry(Model model, HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(false, "报名接口异常", "");
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				resultBean.setMessage("请您先登录");
				return resultBean;
			}
			cn.gyyx.action.beans.novicecard.ResultBean<ActivityConfigBean> configResult = activityConfigBll
					.getConfigMessage(ChallengerConstant.HD_NAME);
			if (!configResult.getIsSuccess()) {
				resultBean.setMessage(configResult.getMessage());
				return resultBean;
			}
			ChallenterUserInfoBean u = new ChallenterUserInfoBean();
			u.setUserId(userInfo.getUserId());
			ResultBean<String> us = userInfoService.checkUserInfo(u);
			return us;
		} catch (Exception e) {
			logger.error("问道挑战新世界[点击报名参赛]接口异常", e);
		}

		return resultBean;
	}

	/**
	 * 获取用户信息
	 */
	@RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<Map<String, String>> getUserInfo(Model model,
			HttpServletRequest request) {
		ResultBean<Map<String, String>> resultBean = new ResultBean<Map<String, String>>();
		resultBean.setProperties(false, "接口异常", null);
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				resultBean.setMessage("请您先登录");
				return resultBean;
			}
			ChallenterUserInfoBean u = new ChallenterUserInfoBean();
			u.setUserId(userInfo.getUserId());
			ResultBean<Map<String, String>> us = userInfoService.getUserInfo(u);
			return us;
		} catch (Exception e) {
			logger.error("问道挑战新世界[获取用户信息]接口异常", e);
		}

		return resultBean;
	}

	/**
	 * 立即报名
	 */
	@RequestMapping(value = "/signUp", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> signUp(ChallenterUserInfoBean userInfoBean,
			Model model, HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "接口异常", "");
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				resultBean.setMessage("请您先登录");
				return resultBean;
			}
			userInfoBean.setAccount(userInfo.getAccount());
			userInfoBean.setUserId(userInfo.getUserId());
			cn.gyyx.action.beans.novicecard.ResultBean<ActivityConfigBean> configResult = activityConfigBll
					.getConfigMessage(ChallengerConstant.HD_NAME);
			if (!configResult.getIsSuccess()) {
				resultBean.setMessage(configResult.getMessage());
				return resultBean;
			}
			ResultBean<String> rs = userInfoService.entry(userInfoBean);
			return rs;
		} catch (Exception e) {
			logger.error("问道挑战新世界[立即报名]接口异常", e);
		}

		return resultBean;
	}

	/**
	 * 分享
	 */
	@RequestMapping(value = "/shareSomeThing", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> shareSomeThing(
			@RequestParam(value = "source") String type,
			HttpServletRequest request) {
		return lotteryService.shareSomeThing(type, actionCode, request);
	}
	
	/**
	 * 视频围观-页面
	 */
	@RequestMapping("/liveRadioList")
	public String liveRadioList(Model model) {
		model.addAttribute("pageType","fighter");
		return "challenger/liveRadioList";
	}
	
	/**
	 * 视频围观分页列表
	 */
	@RequestMapping("/liveRadioData")
	@ResponseBody
	public ResultBeanWithPage<List<ChallenterLiveRadioBean>> liveRadioList(@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo) {
		ChallenterLiveRadioBean bean = new ChallenterLiveRadioBean();
		bean.setPageSize(pageSize);
		bean.setCurrentPage(pageNo);
		return challenterLiveRadioService.getWebFrontChallenterLiveRadioListData(bean);
	}
	
	/**
	 * 前台视频轮播
	 */
	@RequestMapping("/getLiveRadioTopN")
	@ResponseBody
	public ResultBean<List<ChallenterLiveRadioBean>> getTopN(@RequestParam(value = "pageSize") int pageSize) {
		ChallenterLiveRadioBean bean = new ChallenterLiveRadioBean();
		bean.setPageSize(pageSize);
		bean.setIsTop(ChallengerConstant.liveRadioState.Y.toString());
		return challenterLiveRadioService.getTopN(bean);
	}
	
	
}
