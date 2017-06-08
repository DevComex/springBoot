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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.exchangescore.PrizeExchangeLogBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.service.challenger.LotteryService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/challenger/lottery")
public class ChallengeLotteryController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChallengeLotteryController.class);
	private LotteryService lotteryService = new LotteryService();
	int actionCode = 375;

	/**
	 * 挑战列表页
	 */
	@RequestMapping("/test")
	public String test() {
		logger.debug("fighterList");
		return "challenger/test";
	}

	/***
	 * 就抽个奖
	 * 
	 * @param actionCode
	 * @param request
	 * @param sqlSession
	 * @return
	 */
	@RequestMapping(value = "/letUsLottery", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<UserLotteryBean> letUsLottery(HttpServletRequest request) {
		return lotteryService.letUsLottery(actionCode, request);

	}

	/***
	 * 获取用户的中奖信息
	 * 
	 * @param actionCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfoByUser(
			HttpServletRequest request) {
		return lotteryService.getAllLotteryInfoByUser(actionCode, request);

	}

	/**
	 * 获取抽奖资格次数
	 * 
	 * @param request
	 * @param actionCode
	 * @return
	 */
	@RequestMapping(value = "/getLotteryTime")
	@ResponseBody
	public ResultBean<Integer> getLotteryTime(HttpServletRequest request
			) {
		return lotteryService.getLotteryTime(request, actionCode);
	}

	/**
	 * 增加地址
	 * 
	 * @param prizeExchangeLogBean
	 * @return
	 */
	@RequestMapping(value = "/addAddress", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> addAddress(HttpServletRequest request,
			PrizeExchangeLogBean prizeExchangeLogBean) {
		return lotteryService.addAddress(request, prizeExchangeLogBean,
				actionCode);
	}

}
