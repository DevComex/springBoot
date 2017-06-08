/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年12月15日下午4:12:22
 * 版本号：v1.0
 * 本类主要用途叙述：圣诞节店点亮活动的控制器
 */

package cn.gyyx.action.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.service.newLottery.LotteryException;

@Controller
@RequestMapping(value = "/ChristmasLight")
public class ChristmasLightController {


	
	/***
	 * 页面跳转
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/index ")
	public String toIndex(Model model, HttpServletRequest request) {
		return "ChristmasLight/index";
	}

	/****
	 * 得到比率
	 * 
	 * @param parm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProportion ")
	@ResponseBody
	public ResultBean<List<String>> getProportion(
			HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getServers
	 * @Description: TODO 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<List<String>> getServers(
			@RequestParam(value = "netType") String typeCode) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
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
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/***
	 * 得到账户的角色信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAccountRole")
	@ResponseBody
	public ResultBean<String> getAccountRoleBeanByUserCode(
			HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/****
	 * 增加账户角色绑定信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addAccountRole", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> addAccountRole(HttpServletRequest request,
			@RequestParam(value = "roleName") String roleName,
			@RequestParam(value = "roleCode") String roleCode,
			@RequestParam(value = "serverName") String serverName,
			@RequestParam(value = "serverCode") int serverCode) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/***
	 * 获取指定层的祝愿信息
	 * 
	 * @param request
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/getLevelComment")
	@ResponseBody
	public ResultBean<List<String>> getLevelComment(
			HttpServletRequest request, @RequestParam(value = "level") int level) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);

	}

	/***
	 * 获取层级信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLevel")
	@ResponseBody
	public ResultBean<List<Integer>> getLevel(HttpServletRequest request) {
		List<Integer> integers=new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			integers.add(i);
		}
		return new ResultBean<>(true, "谢谢参与，活动已结束！", integers);
	}

	/***
	 * 获取分数
	 * 
	 * @param level
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getScore")
	@ResponseBody
	public ResultBean<Integer> getScore(
			@RequestParam(value = "level") int level, HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/***
	 * 获取用户剩余的分数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMyScore")
	@ResponseBody
	public ResultBean<Integer> getMyScore(HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);

	}

	/***
	 * 获取用户剩余的分数
	 * 
	 * @param 许愿并抽个奖
	 * @return
	 * @throws LotteryException
	 */
	@RequestMapping(value = "/wishAndLottery", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> wishAndLottery(
			HttpServletRequest request,
			@RequestParam(value = "level") int level,
			@RequestParam(value = "comment") String comment)
			throws LotteryException {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getAddress
	 * @Description: TODO 传递用户地址信息
	 * @param request
	 * @return ResultBean<AddressBean>
	 */
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean<String> getAddress(HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: resetAddress
	 * @Description: TODO 重置或者插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/resetAddress")
	@ResponseBody
	public ResultBean<Integer> resetAddress(
			@ModelAttribute AddressBean address, HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<String>> getAllLotteryInfoByUser(
			HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/****
	 * 获取用户的祝愿
	 * 
	 * @param userCode
	 * @param sqlSession
	 * @return
	 */
	@RequestMapping(value = "/getCommentByUser")
	@ResponseBody
	public ResultBean<List<String>> getCommentByUser(
			HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 是否可以祝愿
	 * 
	 * @param sqlSession
	 * @param level
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/isWish")
	@ResponseBody
	public ResultBean<String> isWish(@RequestParam(value = "level") int level,
			HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);

	}

}
