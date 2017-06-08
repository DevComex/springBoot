package cn.gyyx.action.ui.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.config.IHdConfigService;
import cn.gyyx.action.service.config.impl.DefaultHdConfigService;
import cn.gyyx.action.ui.BaseController;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	private IHdConfigService hdConfigService = new DefaultHdConfigService();
	
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResultBean<Boolean> getState(int activityId, HttpServletRequest request) {
		ResultBean<Boolean> result = new ResultBean<Boolean>();
		result.setIsSuccess(false);
		
		try {
			int state = hdConfigService.getState(activityId);
			if (1 == state) {
				result.setIsSuccess(true);
			}
			else {
				result.setStateCode(state);
			}
		}
		catch(Exception e) {
			logger.error("ActivityController->getState->errorCause:" + e.getCause());
			logger.error("ActivityController->getState->errorMessage:" + e.getMessage());
			logger.error("ActivityController->getState->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}
}
