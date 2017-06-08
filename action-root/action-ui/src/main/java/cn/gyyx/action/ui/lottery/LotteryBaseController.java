package cn.gyyx.action.ui.lottery;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.service.lottery.ILotteryCountService;
import cn.gyyx.action.service.lottery.ILotteryPrizesService;
import cn.gyyx.action.service.lottery.impl.LotteryCountService;
import cn.gyyx.action.service.lottery.impl.LotteryPrizesService;
import cn.gyyx.action.service.lottery.impl.LotteryService;
import cn.gyyx.action.ui.WeChatBaseController;

@Controller
@RequestMapping("/lottery")
public class LotteryBaseController extends WeChatBaseController {

	private ILotteryCountService lotteryCountService = null;
	private ILotteryPrizesService lotteryPrizesService = null;
	
	// 获得抽奖次数
	@ResponseBody
	@RequestMapping(value = "/count/get", method = RequestMethod.GET)
	public ResultBean<Map<String, Integer>> getLotteryCount(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="activityId") int activityId, @RequestParam(value="OpenId") String openId) {
		ResultBean<Map<String, Integer>> result = new ResultBean<Map<String, Integer>>();
		result.setIsSuccess(false);
		result.setMessage("");
		
		try {
			// 是否微信浏览器
			if (!this.isWechatBrowser(request, response)) {
				// 不是微信浏览器，则返回
				result.setMessage("请在微信中访问！");
				return result;
			}
			
			// 是否关注微信订阅号
			if (!this.isAttention(activityId, openId)) {
				// 没有关注微信订阅号，则返回
				result.setMessage("请先关注微信订阅号！");
				return result;
			}
			
			// 是否登录
			if (!this.validateLogin(activityId, openId)) {
				// 没有登录
				result.setMessage("请先登录！");
				return result;
			}
			
			if (!StringUtils.isEmpty(openId)) {
				lotteryCountService = new LotteryCountService();
				
				Map<String, Integer> map = lotteryCountService.getLotteryCountAndMustWinCount(openId, activityId);
				
				if (map != null) {
					result.setData(map);
					result.setIsSuccess(true);
				}
				else {
					result.setMessage("未查询到相关数据！");
				}
			}
			else {
				result.setMessage("请先登录！");
			}
		}
		catch(Exception e) {
			logger.error("LotteryBaseController.getLotteryCount => openId=" + openId + " activityId=" + activityId, e);
		}
		
		return result;
	}
	
	// 获得奖品信息
	@ResponseBody
	@RequestMapping(value = "/prizes/get", method = RequestMethod.GET)
	public ResultBean<LotteryPrizesVO> getPrizes(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="activityId") int activityId, @RequestParam(value="OpenId")  String openId) {
		ResultBean<LotteryPrizesVO> result = new ResultBean<LotteryPrizesVO>();
		result.setIsSuccess(false);
		result.setMessage("失败！");
		
		try {
			// 是否微信浏览器
			if (!this.isWechatBrowser(request, response)) {
				// 不是微信浏览器，则返回
				result.setMessage("请在微信中访问！");
				return result;
			}
			
			// 是否关注微信订阅号
			if (!this.isAttention(activityId, openId)) {
				// 没有关注微信订阅号，则返回
				result.setMessage("请先关注微信订阅号！");
				return result;
			}
			
			// 是否登录
			if (!this.validateLogin(activityId, openId)) {
				// 没有登录
				result.setMessage("请先登录！");
				return result;
			}
			
			if (!StringUtils.isEmpty(openId)) {
				lotteryPrizesService = new LotteryPrizesService();
				
				List<LotteryPrizesVO> prizesList = lotteryPrizesService.getPrizesInfomations(activityId, 0, openId);
				
				if (prizesList != null && prizesList.size() > 0) {
					result.setRows(prizesList);
					result.setMessage("查询成功！");
					result.setIsSuccess(true);
				}
				else {
					result.setMessage("没有奖品信息！");
				}
			}
			else {
				result.setMessage("请先登录！");
			}
		}
		catch(Exception e) {
			logger.error("LotteryBaseController.getLotteryCount => userId=" + openId + " activityId=" + activityId, e);
		}
		
		return result;
	}
	
	// 保存邮寄奖品地址
	@ResponseBody
	@RequestMapping(value = "/address/put", method = RequestMethod.POST)
	public ResultBean<String> putAddress(HttpServletRequest request, HttpServletResponse response,
			@Validated LotteryPrizesVO params, BindingResult br) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		result.setMessage("失败！");

		if (br.hasErrors()) {
			result.setMessage(br.getFieldError().getDefaultMessage());
			return result;
		}
		
		try {
			// 是否微信浏览器
			if (!this.isWechatBrowser(request, response)) {
				// 不是微信浏览器，则返回
				result.setMessage("请在微信中访问！");
				return result;
			}
			
			// 是否关注微信订阅号
			if (!this.isAttention(params.getActivityId(), params.getOpenId())) {
				// 没有关注微信订阅号，则返回
				result.setMessage("请先关注微信订阅号！");
				return result;
			}

			// 是否登录
			if (!this.validateLogin(params.getActivityId(), params.getUserId())) {
				// 没有登录
				result.setMessage("请先登录！");
				return result;
			}
			
			if (!StringUtils.isEmpty(params.getUserId())) {
				lotteryPrizesService = new LotteryPrizesService();
				
				result = lotteryPrizesService.putAddress(params);
			}
			else {
				result.setMessage("请先登录！");
			}
		}
		catch(Exception e) {
			logger.error("LotteryBaseController.putAddress", e);
		}
		
		return result;
	}
	
	// 抽奖
	@ResponseBody
	@RequestMapping(value = "/get")
	public ResultBean<LotteryPrizesVO> get(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="activityId") int activityId, @RequestParam(value="OpenId")  String openId, 
			@RequestParam(value="type") int type) {
		ResultBean<LotteryPrizesVO> result = new ResultBean<LotteryPrizesVO>();
		result.setIsSuccess(false);
		result.setMessage("失败！");
		
		try {
			// 是否微信浏览器
			if (!this.isWechatBrowser(request, response)) {
				// 不是微信浏览器，则返回
				result.setMessage("请在微信中访问！");
				return result;
			}
			
			// 是否关注微信订阅号
			if (!this.isAttention(activityId, openId)) {
				// 没有关注微信订阅号，则返回
				result.setMessage("请先关注微信订阅号！");
				return result;
			}
			
			// 是否登录
			if (!this.validateLogin(activityId, openId)) {
				// 没有登录
				result.setMessage("请先登录！");
				return result;
			}
			
			LotteryService service = new LotteryService();
			LotteryPrizesVO prizes = service.getPrizesByMustWin(activityId, openId, this.getIp(request), (type == 0 ? false : true));
			
			if (prizes != null) {
				result.setData(prizes);
				result.setMessage(prizes.getPrizeName());
				result.setIsSuccess(true);
			}
			else {
				result.setMessage(service.getMessage());
			}
		}
		catch(Exception e) {
			logger.error("LotteryBaseController.get", e);
		}
		
		return result;
	}
}
