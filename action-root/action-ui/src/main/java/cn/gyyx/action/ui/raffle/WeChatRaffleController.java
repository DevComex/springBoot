package cn.gyyx.action.ui.raffle;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.ui.WeChatBaseController;

@Controller
@RequestMapping("/wcraffle")
public class WeChatRaffleController extends WeChatBaseController {
	
	
	// 进入首页
	@RequestMapping(value = {"", "/", "/index"})
	public String index(Model model, HttpServletRequest request,HttpServletResponse response) {
		
		String openId = "";
		PrintWriter write = null;
		
		try {
			response.setContentType("text/html;charset=UTF-8");
			write = response.getWriter();
			
			if (isWechatBrowser(request, response)) {
				HashMap<String,Object> openidResult = valideURLSign(request, response);
				openId = openidResult.containsKey("openId") ? openidResult.get("openId").toString() : "";
			}
			
			model.addAttribute("openId", openId);
		}
		catch(Exception e) {
			logger.error("微信大转盘首页", e);
			response.setContentType("text/html;charset=UTF-8");
			
			if (write != null) {
				write.write(String.valueOf("<h1>服务器内部错误！</h1>"));
				write.close();
			}
		}
		
		return "raffle/wxdzp";
	}
	
	// 抽奖
	@ResponseBody
	@RequestMapping("/getprizes")
	public ResultBean<Object> getPrizes(String openid, String actid, String acttype,
			HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<Object>(false,"活动已结束，谢谢参与",null);
		
		// 是否用微信浏览器访问
		if (isWechatBrowser(request, response)) {
		    return result;
		}
		else {
			result.setMessage("请在微信中打开");
		}
		
		return result;
	}
	
	// 获得抽奖次数
	@ResponseBody
	@RequestMapping("/getcount")
	public ResultBean<Integer> getCount(String openid, String actid, String acttype,
			HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Integer> result = new ResultBean<Integer>(false,"活动已结束，谢谢参与",null);
		return result;
	}
	
	// 获得奖品信息
	@ResponseBody
	@RequestMapping("/getprizelist")
	public ResultBean<Object> getPrizesDetail(String openid, String actid, String acttype,
			HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"活动已结束，谢谢参与",null);
		    return result;
	}

	// 设置地址
	@ResponseBody
	@RequestMapping("/setaddress")
	public ResultBean<Integer> setUserAddress(String openid, String actid, String name, String phone, String address) {
		ResultBean<Integer> result = new ResultBean<Integer>(false,"活动已结束，谢谢参与",null);
		if (StringUtils.isEmpty(openid)) {
			result.setMessage("请重新打开！");
			return result;
		}
		
		if (StringUtils.isEmpty(actid)) {
			result.setMessage("活动ID不能为空，请退出重新打开！");
			return result;
		}
		
		if (StringUtils.isEmpty(name)) {
			result.setMessage("“姓名”不能为空！");
			return result;
		}
		if (!Pattern.compile("[\u4e00-\u9fa5]{2,6}").matcher(name).matches()) {
			result.setMessage("“姓名”应该是2-6个中文！");
			return result;
		}
		
		if (StringUtils.isEmpty(phone)) {
			result.setMessage("“手机号”不能为空！");
			return result;
		}
		if (!Pattern.compile("^1[\\d]{10}$").matcher(phone).matches()) {
			result.setMessage("请输入正确“手机号”！");
			return result;
		}
		
		if (StringUtils.isEmpty(address)) {
			result.setMessage("“详细地址”不能为空！");
			return result;
		}
		if (!Pattern.compile("[\\sa-zA-Z0-9\u4e00-\u9fa5]{0,50}").matcher(address).matches()) {
			result.setMessage("请填写真实的“详细地址”！");
			return result;
		}
		return result;
	}
}
