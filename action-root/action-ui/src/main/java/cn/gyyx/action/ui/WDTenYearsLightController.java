/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月17日下午1:19:11
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/wdtenyearslight")
public class WDTenYearsLightController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WDTenYearsLightController.class);

	

	/***
	 * 页面跳转
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/index ")
	public String toIndex() {
		logger.info("");
		return "wdTenYear/index";
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
	public ResultBean<List<Map<String, Object>>> getProportion() {
		List<Map<String, Object>> list =new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			Map<String, Object> map=new HashMap<>();
			map.put("level", i+1);
			map.put("poroportion", 100);
			list.add(map);
		}
		return new ResultBean<>(true, "谢谢参与，活动已结束！", list);
	}

	/**
	 * 
	 * @日期：2015年3月20日 @Title: getServers 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<String> getServers(@RequestParam(value = "netType") String typeCode) {
		logger.info("typeCode", typeCode);
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
	public ResultBean<String> changeRoleInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "serverId") int serverId, @RequestParam(value = "veCode") String veCode) {
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
	public ResultBean<String> getAccountRoleBeanByUserCode(HttpServletRequest request) {
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
			@RequestParam(value = "roleName") String roleName, @RequestParam(value = "roleCode") String roleCode,
			@RequestParam(value = "serverName") String serverName, @RequestParam(value = "serverCode") int serverCode) {
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
	public ResultBean<List<String>> getLevelComment(@RequestParam(value = "level") int level) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 
	 * @日期：2015年3月20日 @Title: getAddress 传递用户地址信息
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
	 * @日期：2015年3月20日 @Title: resetAddress
	 * @重置或者插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/resetAddress")
	@ResponseBody
	public ResultBean<Integer> resetAddress(@ModelAttribute AddressBean address, HttpServletRequest request) {
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
	public ResultBean<List<String>> getAllLotteryInfoByUser(HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfo")
	@ResponseBody
	public ResultBean<List<String>> getAllLotteryInfor() {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
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
	public ResultBean<Integer> getScore(@RequestParam(value = "level") int level, HttpServletRequest request) {
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
	public ResultBean<List<Integer>> getMyScore(HttpServletRequest request) {
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
	public ResultBean<List<Integer>> getLevel() {
		List<Integer> integers=new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			integers.add(i);
		}
		return new ResultBean<>(true, "谢谢参与，活动已结束！", integers);

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
	public ResultBean<List<String>> getCommentByUser(HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/***
	 * 获取用户剩余的分数
	 * 
	 * @param 许愿并抽个奖
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wishAndLottery", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> wishAndLottery(HttpServletRequest request,
			@RequestParam(value = "level") int level, @RequestParam(value = "comment") String comment) {
		return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

}
