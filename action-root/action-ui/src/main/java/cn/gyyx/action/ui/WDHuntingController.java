/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月9日下午2:38:59
 * 版本号：v1.0
 * 本类主要用途叙述：问道十周年狩猎答谢会活动
 * 创建时间：2016年5月9日下午5:14:09
 */

package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: WD10YearFansController
 * @Description: TODO 问道狩猎答谢会控制器
 *
 * @date 2016年3月29日 下午5:55:45
 *
 */
@Controller
@RequestMapping(value = "/wdhunting")
public class WDHuntingController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDHuntingController.class);
	private int actionCode = 316;

	/**
	 * 跳转到中奖界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String  toLottery() {
		logger.debug("toLottery");
		return "wdhunts/indexv2";
	}

	/***
	 * 判断活动是否结束
	 * 
	 * @return
	 */
	@RequestMapping(value = "/isActionOver")
	@ResponseBody
	public ResultBean<String> isActionOver() {
		return  new ResultBean<>(false, "该活动已结束", null);
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getServers 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<List<String>> getServers(
			@RequestParam(value = "netType") String typeCode) {
		logger.info("typeCode", typeCode);
		return  new ResultBean<>(false, "该活动已结束", null);
	}

	/***
	 * 获取角色信息
	 * 
	 * @param request
	 * @param response
	 * @param serverId
	 * @param veCode
	 * @return
	 */
	@RequestMapping(value = "/changeRoleInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> changeRoleInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "serverId") int serverId,
			@RequestParam(value = "veCode") String veCode) {
		return  new ResultBean<>(false, "该活动已结束", null);
	}
	/***
	 * 获取用户的中奖信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<String>> getAllLotteryInfoByUser(
			HttpServletRequest request) {
		return  new ResultBean<>(false, "该活动已结束", null);
	}

	/***
	 * 绑定角色信息
	 * 
	 * @param request
	 * @param roleCode
	 * @param roleName
	 * @param serverCode
	 * @param serverName
	 * @return
	 */
	@RequestMapping(value = "/addRoleAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> addRoleAccount(HttpServletRequest request,
			@RequestParam(value = "roleName") String roleName,
			@RequestParam(value = "serverCode") int serverCode,
			@RequestParam(value = "serverName") String serverName,
			@RequestParam(value = "level") int level) {
		return  new ResultBean<>(false, "该活动已结束", null);
	}

	/***
	 * 报名并抽奖
	 * 
	 * @param actionCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/signAndLottery", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> signAndLottery( HttpServletRequest request) {
		return  new ResultBean<>(false, "该活动已结束", null);
	}

	/****
	 * 判断用户是否绑定角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/isBindRole")
	@ResponseBody
	public ResultBean<String> isBindRole(HttpServletRequest request) {
		return  new ResultBean<>(false, "该活动已结束", null);
	}
}
