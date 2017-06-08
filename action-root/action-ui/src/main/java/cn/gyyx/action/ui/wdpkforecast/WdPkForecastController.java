package cn.gyyx.action.ui.wdpkforecast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkRoleBindBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkVoteLogBean;

@Controller
@RequestMapping("/wdpkforecast")
public class WdPkForecastController {
	public static final int ACTIONCODE = 400;

	/***
	 * 主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "WdPkForecast/index";
	}

	/*
	 * 抽奖主页
	 */
	@RequestMapping("/lottery")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "WdPkForecast/lottery";
	}

	@RequestMapping(value = "/getType", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public int getType(HttpServletRequest request) {
		return 0;
	}

	/**
	 * 
	 * @Title: getServers @Description: TODO 获取服务器信息 @param @param
	 *         typeCode @param @return @return ResultBean<List
	 *         <ServerBean>> @throws
	 */
	@RequestMapping(value = "/getServers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<List<String>> getServers(@RequestParam(value = "netType") String typeCode) {
		return new ResultBean<List<String>>(false, "谢谢参与，活动已结束", null);
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
	@RequestMapping(value = "/getRoles", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> getRoles(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "serverId") int serverId, @RequestParam(value = "veCode") String veCode) {
		return new ResultBean<String>(false, "谢谢参与，活动已结束", null);
	}

	/**
	 * 
	 * @throws ParseException
	 * @Title: addRole @Description: TODO 绑定角色 @param @param
	 *         wdAccountInfoBean @param @param request @param @return @return
	 *         ResultBean<WdAccountInfoBean> @throws
	 */
	@RequestMapping(value = "/addRole", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> addRole(@ModelAttribute @Validated WdPkRoleBindBean wdPkRoleBindBean,
			HttpServletRequest request, BindingResult br) throws ParseException {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getWdAccountInfoBeanByAccountName @Description:
	 *         TODO判断是否绑定过角色，并获取绑定角色信息 @param @param accountName @param
	 *         request @return ResultBean <WdAccountInfoBean> @throws
	 */
	@RequestMapping(value = "/checkRole", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> getWdAccountInfoBeanByAccountName(HttpServletRequest request)
			throws ParseException {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	/***
	 * 增加评论
	 * 
	 * @param nominationCode
	 * @param commentsContent
	 * @return
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> addComment(@RequestParam(value = "commentsContent") String commentsContent, HttpServletRequest request) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	/**
	 * 
	 * @Title: getComments @Description: TODO 获取所有评论 @param @return @return
	 *         ResultBean<List<WdCommentsBean>> @throws
	 */
	@RequestMapping(value = "/getComments", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<List<String>> getComments(HttpServletRequest request) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	/**
	 * 分阶段预测 第一阶段,预测人数最多区组
	 */
	@RequestMapping(value = "/forecastone", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> forecastone(HttpServletRequest request,
			@ModelAttribute WdPkVoteLogBean wdPkVoteLogBean) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);

	}

	/**
	 * 分阶段预测 第二阶段,预测人数最多区组
	 */
	@RequestMapping(value = "/forecasttwo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> forecasttwo(HttpServletRequest request,
			@ModelAttribute WdPkVoteLogBean wdPkVoteLogBean) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);

	}

	/** 
	 * 分阶段预测 第三阶段,预测人数最多区组
	 */
	@RequestMapping(value = "/forecastthree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> forecastthree(HttpServletRequest request,
			@ModelAttribute WdPkVoteLogBean wdPkVoteLogBean) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);

	}

	/**
	 * 分阶段预测 第四阶段,预测人数最多区组
	 */
	@RequestMapping(value = "/forecastfour", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> forecastfour(HttpServletRequest request,
			@ModelAttribute WdPkVoteLogBean wdPkVoteLogBean) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);

	}

	/**
	 * 查询投票机会
	 * 
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "/votetimes", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Integer> votetimes(HttpServletRequest request) throws ParseException {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	/**
	 * 查询投票投中情况
	 *
	 * 
	 */
	@RequestMapping(value = "/checkvotestatus", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<List<String>> checkvotestatus(HttpServletRequest request) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);

	}

	@RequestMapping(value = "/getAllTeams", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<List<String>> getALLVoteTeams(HttpServletRequest request, int Type) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	@RequestMapping(value = "/checkServerTimes", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> checkServerTimes(HttpServletRequest request,
			@ModelAttribute WdPkVoteLogBean wdPkVoteLogBean) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	/******************************************** 抽奖操作 **********************************************/

	@RequestMapping(value = "/getLottery")
	@ResponseBody
	public ResultBean<String> getPredicableLottery(HttpServletRequest request, int lotteryType) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
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
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	/**
	 * 获取所有用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfo")
	@ResponseBody
	public ResultBean<List<Map<String,Object>>> getAllLotteryInfo(HttpServletRequest request, int type) {
	
		List<Map<String,Object>> list=new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Map<String,Object> map=new HashMap<>();
			map.put("roleName", "屠戮者123");
			map.put("serverName", "北京古都");
			map.put("prizeName", "酒葫芦铭牌");
			list.add(map);
		}
		return new ResultBean<>(true, "查询成功", list);
	}

	// 查询地址
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean<String> getAddress(HttpServletRequest request) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	/**
	 * 
	 * @日期：2016年10月09日 @Title: resetAddress
	 * @Description: 插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/setAddress", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address, HttpServletResponse response,
			HttpServletRequest request) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

	@RequestMapping(value = "/getlotteryTimes", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<Map<String, Integer>> lotteryTimes(HttpServletRequest request) {
		return new ResultBean<>(false, "活动已经结束，谢谢参与", null);
	}

}
