/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月6日上午10:35:14
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */
package cn.gyyx.action.ui;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/wd10years")
public class WD10YearsFamousController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WD10YearsFamousController.class);
	
	@RequestMapping("/famous")
	public String toIndex() {
		logger.info("famous");
		return "wd10yearfamous/wdfamouslottery";
	}
	
	@RequestMapping("/oldback")
	public String oldback() {
		logger.info("oldback");
		return "wd10yearfamous/oldback";
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
	public ResultBean getServers(
			@RequestParam(value = "netType") String typeCode) {
		ResultBean result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
		return result;
	}

	/***
	 * 名人争霸抽个大奖
	 * 
	 * @param serverName
	 * @param serverCode
	 * @param actionCode
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/goFamousLottery", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean goFamousLottery(
			@RequestParam(value = "serverName") String serverName,
			@RequestParam(value = "serverCode") int serverCode,
			HttpServletRequest request) {
		ResultBean resultBean =new ResultBean<>(false, "谢谢参与，活动已结束", null);
		return resultBean;
	}

	/***
	 * 问道缘抽奖
	 * 
	 * @param serverName
	 * @param serverCode
	 * @param actionCode
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/goFateLottery", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean goFateLottery(
			@RequestParam(value = "serverName") String serverName,
			@RequestParam(value = "serverCode") int serverCode,
			HttpServletRequest request) {
		ResultBean resultBean= new ResultBean<>(false, "谢谢参与，活动已结束", null);
		return resultBean;
	}

	/**
	 * 添加地址
	 * 
	 * @param model
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/resetAddress", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean resetAddress(AddressBean address,
			HttpServletRequest request) {
		
		ResultBean resultBean= new ResultBean<>(false, "谢谢参与，活动已结束", null);

		return resultBean;
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getAddress 传递用户地址信息
	 * @param request
	 * @return ResultBean<AddressBean>
	 */
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean getAddress(
			@RequestParam(value = "actionCode") int actionCode,
			HttpServletRequest request) {
		ResultBean resultBean= new ResultBean<>(false, "谢谢参与，活动已结束", null);

		return resultBean;
	}

	/***
	 * 获取中奖信息
	 * 
	 * @param actionCode
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfo")
	@ResponseBody
	public ResultBean getAllLotteryInfo(
			@RequestParam(value = "actionCode") int actionCode) {
		ResultBean resultBean= new ResultBean<>(false, "谢谢参与，活动已结束", null);

		return resultBean;
	}

	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean getAllLotteryInfoByUser(
			HttpServletRequest request,
			@RequestParam(value = "actionCode") int actionCode) {
		ResultBean resultBean= new ResultBean<>(false, "谢谢参与，活动已结束", null);

		return resultBean;
	}
}
