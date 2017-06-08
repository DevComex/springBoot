package cn.gyyx.action.ui.wdseekteam;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;

/**
 * 版 权：光宇游戏 
 * 作 者：lizhihai 
 * 创建时间：2016年12月09日  
 * 描 述：问道2017寻找基友活动-控制层
 */
@Controller
@RequestMapping(value = "/wd2017/seekteam")
public class Wd2017SeekTeamController {
	public static final int ACTIONCODE = 419;
	
	
	/***
	 * 主页面
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {
		return "wdseekteam/index";
	}
	/**
	 *  teamList页面
	 */
	@RequestMapping("/teamList")
	public String teamList(Model model, HttpServletRequest request) {
		return "wdseekteam/teamList";
	}
	/**
	 *  teamList页面
	 */
	@RequestMapping("/teamDetail")
	public String teamDetail(Model model, HttpServletRequest request) {
		return "wdseekteam/teamDetail";
	}
	/**
	 *  查看我的现状  
	 *  是否绑定(已绑，未绑)，是否入队（入队，未入队）  入队（队长，队员）
	 */
	@RequestMapping(value = "/viewMyStatus",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> viewMyStatus( HttpServletRequest request) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
		}
	
	/**
	 * 获取服务器信息
	 */
	@RequestMapping(value = "/getServers",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> getServers(@RequestParam(value = "netType") String typeCode) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	
	/**
	 * 获取角色列表
	 */
	@RequestMapping(value ="/getRoleList",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> getRoleList(@RequestParam(value = "serverCode") String serverCode, @RequestParam(value = "validCode") String validCode,
			@RequestParam(value = "serverName") String serverName,	 HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Object> resultBean = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return resultBean;
		
	}
	
	/**
	 * 绑定角色
	 */
	@RequestMapping(value="/addAccountRole",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> addAccountRole(HttpServletRequest request,
			@RequestParam(value = "roleName") String roleName,@RequestParam(value = "RoleId") String roleCode,
			@RequestParam(value = "serverCode") int serverCode, @RequestParam(value = "serverName") String serverName,
			@RequestParam(value = "Religion") String Religion,@RequestParam(value = "ImageNumber") String imageNumber
			) {
		ResultBean<String> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	/**
	 * 查看我的队伍
	 */
	@RequestMapping(value="/viewMyTeam",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> viewMyTeam(HttpServletRequest request) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	
	/**
	 * 创建队伍
	 */
	@RequestMapping(value="/creatTeam",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> createTeam(HttpServletRequest request,String declaration) {
		ResultBean<String> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	
	/**
	 * 提交入队申请 
	 */
	@RequestMapping(value="/applyTeam",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> applyTeam(HttpServletRequest request,String declaration,int teamCode) {
		ResultBean<String> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	/**
	 * 查看入队申请 
	 */
	@RequestMapping(value="/applyList",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Map<String,Object>> applyList(HttpServletRequest request) {
		ResultBean<Map<String,Object>> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	/**
	 * 我的申请记录  我申请过哪些队伍
	 */
	@RequestMapping(value="/myApplyList",method = RequestMethod.POST)
	@ResponseBody
	public  ResultBean<Object> myApplyList(HttpServletRequest request) {
		 ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	/**
	 * 队长审核申请 
	 */
	@RequestMapping(value="/applyaudit",method = RequestMethod.POST)
	@ResponseBody
	public  ResultBean<String> applyaudit(HttpServletRequest request,String account,Integer userId,String operation) {
		 ResultBean<String> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	
	/**
	 * 我的奖励状态
	 * viewPrizeStatus
	 */
	@RequestMapping(value="/viewPrizeStatus",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> viewPrizeStatus(HttpServletRequest request) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);

		return result;
	}
	/**
	 * 领取奖励
	 */
	@RequestMapping(value="/drawprize",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> drawprize(HttpServletRequest request,int number) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	/**
	 * 队伍列表  1.完成组队的  2.未完成组队的 需要加分页
	 */
	@RequestMapping(value="/teamlist",method = RequestMethod.POST)
	@ResponseBody
	public ResultBeanWithPage<Object> teamlist(HttpServletRequest request,String type,String roleName,int pageSize,
			int pageNo) {
		ResultBeanWithPage<Object> result = new ResultBeanWithPage<>();
		result.setIsSuccess(false);
		result.setMessage("活动已结束，谢谢参与");
		return result;
	}
	/**
	 * 成功入队的列表-前台轮播展示使用
	 */
	@RequestMapping(value="/joinedteam/resent/userlist",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> Object(HttpServletRequest request) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
	/**
	 * 推荐的队伍列表-前台轮播展示使用
	 */
	@RequestMapping(value="/recommend/teamlist",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> recommendTeamList(HttpServletRequest request) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	/**
	 * 抽奖
	 */
	@RequestMapping(value = "/lottery")
	@ResponseBody
	public ResultBean<Object> lottery(HttpServletRequest request,HttpServletResponse response) {
		ResultBean<Object> lotteryResult = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return lotteryResult;
	}
	
	
	/**
	 * 获取用户中奖信息
	 */
	@RequestMapping(value = "/getLotteryInfoByUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> getRealLotteryInfoByUser(HttpServletRequest request) {
		ResultBean<Object> resultBean = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return resultBean;
	}
	
	/**
	 * 获取当前活动所有中奖信息
	 */
	@RequestMapping(value = "/getAllLotteryInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> getAllLotteryInfo() {
		ResultBean<Object> resultBean = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return resultBean;
	}
	/**
	 * 重置或者插入地址信息
	 */
	@RequestMapping(value = "/resetAddress", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> resetAddress(@ModelAttribute AddressBean address, HttpServletRequest request) {
		ResultBean<Object> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}

	/**
	 * 获取用户地址信息
	 */
	@RequestMapping(value = "/getAddress",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<AddressBean> getAddress(HttpServletRequest request) {
		ResultBean<AddressBean> result = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return result;
	}
}
