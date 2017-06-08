package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.activityCode.ConfigBean.ACTION;
import cn.gyyx.action.service.jsws.JSWSLotteryService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping("/CJKP")
public class CJKPController {
	private JSWSLotteryService JSWSLotteryService = new JSWSLotteryService();
	@RequestMapping("/index")
	public String toIndex(HttpServletRequest request,Model model){
		ResultBean<String> result = new ResultBean<String>();
		UserInfo userInfo = null;
		try{
			userInfo = SignedUser.getUserInfo(request);
		}catch(Exception e){
			result.setProperties(false, "noLogin", "请先登录");
		}
		if(userInfo!=null){
			result = JSWSLotteryService.loginActivityCode(request, ACTION.CJKP.getActionCode(), userInfo.getAccount(), ACTION.CJKP);
		}else{
			result.setProperties(false,"noLogin","请先登录");
		}
		model.addAttribute("indexResultM", result);
		return "CJKPPresent/index";
	}
	@ResponseBody
	@RequestMapping("/independent")
	public ResultBean<String> getActivityCodePC(HttpServletRequest request,String phone,Model model){
		ResultBean<String> result = JSWSLotteryService.getActivityCodePhone(request, ACTION.CJKP.getActionCode(), phone, ACTION.CJKP);
		System.out.println(result.getIsSuccess()+"**"+result.getMessage()+"**"+result.getData());
		return result;
	}
	@ResponseBody
	@RequestMapping("/login")
	public ResultBean<String> getLoginActivityCode(HttpServletRequest request){
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if(userInfo!=null){
			ResultBean<String> result = JSWSLotteryService.loginActivityCode(request, ACTION.CJKP.getActionCode(), userInfo.getAccount(), ACTION.CJKP);
			return result;
		}else{
			return new ResultBean<String>(false,"noLogin","请先登录！！");
		}
	}
}
