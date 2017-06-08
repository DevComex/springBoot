package cn.gyyx.action.ui.challenger;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.ChallenterTeamBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.service.challenger.TeamModuleService;
import cn.gyyx.action.service.challenger.ChallengerConstant;
import cn.gyyx.action.service.challenger.UserInfoService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @Description: 问道挑战新世界队伍控制器
 * @author 成龙
 * @date 2016年7月12日 下午19:00:00
 */
@Controller
@RequestMapping(value = "/challenger")
public class ChallengerTeamController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChallengerTeamController.class);
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private TeamModuleService teamModuleService = new TeamModuleService();
	private UserInfoService userInfoService = new UserInfoService();
	
	/**
	 * 队伍首页
	 */
	@RequestMapping("/teamList")
	public String toIndex(Model model, HttpServletRequest request) {
		model.addAttribute("pageType","teamlist");
		return "challenger/teamList";
	}

	/**
	 * 队伍详情
	 */
	@RequestMapping("/teamInfo")
	public String teamInfo(Model model, HttpServletRequest request) {
		try {
			model.addAttribute("pageType","myteam");
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo != null) {
				ChallenterUserInfoBean s = userInfoService.getUserInfoBean(userInfo.getUserId());
				model.addAttribute("useinfo", s);
				ChallenterTeamBean rs1 = teamModuleService.getUserTeamInfo(userInfo.getUserId());
				if(rs1 != null){
					model.addAttribute("team_msg", "");//有队伍
					model.addAttribute("sourceSrcUrl", "/challenger/teamDetails/"+rs1.getCode());//有队伍
					if(rs1.getUserId() == userInfo.getUserId()){
						model.addAttribute("is_leader", "Y");//是队长
						
						model.addAttribute("teamInfo",rs1);
						
						//我的队伍信息
						List<ChallenterUserInfoBean> mylist = teamModuleService.getUserTeamMembers(rs1.getCode());
						model.addAttribute("member_list", mylist);//队员列表
						
						//队伍申请
						List<ChallenterUserInfoBean> applylist = teamModuleService.getUserTeamApplyLogList(rs1.getCode());
						model.addAttribute("apply_list", applylist);//队员申请
					}else{
						rs1 = teamModuleService.getTeam(rs1.getCode(),true);
						model.addAttribute("teamInfo",rs1);
						model.addAttribute("is_leader", "N");
					}
				}else{
					rs1 = teamModuleService.getUserCreateTeam(userInfo.getUserId());
					if(rs1 != null){
						if (ChallengerConstant.teamState.oncheck.toString().equals(
								rs1.getState())) {
							model.addAttribute("team_msg", "创建审核中,请耐心等待");
						} 
					}else{
						//有没有队伍申请 
						int c = teamModuleService.getUserApplyTeamCount(userInfo.getUserId());
						if(c > 0){
							model.addAttribute("team_msg", "申请队伍审核中,请耐心等待");
						}else{
							model.addAttribute("team_msg", "noTeam");//没有队伍
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("问道挑战新世界[队伍详情页]接口异常", e);
		}
		return "challenger/teamInfo";
	}
	
	/**
	 * 队伍详细信息
	 */
	@RequestMapping("/teamDetails/{teamId}")
	public String teamDetails(@PathVariable("teamId") int teamId,Model model, HttpServletRequest request) {
		model.addAttribute("pageType","teamDetails");
		try {
			ChallenterTeamBean rs1 = teamModuleService.getTeam(teamId, true);
			model.addAttribute("teamInfo", rs1);
		} catch (Exception e) {
			logger.error("问道挑战新世界[队伍详细信息页]异常", e);
		}
		return "challenger/teamDetails";
	}
	
	/**
	 * 我的队伍-检测提示
	 */
	@RequestMapping("/entryTeamInfo")
	@ResponseBody
	public ResultBean<String> entryTeamInfo(Model model, HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(false, "检测是否创建申请队伍异常", "");
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
			ResultBean<String> rs1 = teamModuleService.entryTeamInfo(u);
			return rs1;
		} catch (Exception e) {
			logger.error("问道挑战新世界[检测是否创建申请队伍]接口异常", e);
		}

		return resultBean;
	}

	/**
	 * 创建队伍-检测是否可以校验
	 */
	@RequestMapping(value = "/entryCreateTeam", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> entryCreateTeam(Model model, HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(false, "检测是否可以创建队伍接口异常", "");
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
			ResultBean<String> rs1 = teamModuleService.entryCreateTeam(u);
			return rs1;
		} catch (Exception e) {
			logger.error("问道挑战新世界[检测是否可以创建队伍]接口异常", e);
		}

		return resultBean;
	}
	
	/**
	 * 创建队伍
	 */
	@RequestMapping(value = "/createTeam", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> createTeam(ChallenterTeamBean teamBean,Model model, HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(false, "创建队伍接口异常", "");
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
			u.setAccount(userInfo.getAccount());
			ResultBean<String> rs1 = teamModuleService.createTeam(u,teamBean);
			return rs1;
		} catch (Exception e) {
			logger.error("问道挑战新世界[创建队伍]接口异常", e);
		}

		return resultBean;
	}
	
	/**
	 * 队伍列表
	 */
	@RequestMapping(value = "/teamData", method = { RequestMethod.GET })
	@ResponseBody
	public ResultBeanWithPage<List<ChallenterTeamBean>> teamList(@RequestParam int pageIndex,@RequestParam int pageSize,Model model, HttpServletRequest request) {
		ResultBeanWithPage<List<ChallenterTeamBean>> resultBean = new ResultBeanWithPage<>();
		resultBean.setProperties(false, "获取队伍列表异常", null, 0);
		try {
			ResultBeanWithPage<List<ChallenterTeamBean>> rs1 = teamModuleService.teamList(pageIndex,pageSize);
			return rs1;
		} catch (Exception e) {
			logger.error("问道挑战新世界[获取队伍列表异常]接口异常", e);
		}

		return resultBean;
	}
	
	/**
	 * 申请加入队伍 
	 */
	@RequestMapping(value = "/applyTeam", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> applyTeam(@RequestParam int teamId,Model model, HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(false, "申请加入队伍异常", null);
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
			ResultBean<String> rs1 = teamModuleService.applyTeam(teamId,u);
			return rs1;
		} catch (Exception e) {
			logger.error("问道挑战新世界[申请加入队伍异常]接口异常", e);
		}

		return resultBean;
	}
	
	/**
	 * 队伍详情信息 根据id查询 
	 */
	@RequestMapping(value = "/teamDetail", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<ChallenterTeamBean> teamDetail(@RequestParam int teamId,Model model, HttpServletRequest request) {
		ResultBean<ChallenterTeamBean> resultBean = new ResultBean<ChallenterTeamBean>();
		resultBean.setProperties(false, "获取队伍详情信息接口异常", null);
		try {
			ResultBean<ChallenterTeamBean> rs1 = teamModuleService.teamDetail(teamId);
			return rs1;
		} catch (Exception e) {
			logger.error("问道挑战新世界[获取队伍详情信息]接口异常", e);
		}

		return resultBean;
	}
	
	/**
	 * 队伍申请审核 
	 */
	@RequestMapping(value = "/teamApplyReview", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> teamDetail(int applyId,String status,Model model, HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(false, "队伍申请审核接口异常", null);
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
			
			ResultBean<String> rs1 = teamModuleService.teamApplyReview(u,applyId,status);
			return rs1;
		} catch (Exception e) {
			logger.error("问道挑战新世界[队伍申请审核接口异常]接口异常", e);
		}

		return resultBean;
	}
}
