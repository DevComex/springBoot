package cn.gyyx.action.ui;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lhzs.activityCode.LHZSConfig.RESULTTYPE;
import cn.gyyx.action.service.lhzs.activityCode.LHZSActivityCodeService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;

@Controller
@RequestMapping("/LHZS")
public class LHZSActivityCodeController {
	private LHZSActivityCodeService activityCodeService = new LHZSActivityCodeService();
	@RequestMapping("/step1Index")
	private String stepOneIndex(HttpServletRequest request,Model model){
		//登录 和 剩余抽奖次数
		ResultBean<String> result = new ResultBean<String>();
		UserInfo userInfo = null;
		try{
			userInfo = SignedUser.getUserInfo(request);
			if(userInfo==null){
				result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
				model.addAttribute("resultBean", result);
				model.addAttribute("lotteryNum", "default");
				model.addAttribute("account", "");
				return "LHZSActivityCode/step1";
			}
		}catch(Exception e){
			result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
			model.addAttribute("resultBean", result);
			model.addAttribute("lotteryNum", "default");
			model.addAttribute("account", "");
			return "LHZSActivityCode/step1";
		}
		result.setProperties(true, RESULTTYPE.success.name(), "登录成功");
		int lotteryNum = activityCodeService.lotteryNum(278, userInfo);
		model.addAttribute("resultBean", result);
		model.addAttribute("lotteryNum", lotteryNum);
		model.addAttribute("account", userInfo.getAccount());
		return "LHZSActivityCode/step1";
	}
	@RequestMapping("/step2Index")
	private String stepTwoIndex(HttpServletRequest request,Model model){
		//登录
		ResultBean<String> result = new ResultBean<String>();
		UserInfo userInfo = null;
		String resultStr = "";
		try{
			resultStr = new LHZSActivityCodeService().getRegion();
		}catch(Exception e){
			resultStr = RESULTTYPE.error.name();    //error表示获取失败
		}
		if(result.equals("{}")){
			resultStr=RESULTTYPE.error.name();
		}if(result.equals("")){
			resultStr=RESULTTYPE.error.name();
		}
		try{
			userInfo = SignedUser.getUserInfo(request);
			if(userInfo==null){
				result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
				model.addAttribute("resultBean", result);
				model.addAttribute("lotteryNum", "default");
				model.addAttribute("account", "");
				model.addAttribute("region", resultStr);
				return "LHZSActivityCode/step2";
			}
		}catch(Exception e){
			result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
			model.addAttribute("resultBean", result);
			model.addAttribute("lotteryNum", "default");
			model.addAttribute("account", "");
			model.addAttribute("region", resultStr);
			return "LHZSActivityCode/step2";
		}
		result.setProperties(true, RESULTTYPE.success.name(), "登录成功");
		model.addAttribute("account", userInfo.getAccount());
		model.addAttribute("lotteryNum", activityCodeService.lotteryNum(278, userInfo));
		model.addAttribute("resultBean", result);
		model.addAttribute("region", resultStr);
		return "LHZSActivityCode/step2";
	}
	@RequestMapping("/step3Index")
	private String stepThreeIndex(HttpServletRequest request,Model model){
		//登录和剩余抽奖次数
		ResultBean<String> result = new ResultBean<String>();
		UserInfo userInfo = null;
		try{
			userInfo = SignedUser.getUserInfo(request);
			if(userInfo==null){
				result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
				model.addAttribute("resultBean", result);
				model.addAttribute("lotteryNum", "default");
				model.addAttribute("account", "");
				return "LHZSActivityCode/step3";
			}
		}catch(Exception e){
			result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
			model.addAttribute("resultBean", result);
			model.addAttribute("lotteryNum", "default");
			model.addAttribute("account", "");
			return "LHZSActivityCode/step3";
		}
		result.setProperties(true, RESULTTYPE.success.name(), "登录成功");
		int lotteryNum = activityCodeService.lotteryNum(278, userInfo);
		model.addAttribute("resultBean", result);
		model.addAttribute("lotteryNum", lotteryNum);
		model.addAttribute("account", userInfo.getAccount());
		return "LHZSActivityCode/step3";
	}
	//TODO
	/**
	 * 步骤一按钮
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/step1",produces = "text/html;charset=UTF-8")
	private String stepOne(HttpServletRequest request){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		ResultBean<String> result = new ResultBean<String>();
		UserInfo userInfo = null;
		try{
			userInfo = SignedUser.getUserInfo(request);
			if(userInfo==null){
				result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
				returnMap.put("resultBean", result);
				returnMap.put("lotteryNum", "default");
				returnMap.put("account", "");
				return DataFormat.objToJson(returnMap);
			}
		}catch(Exception e){
			result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
			returnMap.put("resultBean", result);
			returnMap.put("lotteryNum", "default");
			returnMap.put("account", "");
			return DataFormat.objToJson(returnMap);
		}
		Map<String,Object> resultMap = activityCodeService.stepOne(userInfo, 278, "byChance");
		String resultJSON = DataFormat.objToJson(resultMap);
		return resultJSON;
	}
	/**
	 * 获取游戏大区
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRegion")
	private ResultBean<String> getRegion(HttpServletRequest request){
		String result = "";
		ResultBean<String> resultBean = new ResultBean<String>();
		try{
			result = new LHZSActivityCodeService().getRegion();
			resultBean.setProperties(true, "success", result);
		}catch(Exception e){
			resultBean.setProperties(false, "error", "网络不给力！！");
		}
		if(result.equals("{}")){
			resultBean.setProperties(false, "error", "网络不给力！！");
		}if(result.equals("")){
			resultBean.setProperties(false, "error", "网络不给力！！");
		}
		return resultBean;
	}
	/**
	 * 第二步 按钮
	 * @param request
	 * @param activityCode
	 * @param userPsw
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/step2")
	private ResultBean<String> stepTwo(HttpServletRequest request,String serverID,String serverName,String activityCode,String userPsw){
		ResultBean<String> result = new ResultBean<String>();
		if(activityCodeService.actionTime(278)){
			UserInfo userInfo = null;
			try{
				userInfo = SignedUser.getUserInfo(request);
				if(userInfo==null){
					result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
					return result;
				}
			}catch(Exception e){
				result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
				return result;
			}
			result = activityCodeService.stepTwo(userInfo, 278,
					serverID, serverName,activityCode,userPsw);
		}else{
			result.setProperties(false, RESULTTYPE.error.name(), "活动已结束！！");
		}
		return result;
	}
	/**
	 * 第三部按钮
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/step3")
	private ResultBean<String> stepThree(HttpServletRequest request,HttpServletResponse response,String checkcode){
		ResultBean<String> result = new ResultBean<String>();
		UserInfo userInfo = null;
		try{
			userInfo = SignedUser.getUserInfo(request);
			if(userInfo==null){
				result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
				return result;
			}
		}catch(Exception e){
			result.setProperties(false, RESULTTYPE.noLogin.name(), "请先登录哟！");
			return result;
		}
		if (!new Captcha(request, response).equals(checkcode)) {
			result.setProperties(false, RESULTTYPE.error.name(), "您的验证码填写不正确！！");
		} else {
			result = activityCodeService.stepThree(16, userInfo.getUserId());
		}
		return result;
	}
	
}
