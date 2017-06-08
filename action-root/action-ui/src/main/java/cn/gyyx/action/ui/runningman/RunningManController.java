package cn.gyyx.action.ui.runningman;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 奔跑吧道友活动控制器
 */
@Controller
@RequestMapping(value="/runningMan")
public class RunningManController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(RunningManController.class);
	
	
	/**
	 * 活动首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		logger.debug("index");
		return "runningMan/index";
	}
	
	/**
	 * 获取服务器信息
	 * @param typeCode
	 * @return
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<String> getServers(@RequestParam("typeCode") String typeCode){
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 从服务器获取角色信息
	 * @param request
	 * @param response
	 * @param veCode
	 * @param serverCode
	 * @param serverName
	 * @return
	 */
	@RequestMapping(value = "/getRoleInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> getRoleInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "veCode") String veCode,
			@RequestParam(value = "serverCode") int serverCode,
			@RequestParam(value = "serverName") String serverName){
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 获取玩家信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo")
	@ResponseBody
	public ResultBean<String> getAccountRole(HttpServletRequest request){
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	
	/**
	 * 添加账户角色绑定信息
	 * @param request
	 * @param roleName
	 * @param roleCode
	 * @param serverCode
	 * @param serverName
	 * @return
	 */
	@RequestMapping(value = "/addAccountRole", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> addAccountRole(HttpServletRequest request,
			@RequestParam(value = "roleName") String roleName,
			@RequestParam(value = "roleCode") String roleCode,
			@RequestParam(value = "serverCode") int serverCode,
			@RequestParam(value = "serverName") String serverName){
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 获取当前玩家中奖纪录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserPrizeList")
	@ResponseBody
	public ResultBean<String> getUserPrizeList(HttpServletRequest request){
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 获取全部玩家中奖纪录
	 * @return
	 */
	@RequestMapping(value = "/getAllUserPrizeList")
	@ResponseBody
	public ResultBean<String> getAllUserPrizeList(){
		return new ResultBean<>(true, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 获取用户地址信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean<String> getAddress(HttpServletRequest request) {
//		return runningManServeice.getAddress(request);
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 重置或者插入地址信息
	 * @param address
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resetAddress",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> resetAddress(@ModelAttribute AddressBean address, HttpServletRequest request){
//		logger.info("RunningManController---getRoleInfo---param:address---" + address);
//		return runningManServeice.resetAddress(address, request);
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 获取用户线路1完成任务列表
	 * @param address
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dailyTasks")
	@ResponseBody
	public ResultBean<String> dailyTasks(HttpServletRequest request){
//		return runningManServeice.dailyTasks(request);
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 获取用户线路2完成任务列表
	 * @param address
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/achieveTasks")
	@ResponseBody
	public ResultBean<String> achieveTasks(HttpServletRequest request){
//		return runningManServeice.achieveTasks(request);
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 领取奖励
	 * @param request
	 * @param prizeName
	 * @return
	 */
	@RequestMapping(value = "/receiveReward",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> receiveReward(HttpServletRequest request,
				@RequestParam("prizeName") String prizeName){
//		return runningManServeice.receiveReward(request, prizeName);
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * 抽奖接口
	 * @param request
	 * @param actionCode
	 * @return
	 */
	@RequestMapping(value = "/getPrize")
	@ResponseBody
	public ResultBean<String> getPrize(HttpServletRequest request,
												@RequestParam("actionCode") int actionCode) {
//		return runningManServeice.getPrize(request, actionCode);
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
}