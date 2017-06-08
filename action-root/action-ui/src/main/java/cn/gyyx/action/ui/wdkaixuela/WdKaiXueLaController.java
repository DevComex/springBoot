package cn.gyyx.action.ui.wdkaixuela;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ServerBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
  * 问道 开学啦 活动controller
  *
  * @author lihu
  * 注释时间 : 2016年8月15日
  */
@Controller
@RequestMapping("/kaixuela")
public class WdKaiXueLaController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WdKaiXueLaController.class);
	public static final int GAMEID = 2; //游戏主键
	@RequestMapping("/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/index";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/login";
	}
	
	@RequestMapping("/bindPage")
	public String bindPage(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/bindPage";
	}
	
	@RequestMapping("/singup")
	public String singup(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/nologinsingup";
	}
	
	@RequestMapping("/lottery")
	public String lottery(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/lotteryPage";
	}
	
	@RequestMapping("/rule")
	public String rule(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/rulepage";
	}
	
	@RequestMapping("/commentshow")
	public String commentshow(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/commentshowpage";
	}
	
	@RequestMapping("/coment")
	public String coment(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/comentpage";
	}
	
	@RequestMapping("/singwating")
	public String singwating(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/singwating";
	}
	
	@RequestMapping("/singover")
	public String singover(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/singover";
	}
	
	@RequestMapping("/singno")
	public String singno(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/singno";
	}
	
	@RequestMapping("/fxpage")
	public String fxpage(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "wdkaixuela/fxpage";
	}
	
	
	/**
	 * 获取登录用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryUser" )
	@ResponseBody
	public ResultBean<Object> queryUser(HttpServletRequest request,
			 HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"活动已结束，谢谢参与",null);

		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			result.setIsSuccess(false);
			result.setMessage("请先登录");
			return result;
		}
		return result;
	}
	
	
	/**
	 * 绑定角色
	 * @param request
	 * @param wsuBean
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bindRole" )
	@ResponseBody
	public ResultBean<Object> bindRole(HttpServletRequest request,
			 HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"活动已结束，谢谢参与",null);
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			logger.info("bindRole  userInfo :" ,userInfo);
			if (userInfo == null) {
				result.setIsSuccess(false);
				result.setMessage("请先登录");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setProperties(false, "活动已结束，谢谢参与", null);
		}
		return result;
	}
	
	/** 
	* 叙述:报名接口<br />
	* @param request
	* @param wxBean
	* @param response
	* @return ResultBean<Object>
	*/
	@RequestMapping(value = "/enroll" )
	@ResponseBody
	public ResultBean<Object> enroll(HttpServletRequest request,
			String school, String imgUrl, HttpServletResponse response) {
		ResultBean<Object> result = new ResultBean<>(false,"活动已结束，谢谢参与",null);
		if(school==null||imgUrl==null||school.equals("")||imgUrl.equals("")){
			result.setIsSuccess(false);
			result.setMessage("请选择正确的门派或图片");
			return result;
		}
		try {
			  UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.setIsSuccess(false);
				result.setMessage("请先登录");
				return result;
			}
		} catch (RuntimeException e) {
			result.setIsSuccess(false);
			result.setMessage("活动已结束，谢谢参与" );
			result.setData(e);
		}
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
		boolean cResult = checkLogin(request);
		ResultBean<ServerBean> result = new ResultBean<ServerBean>(false,"活动已结束，谢谢参与",null);
		 
		logger.info("getservers  ","nettype="+nettype);
		 if(!cResult){// 登陆不成功
			result.setIsSuccess(false);
			result.setMessage("请先登录");
			return result;
		}
		return result;
	}
	
	/**
	  * @Title: getRoles
	  * @Description:  获取角色信息
	  * @param serverId 服务器ID
	   
	  * @param request 请求
	  * @return ResultBean<ServerBean> 
	  * @throws
	  */
	@RequestMapping(value="/getRoles")
	@ResponseBody
	public ResultBean<Object> getRoles(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "serverId") int serverId,@RequestParam(value = "veCode") String veCode) {
		ResultBean<Object> result = new ResultBean<>(false,"活动已结束，谢谢参与",null);
		logger.info("getservers  ","serverId"+serverId+"veCode="+veCode);
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			result.setIsSuccess(false);
			result.setMessage("请先登录");
			return result;
		} 
		if (!new Captcha(request, response).equals(veCode)) {
			result.setIsSuccess(false);
			result.setMessage("很抱歉，您的验证码填写不正确");
			return result;
		}
		return result;
	}
	
	
	/**
	 * 增加地址
	 * 
	 * @param prizeExchangeLogBean
	 * @return
	 */
	@RequestMapping(value = "/addAddress" )
	@ResponseBody
	public ResultBean<Integer> resetAddress( HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<>(false,"活动已结束，谢谢参与",null);
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.setIsSuccess(false);
				result.setMessage("请先登录");
				return result;
			} 
			
		} catch (Exception e) {
			logger.info("addAddress ",e.getMessage());
		}
		return result;
	}
	
	/**
	 * 排名展示
	 * @param school
	 * @return
	 */
	@RequestMapping(value="/Ranking/display")
	@ResponseBody
	public ResultBean<Object> display(@RequestParam(value = "school")String school){
		ResultBean<Object> result = new ResultBean<Object>(false,"活动已结束，谢谢参与",null);
		return result;
	}
	
	
	/**
	 * 评论展示
	 * @param school
	 * @return
	 */
	@RequestMapping(value="/comment/findComment")
	@ResponseBody
	public ResultBean<Object> findComment(@RequestParam(value = "school")String school){
		ResultBean<Object> result = new ResultBean<Object>(false,"活动已结束，谢谢参与",null);
		return result;
	}
	/**
	 * 添加评论接口
	 * @param request
	 * @param school
	 * @param comment
	 * @return
	 */
	@RequestMapping(value="/comment/addComment")
	@ResponseBody
	public ResultBean<Object> addComment(HttpServletRequest request,@RequestParam(value = "school")String school,@RequestParam(value = "comment")String comment){
		ResultBean<Object> result = new ResultBean<Object>(false,"活动已结束，谢谢参与",null);
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.setIsSuccess(false);
				result.setMessage("请先登录");
				return result;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsSuccess(false);
			result.setMessage("添加评论失败");
		}
		return result;
	}
	 /**
	  * 用户抽奖 操作
	  * @param request
	  * @param serverName
	  * @param serverCode
	  * @param actionCode
	  * @return
	  * @throws LotteryException
	  * @throws UnsupportedEncodingException
	  */
	@RequestMapping(value = "/getLottery")
	@ResponseBody
	public ResultBean<UserLotteryBean> getPredicableLottery(
			HttpServletRequest request,
			@RequestParam(value = "serverName") String serverName,
			@RequestParam(value = "serverCode") int serverCode,
			@RequestParam(value = "actionCode") int actionCode)
			throws LotteryException, UnsupportedEncodingException {
		ResultBean<UserLotteryBean> resultBean = new ResultBean<>(false,"活动已结束，谢谢参与",null);
		// 用户信息
		UserInfo userInfo = null;
		try {
			userInfo = SignedUser.getUserInfo(request);
			logger.info("UserInfo", userInfo);
			if (userInfo == null) {
				return new ResultBean<>(false,
						"无法探测到您的账户信息，请重新登录", null);
			}
		} catch (Exception e) {
			logger.warn("取不到账户信息");
			logger.warn(e.getMessage());
			return new ResultBean<>(false, "无法探测到您的账户信息，请重新登录",
					null);
		}
		return resultBean;
	}
	
	/**
	 * 查看我的奖励
	 */
	@RequestMapping(value = "/queryMyPrice")
	@ResponseBody
	public ResultBean<Object> queryMyPrice(
			HttpServletRequest request){
		ResultBean<Object> resultBean = new ResultBean<>(false,"活动已结束，谢谢参与",null);
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("请先登录");
			return resultBean;
		}
		return resultBean;
		
	}
	/**
	 * 获奖信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querAllPrice")
	@ResponseBody
	public ResultBean<Object> querAllPrice(
			HttpServletRequest request,@RequestParam(value = "actionCode") int actionCode){
		ResultBean<Object> resultBean = new ResultBean<>(false,"活动已结束，谢谢参与",null);
		return resultBean;
	}
	/**
	 * 检测登录
	 * @param request
	 * @return
	 */
	private boolean checkLogin(HttpServletRequest request) {
		UserInfo userInfo = SignedUser.getUserInfo(request);

		boolean isSuccess = false;

		if (userInfo != null) {
			isSuccess = true;

		}

		return isSuccess;
	}
	
}
