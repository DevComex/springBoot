package cn.gyyx.action.ui.address;

import javax.servlet.http.HttpServletRequest;

import org.parboiled.common.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.address.vo.ActionPrizesAddressOfExchangeVO;
import cn.gyyx.action.beans.address.vo.ActionPrizesAddressOfLotteryVO;
import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.service.address.IActionPrizesAddressService;
import cn.gyyx.action.service.address.impl.ActionPrizesAddressOfExchangeService;
import cn.gyyx.action.service.address.impl.ActionPrizesAddressOfLotteryService;
import cn.gyyx.action.service.address.impl.ActionPrizesAddressServiceImpl;
import cn.gyyx.action.ui.BaseController;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping("/address")
public class ActionAddressController extends BaseController {

	private IActionPrizesAddressService actionPrizesAddressService;
	
	// 保存地址
	@ResponseBody
	@RequestMapping(value = "/y/post/{type}", method = RequestMethod.POST)
	public ResultBean<String> post(HttpServletRequest request, 
			@PathVariable("type") String type, 
			@Validated ActionPrizesAddressPO address, BindingResult br) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		
		logger.info("ActionAddressController->post->start.");
		
		if (br.hasErrors()) {
			result.setMessage(br.getFieldError().getDefaultMessage());
			return result;
		}
		
		try {
			logger.info("ActionAddressController->post->address=" + JSON.toJSONString(address));
			
			// 校验是否登录
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("ActionAddressController->post->please login.");
				
				result.setStateCode(100);
				result.setMessage("请登录！");
				return result;
			}
			
			logger.info("ActionAddressController->post->UserInfo=" + JSON.toJSONString(userInfo));
			
			address.setUserId(userInfo.getAccount());  // 用户账号
			address.setActivityType(ActivityTypeEnums.Lottery.toString());  // 默认类型为“抽奖”
			
			if (!StringUtils.isEmpty(type)) {
				if (type.equalsIgnoreCase(ActivityTypeEnums.Lottery.toString())) {
					address.setActivityType(ActivityTypeEnums.Lottery.toString());  // 类型为“抽奖”
				}
				else if (type.equalsIgnoreCase(ActivityTypeEnums.Exchange.toString())) {
					address.setActivityType(ActivityTypeEnums.Exchange.toString());  // 类型为“兑换”
				}
			}
			
			logger.info("ActionAddressController->post->address=" + JSON.toJSONString(address));
			
			actionPrizesAddressService = new ActionPrizesAddressServiceImpl();
			result = actionPrizesAddressService.post(address);
			
			logger.info("ActionAddressController->post->result=" + JSON.toJSONString(result));
		}
		catch(Exception e) {
			logger.error("ActionAddressController->post->Cause:" + e.getCause());
			logger.error("ActionAddressController->post->Message:" + e.getMessage());
			logger.error("ActionAddressController->post->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionAddressController->post->done.");
		
		return result;
	}

	// 获得抽奖地址
	@ResponseBody
	@RequestMapping(value = "/y/get/lottery", method = RequestMethod.GET)
	public ResultBean<ActionPrizesAddressOfLotteryVO> getLottery(int activityId, 
			HttpServletRequest request) {
		ResultBean<ActionPrizesAddressOfLotteryVO> result = new ResultBean<ActionPrizesAddressOfLotteryVO>();
		result.setIsSuccess(false);
		
		logger.info("ActionAddressController->getLottery->start.");
		
		try {
			// 校验是否登录
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("ActionAddressController->getLottery->please login.");
				
				result.setStateCode(100);
				result.setMessage("请登录！");
				return result;
			}
			
			actionPrizesAddressService = new ActionPrizesAddressOfLotteryService();
			result = actionPrizesAddressService.get(activityId, userInfo.getAccount(), ActivityTypeEnums.Lottery.toString());
		}
		catch(Exception e) {
			logger.error("ActionAddressController->getLottery->Cause:" + e.getCause());
			logger.error("ActionAddressController->getLottery->Message:" + e.getMessage());
			logger.error("ActionAddressController->getLottery->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionAddressController->getLottery->result=" + JSON.toJSONString(result));
		
		return result;
	}
	
	// 获得邀请函地址
	@ResponseBody
	@RequestMapping(value = "/y/get/exchange", method = RequestMethod.GET)
	public ResultBean<ActionPrizesAddressOfExchangeVO> getExchange(int activityId, 
			HttpServletRequest request) {
		ResultBean<ActionPrizesAddressOfExchangeVO> result = new ResultBean<ActionPrizesAddressOfExchangeVO>();
		result.setIsSuccess(false);
		
		logger.info("ActionAddressController->get->start.");
		
		try {
			// 校验是否登录
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("ActionAddressController->get->please login.");
				
				result.setStateCode(100);
				result.setMessage("请登录！");
				return result;
			}
			
			actionPrizesAddressService = new ActionPrizesAddressOfExchangeService();
			result = actionPrizesAddressService.get(activityId, userInfo.getAccount(), ActivityTypeEnums.Exchange.toString());
		}
		catch(Exception e) {
			logger.error("ActionAddressController->get->Cause:" + e.getCause());
			logger.error("ActionAddressController->get->Message:" + e.getMessage());
			logger.error("ActionAddressController->get->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionAddressController->get->result=" + JSON.toJSONString(result));
		
		return result;
	}
}
