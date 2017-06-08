package cn.gyyx.action.ui;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/oldplayerlottery")
public class WdOldPlayerLotteryController {

	private static final Logger logger = GYYXLoggerFactory.getLogger(WdOldPlayerLotteryController.class);

	public static final int ACTIONCODE = 396;

	/***
	 * 主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "oldplayerlottery/index";
	}

	/**
	 * 
	 * @日期：2015年3月20日 @Title: getServers
	 * @Description: 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(@RequestParam(value = "netType") String typeCode) {
		return new ResultBean<>(false, "活动已结束，感谢参与！", null);
	}

	/**
	 * 
	 * @日期：2015年3月20日 @Title: getAddress
	 * @Description: 传递用户地址信息
	 * @param request
	 * @return ResultBean<AddressBean>
	 */
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean<AddressBean> getAddress(HttpServletRequest request) {
		return new ResultBean<>(false, "活动已结束，感谢参与！", null);
	}

	/**
	 * 
	 * @日期：2015年3月20日 @Title: resetAddress
	 * @Description: 重置或者插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/resetAddress", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> resetAddress(@ModelAttribute AddressBean address, HttpServletRequest request) {
		return new ResultBean<>(false, "活动已结束，感谢参与！", null);
	}

	/**
	 * 得到当前活动所有中奖的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfo")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfo() {
		return new ResultBean<>(false, "活动已结束，感谢参与！", null);
	}

	/***
	 * 抽奖
	 * 
	 * @param request
	 * @param serverName
	 * @param serverCode
	 * @return
	 * @throws LotteryException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getLottery", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<UserLotteryBean> getPredicableLottery(HttpServletRequest request,
			@RequestParam(value = "serverName") String serverName, @RequestParam(value = "serverCode") int serverCode)
			throws LotteryException, UnsupportedEncodingException {
		return new ResultBean<>(false, "活动已结束，感谢参与！", null);
	}

	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getRealLotteryInfoByUser(HttpServletRequest request) {
		return new ResultBean<>(false, "活动已结束，感谢参与！", null);
	}

	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfoByUser(HttpServletRequest request) {
		return new ResultBean<>(false, "活动已结束，感谢参与！", null);
	}

}
