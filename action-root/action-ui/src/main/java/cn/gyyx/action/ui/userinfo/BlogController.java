package cn.gyyx.action.ui.userinfo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.OperateScoreEnums;
import cn.gyyx.action.service.lottery.IActionQualificationService;
import cn.gyyx.action.service.lottery.impl.AddScoreOnceEverydayByBlogQualificationService;
import cn.gyyx.action.ui.BaseController;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping("/blog")
public class BlogController extends BaseController {
	
	private IActionQualificationService actionQualificationService = null;
	
	@ResponseBody
	@RequestMapping(value = "/y/share", method = RequestMethod.GET)
	public ResultBean<Boolean> share(int activityId, HttpServletRequest request) {
		ResultBean<Boolean> result = new ResultBean<Boolean>();
		result.setIsSuccess(false);
		
		logger.info("BlogController->share->activityId=" + activityId);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("BlogController->share->please login.");
				result.setStateCode(100);
				return result;
			}
			
			logger.info("BlogController->share->userInfo=" + JSON.toJSONString(userInfo));
			
			actionQualificationService = new AddScoreOnceEverydayByBlogQualificationService();
			result = actionQualificationService.add(activityId, userInfo.getAccount(), OperateScoreEnums.SinaBlog.toString(), this.getIp(request));
		}
		catch(Exception e) {
			logger.error("BlogController->share->errorCause:" + e.getCause());
			logger.error("BlogController->share->errorMessage:" + e.getMessage());
			logger.error("BlogController->share->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("BlogController->share->result=" + result.getIsSuccess());
		
		return result;
	}
}
