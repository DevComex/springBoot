/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午9:59:22
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.oa.challenter;

import java.io.IOException;
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
import cn.gyyx.action.beans.challenger.ChallenterLiveRadioBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveRankBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveTimeRankBean;
import cn.gyyx.action.beans.challenger.ChallenterTeamBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.beans.challenger.SameDataBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.challenger.SameDataBll;
import cn.gyyx.action.service.challenger.ChallengeService;
import cn.gyyx.action.service.challenger.ChallengerConstant;
import cn.gyyx.action.service.challenger.ChallenterLiveRadioService;
import cn.gyyx.action.service.challenger.ChallenterLiveService;
import cn.gyyx.action.service.challenger.TeamModuleService;
import cn.gyyx.action.service.challenger.UserInfoService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;

@Controller
@RequestMapping(value = "/challenteroa")
public class ChallenterController {
	private int actionCode = 375;
	private UserInfoService userService = new UserInfoService();
	private TeamModuleService teamModuleService = new TeamModuleService();
	private ChallengeService challengeService = new ChallengeService();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChallenterController.class);
	private ChallenterLiveService challenterLiveService = new ChallenterLiveService();
	private ChallenterLiveRadioService challenterLiveRadioService = new ChallenterLiveRadioService();

	private SameDataBll sameDataBll = new SameDataBll();

	@RequestMapping(value = "/checkUserInfo")
	public String checkUserInfo() {
		logger.debug("checkUserInfo");
		return "challenter/userinfocheck";

	}

	@RequestMapping(value = "/checkchallengeinfo")
	public String challengecheck() {
		logger.debug("checkUserInfo");
		return "challenter/challengecheck";

	}

	@RequestMapping(value = "/checkchallenterLive")
	public String checkchallenterLive() {
		logger.debug("challenterLive");
		return "challenter/challenterLive";

	}

	/***
	 * 审核通过用户报名信息
	 * 
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = "/passUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> passUserInfo(
			@RequestParam(value = "userCode") int userCode,
			HttpServletRequest request) throws IOException {
		/* try { */
		OAUserInfoModel userInfoModel = OAUserRequest
				.getUserInfoByRequest(request);
		return userService.passUserInfo(userCode, userInfoModel.getStaffCode(),
				userInfoModel.getRealName(), actionCode);
		/*
		 * } catch (Exception e) { logger.warn("passUserInfo" + e); return new
		 * ResultBean<>(false, "您没登录", ""); }
		 */
	}

	/***
	 * 拒绝用户报名信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/refuseUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> refuseUserInfo(
			@RequestParam(value = "userCode") int userCode,
			HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			return userService.refuseUserInfo(userCode,
					userInfoModel.getStaffCode(), userInfoModel.getRealName(),
					actionCode);
		} catch (Exception e) {
			logger.warn("refuseUserInfo" + e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/***
	 * 分页获取所有未通过的信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/getAllUnPassChallenterUserInfo")
	@ResponseBody
	public ResultBeanWithPage<List<ChallenterUserInfoBean>> getAllUnPassChallenterUserInfo(
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo) {
		return userService.getAllUnPassChallenterUserInfo(pageSize, pageNo);
	}

	/**
	 * 队伍创建审核
	 */
	@RequestMapping(value = "/teamlist")
	public String teamlist() {
		return "challenter/teamlist";
	}

	/***
	 * 分页获取队伍创建审核
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/getTeamListData")
	@ResponseBody
	public ResultBeanWithPage<List<ChallenterTeamBean>> getTeamListData(
			ChallenterTeamBean bean,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo) {
		bean.setPageSize(pageSize);
		bean.setCurrentPage(pageNo);
		bean.setState(ChallengerConstant.teamState.oncheck.toString());
		return teamModuleService.getTeamListData(bean);
	}

	/***
	 * 审核队伍创建申请
	 */

	@RequestMapping(value = "/reviewCreateTeamApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> reviewCreateTeamApply(
			@RequestParam(value = "code") int code,
			@RequestParam(value = "state") String state,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return teamModuleService.reviewCreateTeamApply(code, state,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/***
	 * 审核通过挑战信息
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/passChallengeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> passChallengeInfo(
			@RequestParam(value = "code") int code, HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challengeService.passChallengeInfo(code, actionCode,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			logger.error("passChallengeInfo" + e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/***
	 * 拒绝挑战信息
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/refuseChallengeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> refuseChallengeInfo(
			@RequestParam(value = "code") int code, HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challengeService.refuseChallengeInfo(code, actionCode,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			logger.error("passChallengeInfo" + e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/***
	 * 分页获取未审核的挑战信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/getAllChallenterBean")
	@ResponseBody
	public ResultBeanWithPage<List<ChallenterInfoBean>> getAllChallenterBean(
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo) {
		return challengeService.getAllChallenterBean(pageSize, pageNo);
	}

	/**
	 * 根据页码获取未审核直播信息
	 * 
	 * @param size
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/getChallenterLiveBeansPage")
	@ResponseBody
	public ResultBeanWithPage<List<ChallenterLiveBean>> getChallenterLiveBeansPage(
			@RequestParam(value = "pageSize") int size,
			@RequestParam(value = "pageIndex") int pageNo) {
		return challenterLiveService.getChallenterLiveBeansPage(size, pageNo);

	}

	/***
	 * 审核通过直播信息
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/refuseLiveInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> refuseLiveInfo(
			@RequestParam(value = "code") int code, HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveService.refuseLiveInfo(
					userInfoModel.getStaffCode(), userInfoModel.getRealName(),
					code, actionCode);
		} catch (Exception e) {
			logger.error("refuseLiveInfof" + e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/***
	 * 审核通过直播信息
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/passLiveInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> passLiveInfo(
			@RequestParam(value = "code") int code, HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveService.passLiveInfo(
					userInfoModel.getStaffCode(), userInfoModel.getRealName(),
					code, actionCode);
		} catch (Exception e) {
			logger.error("passLiveInfo" + e);
			return new ResultBean<>(false, "您没登录", "");
		}

	}

	/***
	 * 得到五条直播信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFiveLive")
	@ResponseBody
	public String getFiveLive() {
		SameDataBean sameDataBean = sameDataBll.getSameDataBean("fivelive",
				actionCode);
		if (sameDataBean != null) {
			return sameDataBean.getContent();
		} else {
			return "";
		}

	}

	/**
	 * 自定义数据
	 */
	@RequestMapping(value = "/sampleDatalist")
	public String sampleDatalist(Model model) {
		List<ChallenterLiveRankBean> liveRankList = challenterLiveService
				.getTopFiveChallenterLiveRank();
		List<ChallenterDeathLifeRankBean> deathLifeRankList = challenterLiveService
				.getTopFiveChallenterDeathLifeRank();
		List<ChallenterLiveTimeRankBean> liveTimeRankList = challenterLiveService
				.getTopFiveChallenterLiveTimeRank();

		model.addAttribute("liveRankList", liveRankList);
		model.addAttribute("deathLifeRankList", deathLifeRankList);
		model.addAttribute("liveTimeRankList", liveTimeRankList);
		return "challenter/sampleDatalist";
	}

	/***
	 * 保存直播排行榜
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/saveLiveRank", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> saveLiveRank(String[] player, String[] liveCount,
			String[] channel, String[] winCount, HttpServletRequest request) {
		try {
			ChallenterLiveRankBean bean = new ChallenterLiveRankBean();
			bean.setPlayerString(player);
			bean.setLiveCountString(liveCount);
			bean.setChannelString(channel);
			bean.setWinCountString(winCount);
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveService.saveLiveRank(
					userInfoModel.getStaffCode(), userInfoModel.getRealName(),
					bean);
		} catch (Exception e) {
			logger.error("saveLiveRank", e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/***
	 * 保存生死斗排行榜
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/saveDeathLifeRank", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> saveDeathLifeRank(String[] player,
			String[] attendCount, String[] winCount, HttpServletRequest request) {
		try {
			ChallenterDeathLifeRankBean bean = new ChallenterDeathLifeRankBean();
			bean.setPlayerString(player);
			bean.setAttendCountString(attendCount);
			bean.setWinCountString(winCount);
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveService.saveDeathLifeRank(
					userInfoModel.getStaffCode(), userInfoModel.getRealName(),
					bean);
		} catch (Exception e) {
			logger.error("saveDeathLifeRank", e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}
	
	/***
	 * 保存直播时间排行榜
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/saveliveTimeRank", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> saveliveTimeRank(String[] playerA,String[] playerB,
			String[] liveTime, String[] liveRadio, HttpServletRequest request) {
		try {
			ChallenterLiveTimeRankBean bean = new ChallenterLiveTimeRankBean();
			bean.setPlayerAString(playerA);
			bean.setPlayerBString(playerB);
			bean.setLiveTimeString(liveTime);
			bean.setLiveRadioString(liveRadio);
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveService.saveliveTimeRank(
					userInfoModel.getStaffCode(), userInfoModel.getRealName(),
					bean);
		} catch (Exception e) {
			logger.error("saveDeathLifeRank", e);
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/***
	 * 分页获取围观视频列表
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/getLiveRadioData", method = RequestMethod.GET)
	@ResponseBody
	public ResultBeanWithPage<List<ChallenterLiveRadioBean>> getLiveRadioData(
			ChallenterLiveRadioBean bean,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "pageIndex") int pageNo) {
		bean.setPageSize(pageSize);
		bean.setCurrentPage(pageNo);
		return challenterLiveRadioService.getChallenterLiveRadioListData(bean);
	}

	/**
	 * 围观视频列表页
	 */
	@RequestMapping(value = "/liveradiolist")
	public String liveradiolist() {
		return "challenter/liveradiolist";
	}

	/**
	 * 视频是否推荐
	 */
	@RequestMapping(value = "/topOperator", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> topOperator(
			@RequestParam(value = "code") int code,
			@RequestParam(value = "state") String state,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveRadioService.topOperator(code, state,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 视频add
	 */
	@RequestMapping(value = "/liveRadioAdd", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> liveRadioAdd(ChallenterLiveRadioBean bean,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveRadioService.insert(bean,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 视频修改
	 */
	@RequestMapping(value = "/liveRadioMod", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> liveRadioMod(ChallenterLiveRadioBean bean,
			HttpServletRequest request) throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveRadioService.update(bean,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 视频删除
	 */
	@RequestMapping(value = "/liveRadioDel", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> liveRadioDel(
			@RequestParam(value = "code") int code, HttpServletRequest request)
			throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "您没登录,请先登录", "");
			}
			return challenterLiveRadioService.deleteRadio(code,
					userInfoModel.getStaffCode(), userInfoModel.getRealName());
		} catch (Exception e) {
			return new ResultBean<>(false, "您没登录", "");
		}
	}

	/**
	 * 获取视频-根据ID
	 */
	@RequestMapping(value = "/getLiveRadio", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<ChallenterLiveRadioBean> getLiveRadio(
			@RequestParam(value = "code") int code, HttpServletRequest request)
			throws IOException {
		try {
			OAUserInfoModel userInfoModel = OAUserRequest
					.getUserInfoByRequest(request);
			if (userInfoModel == null) {
				return new ResultBean<ChallenterLiveRadioBean>(false,
						"您没登录,请先登录", null);
			}
			return challenterLiveRadioService.getChallenterLiveRadioBean(code);
		} catch (Exception e) {
			return new ResultBean<ChallenterLiveRadioBean>(false, "您没登录", null);
		}
	}

	@RequestMapping(value = "/setFiveLive", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> setFiveLive(
			@RequestParam(value = "time[]") String[] time,
			@RequestParam(value = "playerOne[]") String[] playerOne,
			@RequestParam(value = "playerTwo[]") String[] playerTwo,
			@RequestParam(value = "radio[]") String[] radio) {
		return challengeService.setFiveLive(actionCode, time, playerOne,
				playerTwo, radio);

	}
	
	
}
