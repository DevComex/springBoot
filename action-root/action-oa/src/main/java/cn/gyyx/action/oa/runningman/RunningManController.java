package cn.gyyx.action.oa.runningman;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 奔跑吧道友后台控制器
 */
@Controller
@RequestMapping(value="/runningMan")
public class RunningManController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(RunningManController.class); 

	
	/**
	 * 后台页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model){
		
		return "runningMan/index";
	}
	
	/**
	 * 分页获取全部用户信息
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "/getUserInfoList")
	@ResponseBody
	public ResultBean<PageBean<String>> getUserList(
			@RequestParam("currentPage") int currentPage){
		logger.info("奔跑吧道友后台统计-RunningManController-getUserList"
				+ "，参数currentPage:" + currentPage);
		return new ResultBean<PageBean<String>>(false, "活动已结束", null);
	}
	
	/**
	 * 获取用户任务完成情况
	 * @param model
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/tasks")
	public String tasks(Model model,@RequestParam("userCode") int userCode){
		return "runningMan/tasks";
	}
	
	/**
	 * 获取用户中奖纪录
	 * @param model
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/prizeList")
	public String prizeList(Model model,@RequestParam("userCode") int userCode){
		return "runningMan/prizeList";
	}
	
	/**
	 * 按条件查询
	 * @param userName
	 * @param roleName
	 * @return
	 */
	@RequestMapping(value = "/findByUserNameAndRoleName")
	@ResponseBody
	public ResultBean<String> findByUserNameAndRoleName(
			@RequestParam("userName") String userName,
			@RequestParam("roleName") String roleName){
		return new ResultBean<String>(false, "活动已结束", null);
	}
	
	/**
	 * 导出
	 * @param response
	 */
	@RequestMapping(value="/extendExcel",method=RequestMethod.GET)
	public void extendExcel(HttpServletResponse response) {
		
	}
}
