/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：薄振成 
 * 联系方式：bozhencheng@gyyx.cn 
 * 创建时间：2016年07月05日
 * 版本号：
 * 本类主要用途叙述：问道之旅报名
 */


package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.common.ServerBean;
import cn.gyyx.action.beans.wdtravelentry.WdTravelEntry;
import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.core.auth.UserInfo;

/**
 * @author bozch
 *
 */
@Controller
@RequestMapping("/travelentry")
public class WDTravelEntryController {
	
	
	/**
	 * index 首页
	 * @param model 属性
	 * @param request 请求
	 * @return
	 */
	@RequestMapping({"","/","/index"})
	public String index(Model model, HttpServletRequest request){
		
		return "wdtravelentry/index";
	}
	
	
	/**
	  * @Title: getRoleBindStatus
	  * @Description:  获取用户绑定状态
	  * @param request 请求
	  * @return ResultBean<Boolean> 
	  * @throws
	  */
	@RequestMapping(value="/getbindStatus")
	@ResponseBody
	public ResultBean<Object> getRoleBindStatus(HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<>();
		result.setIsSuccess(false);
		result.setMessage("活动已经结束");
		return result;
		
	}
	
	
	/**
	  * @Title: getServers
	  * @Description:  获取服务器信息
	  * @param request 请求
	  * @param nettype 大区类型
	  * @return ResultBean<ServerBean> 
	  * @throws
	  */
	@RequestMapping(value="/getservers")
	@ResponseBody
	public ResultBean<ServerBean> getServers(HttpServletRequest request,@RequestParam("nettype")int nettype){
		ResultBean<ServerBean> result = new ResultBean<>();
		result.setIsSuccess(false);
		result.setMessage("活动已经结束");
		return result;
	}
	
	/**
	  * @Title: getServers
	  * @Description:  获取角色信息
	  * @param serverId 服务器ID
	  * @param captcha 验证码
	  * @param request 请求
	  * @return ResultBean<ServerBean> 
	  * @throws
	  */
	@RequestMapping(value="/getRoles")
	@ResponseBody
	public ResultBean<Object> getRoles(@RequestParam(value="serverId")int serverId , @RequestParam("captcha")String captcha, HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<>();
		result.setIsSuccess(false);
		result.setMessage("活动已经结束");
		return result;
	}
	
	
	/**
	  * @Title: 
	  * @Description:  填写信息
	  * @param request 请求
	  * @param entryForm 请求表单
	  * @param vresult 验证结果
	  * @return ResultBean<Boolean> 
	  * @throws
	  */
	@RequestMapping(value = "/fillInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> fillInfo(@ModelAttribute @Valid WdTravelEntry entryForm, BindingResult vresult, HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<>();
		result.setIsSuccess(false);
		result.setMessage("活动已经结束");
		return result;
	}
	 
	/**
	  * @Title: 
	  * @Description:  填写信息
	  * @param request 请求
	  * @param entryForm 请求表单
	  * @return ResultBean<Boolean> 
	  * @throws
	  */
	@RequestMapping(value = "/getFillInfo", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<Object> getTravelEntry(HttpServletRequest request){
		ResultBean<Object> result = new ResultBean<>();
		result.setIsSuccess(false);
		result.setMessage("活动已经结束");
		return result;
	}
	/**
	  * @Title: bindRole
	  * @Description:  绑定角色
	  * @param 
	  * @return ResultBean<Boolean> 
	  * @throws
	  */
	@RequestMapping(value = "/bindrole", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> bindRole(HttpServletRequest request,
			@RequestParam("region")String region,
			@RequestParam("serverId")int serverId,
			@RequestParam("serverName")String serverName,
			@RequestParam("roleName")String roleName){
		ResultBean<Object> result = new ResultBean<>();
		result.setIsSuccess(false);
		result.setMessage("活动已经结束");
		return result;
	}
	
	private ResultBean<UserInfo> checkLogin(HttpServletRequest request){
		ResultBean<UserInfo> result = new ResultBean<>();
		result.setIsSuccess(false);
		result.setMessage("活动已经结束");
		return result;
	}
}
