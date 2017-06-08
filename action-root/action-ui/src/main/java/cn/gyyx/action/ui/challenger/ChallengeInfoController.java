/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午4:40:09
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.ui.challenger;

import java.util.List;

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
import cn.gyyx.action.beans.challenger.ChallenterLiveRankBean;
import cn.gyyx.action.beans.challenger.SeeMyChallengeRecordBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.challenger.ChallenterInfoBll;
import cn.gyyx.action.bll.challenger.ChallenterLiveBll;
import cn.gyyx.action.service.challenger.ChallengeService;
import cn.gyyx.action.service.challenger.ChallenterLiveService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/challenger/challengeinfo")
public class ChallengeInfoController {

	private ChallengeService challengeService = new ChallengeService();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChallengeInfoController.class);
	private ChallenterInfoBll challenterInfoBll = new ChallenterInfoBll();

	private ChallenterLiveService challenterLiveService = new ChallenterLiveService();
	private ChallenterLiveBll challenterLiveBll = new ChallenterLiveBll();
	int actionCode = 375;

	/**
	 * 挑战列表页
	 */
	@RequestMapping("/test")
	public String test() {
		logger.debug("fighterList");
		return "challenger/test";
	}

	/**
	 * 挑战列表页
	 */
	@RequestMapping("/fighterList")
	public String fighterList(Model model) {
		logger.debug("fighterList");
		List<ChallenterInfoBean> listChallenterInfo = challenterInfoBll
				.getTopFiveChallenterInfo();
		List<ChallenterLiveBean> listChallenterLiveBeans = challenterLiveBll
				.getTopNumChallenterLiveBean(15, "pass");
		// 生死斗排行
		List<ChallenterDeathLifeRankBean> deathLifeRankList = challenterLiveService
				.getTopFiveChallenterDeathLifeRank();
		
		// 直播场数排行
		List<ChallenterLiveRankBean> liveRankList = challenterLiveService
						.getTopFiveChallenterLiveRank();
		
		model.addAttribute("listChallenterLiveBeans", listChallenterLiveBeans);
		model.addAttribute("pageType", "fighter");
		model.addAttribute("listChallenterInfo", listChallenterInfo);
		model.addAttribute("deathLifeRankList", deathLifeRankList);
		model.addAttribute("liveRankList", liveRankList);
		return "challenger/fighterListNew";
	}

	/***
	 * 发起挑战
	 * 
	 * @return
	 */
	@RequestMapping(value = "/provocationEveryOne", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> provocationEveryOne(
			ChallenterInfoBean challenterInfoBean, HttpServletRequest request) {
		return challengeService
				.provocationEveryOne(challenterInfoBean, request);
	}

	/***
	 * 分页获取挑战信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/getAllChallenterUserInfoBean")
	@ResponseBody
	public ResultBeanWithPage<List<ChallenterInfoBean>> getAllChallenterUserInfoBean(
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo) {
		return challengeService.getAllChallenterUserInfoBean(pageSize, pageNo);
	}

	/***
	 * 对某人挑战
	 * 
	 * @param actionCode
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/challengeSomeOne")
	@ResponseBody
	public ResultBean<String> challengeSomeOne(
			@RequestParam(value = "userId") int userId,
			HttpServletRequest request) {
		return challengeService.challengeSomeOne(actionCode, userId, request);
	}

	/****
	 * 我挑战了谁
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/getMyChallengeInfo")
	@ResponseBody
	public ResultBean<SeeMyChallengeRecordBean> getMyChallengeInfo(
			HttpServletRequest request) {
		return challengeService.getMyChallengeInfo(request);
	}

	/***
	 * 谁挑战了我的信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWhoChallengeMe")
	@ResponseBody
	public ResultBean<SeeMyChallengeRecordBean> getWhoChallengeMe(
			HttpServletRequest request) {
		return challengeService.getWhoChallengeMe(request);
	}

	/***
	 * 看是否能宣战
	 * 
	 * @return
	 */
	@RequestMapping(value = "/isCanDeclareWar")
	@ResponseBody
	public ResultBean<String> isCanDeclareWar(HttpServletRequest request) {
		return challengeService.isCanDeclareWar(request);
	}

	/**
	 * 增加直播信息
	 * 
	 * @param challenterLiveBean
	 */
	@RequestMapping(value = "/addChallenterLive", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> addChallenterLive(
			ChallenterLiveBean challenterLiveBean, HttpServletRequest request) {
		return challenterLiveService.addChallenterLive(actionCode,
				challenterLiveBean, request);
	}

	/***
	 * 获取前几条直播信息
	 * 
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/getTopNumChallenterLiveBean")
	@ResponseBody
	public ResultBean<List<ChallenterLiveBean>> getTopNumChallenterLiveBean(
			@RequestParam(value = "size") int size) {
		return challenterLiveService.getTopNumChallenterLiveBean(size);

	}

	/**
	 * 获取五条直播预告信息
	 * 
	 * @param actionCode
	 * @return
	 */
	@RequestMapping(value = "/getFiveLive")
	@ResponseBody
	public String getFiveLive() {
		return challengeService.getFiveLive(actionCode);
	}

}
