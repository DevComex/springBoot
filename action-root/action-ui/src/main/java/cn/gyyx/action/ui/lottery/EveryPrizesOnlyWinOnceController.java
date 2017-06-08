package cn.gyyx.action.ui.lottery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.address.vo.ActionPrizesAddressOfLotteryVO;
import cn.gyyx.action.service.lottery.ILotteryService;
import cn.gyyx.action.service.lottery.template.EveryPrizesOnlyWinOnceLotteryService;
import cn.gyyx.action.ui.BaseController;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping("/lottery")
public class EveryPrizesOnlyWinOnceController extends BaseController {

	private ILotteryService lotteryService = null;
	
	@ResponseBody
	@RequestMapping(value = "/y/epowo/get", method = RequestMethod.POST)
	public ResultBean<ActionPrizesAddressOfLotteryVO> get(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="activityId") int activityId) {
		ResultBean<ActionPrizesAddressOfLotteryVO> result = new ResultBean<ActionPrizesAddressOfLotteryVO>();
		result.setIsSuccess(false);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("EveryPrizesOnlyWinOnceController->get->please login.");
				result.setStateCode(100);
				result.setMessage("请先登录！");
				return result;
			}
			
			lotteryService = new EveryPrizesOnlyWinOnceLotteryService();
			result = lotteryService.getPrizes(activityId, userInfo.getAccount(), this.getIp(request));
		}
		catch(Exception e) {
			logger.error("EveryPrizesOnlyWinOnceController->get->Cause=" + e.getCause());
			logger.error("EveryPrizesOnlyWinOnceController->get->Message=" + e.getMessage());
			logger.error("EveryPrizesOnlyWinOnceController->get->StackTrace=" + e.getStackTrace());
		}
		
		return result;
	}
}
