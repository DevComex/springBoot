package cn.gyyx.action.ui.exchange;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.ui.BaseController;

@Controller
@RequestMapping("/exchange")
public class ActionExchangeController extends BaseController {

	
	// 是否兑换过邀请函
	@ResponseBody
	@RequestMapping(value = "/y/get/exsits", method = RequestMethod.GET)
	public ResultBean<Boolean> isExsits(HttpServletRequest request, @RequestParam int activityId) {
		ResultBean<Boolean> result = new ResultBean<Boolean>();
		result.setIsSuccess(false);
		result.setMessage("活动已结束！");
		logger.info("ActionExchangeController->isExsits->start.");
		logger.info("ActionExchangeController->isExsits->done.");
		return result;
	}
	
	// 兑换邀请函
	@ResponseBody
	@RequestMapping("/y/post")
	public ResultBean<ActionPrizesAddressPO> post(HttpServletRequest request, @RequestParam int activityId, String itemId) {
		ResultBean<ActionPrizesAddressPO> result = new ResultBean<ActionPrizesAddressPO>();
		result.setStateCode(0);
		result.setIsSuccess(false);
		
		logger.info("ActionExchangeController->post->start.");
		result.setMessage("活动已结束！");
		logger.info("ActionExchangeController->post->done.");
		
		return result;
	}
	
}
