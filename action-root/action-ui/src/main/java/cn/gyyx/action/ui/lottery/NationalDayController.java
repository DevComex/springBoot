package cn.gyyx.action.ui.lottery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.gyyx.action.ui.WeChatBaseController;

@Controller
@RequestMapping("/lottery")
public class NationalDayController extends WeChatBaseController {

	private int activityId = 395;
	
	@RequestMapping("/nationalday")
	public String index(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="OpenId") String openId) {
		
		try {
			response.setContentType("text/html;charset=UTF-8");
			
			if (isWechatBrowser(request, response)) {
				if (!this.validateLogin(activityId, openId)) {
					return this.getLoginUrl(activityId); 
				}
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			logger.error("国庆微信抽奖活动", e);
		}
		
		return "/nationalDay/prize";
	}
}
