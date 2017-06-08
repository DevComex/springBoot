package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.activityCode.ConfigBean;
import cn.gyyx.action.service.lhcj.LHCJLotteryService;
@Controller
@RequestMapping("/LHCJ")
public class LHCJLotteryController {
	private LHCJLotteryService lhcjLotteryService = new LHCJLotteryService();
	@ResponseBody
	@RequestMapping("/submitPhone")
	public ResultBean<String> submitPhone(String phoneNum,Model model,HttpServletRequest request){
		ResultBean<String> result = new ResultBean<String>();
		result = lhcjLotteryService.getActivityCode(request,ConfigBean.ACTION.LHCJ.getActionCode(),phoneNum,ConfigBean.IMPORTTYPE.phone);
		return result;
	}
	@RequestMapping("/index")
	public String index(){
		return "LHCJActivityCode/detail_gift";
	}
}	
