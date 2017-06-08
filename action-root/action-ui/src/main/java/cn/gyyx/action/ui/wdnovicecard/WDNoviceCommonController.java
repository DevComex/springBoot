package cn.gyyx.action.ui.wdnovicecard;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.common.ActionUserLoginLog;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.wd9yearnovicebag.ServerBean;
import cn.gyyx.action.bll.common.ActionUserLoginLogBLL;
import cn.gyyx.action.bll.novicecard.ServerBLL;
import cn.gyyx.action.bll.wd9yearnovicebag.ServerConfigBLL;
import cn.gyyx.action.service.mobile.website.CallApi;
import cn.gyyx.action.service.wdnovicecard.WDNoviceCommonService;
import cn.gyyx.action.service.wdnovicecard.WDNoviceCommonUtil;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Throwables;

/**
 * 版 权：光宇游戏
 * 作 者：ChengLong 
 * 创建时间：2016年11月17日 下午12:49:24 
 * 描 述：新手礼包-公用
 */
@Controller
@RequestMapping("/wdnovice/common")
public class WDNoviceCommonController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDNoviceCommonController.class);
	
	private static final Integer gameId = 2;
	private WDNoviceCommonService commonService = new WDNoviceCommonService();
	
	private ServerConfigBLL serverConfigBLL = new ServerConfigBLL();
	ActionUserLoginLogBLL actionUserLoginLogBLL = new ActionUserLoginLogBLL();
	
	/**
	 * 新手礼包-获得服务器列表
	 */
	@RequestMapping("/bag/servers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServersOfBag(String activityId, Model model) {
		ResultBean<List<ServerBean>> result = new ResultBean<>(false,"接口异常",null);
		logger.info("获取新手礼包-服务器列表,活动ID:{}",activityId);
		try {
			if(activityId == null){
				activityId = "-1";
			}
			logger.info("获取新手礼包-服务器列表,活动id:{}",activityId);
			List<ServerBean> server = serverConfigBLL.getServerConfig(Integer.parseInt(activityId));
			
			return new ResultBean<>(true,"获取服务器列表成功",server);
		} catch (Exception e) {
			logger.error("获取服务器列表失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		return result;
	}
	
	/**
	 * 新手卡或礼包-获得服务器列表
	 */
	@RequestMapping("/card/servers")
	@ResponseBody
	public ResultBean<List<cn.gyyx.action.beans.novicecard.ServerBean>> getServersOfCard(Integer gameCategory,Integer netType, Integer batchNo) {
		ResultBean<List<cn.gyyx.action.beans.novicecard.ServerBean>> result = new ResultBean<>(false,"接口异常",null);
		logger.info("获取新手卡-获得服务器列表:gameCategory:"+gameCategory+",netType:"+netType+",batchNo:"+batchNo+"");
		try {
			if(gameCategory == null){
				gameCategory = -1;
			}
			if(netType == null){
				netType = -1;
			}
			if(batchNo == null){
				batchNo = -1;
			}
			ResultBean<List<cn.gyyx.action.beans.novicecard.ServerBean>> result1 = new ServerBLL().getServerByGameIdAndState(gameCategory, true, netType, batchNo);
			return new ResultBean<>(result1.getIsSuccess(),result1.getMessage(),result1.getData());
		} catch (Exception e) {
			logger.error("获取服务器列表失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 新手卡或礼包-获得服务器列表
	 */
	@RequestMapping("/servers")
	@ResponseBody
	public ResultBean<List<cn.gyyx.action.beans.novicecard.ServerBean>> getServersOfCardOrBag(Integer gameCategory,Integer netType, Integer batchNo) {
		ResultBean<List<cn.gyyx.action.beans.novicecard.ServerBean>> result = new ResultBean<>(false,"接口异常",null);
		logger.info("获取新手卡-获得服务器列表:gameCategory:"+gameCategory+",netType:"+netType+",batchNo:"+batchNo+"");
		try {
			if(gameCategory == null){
				gameCategory = -1;
			}
			if(netType == null){
				netType = -1;
			}
			if(batchNo == null){
				batchNo = -1;
			}
			ResultBean<List<cn.gyyx.action.beans.novicecard.ServerBean>> result1 = new ServerBLL().getServerByGameIdAndState(gameCategory, true, netType, batchNo);
			return new ResultBean<>(result1.getIsSuccess(),result1.getMessage(),result1.getData());
		} catch (Exception e) {
			logger.error("获取服务器列表失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		return result;
	}
	
	/**
	 * 发送短信验证码
	 */
	@RequestMapping("smscode")
	@ResponseBody
	public cn.gyyx.action.beans.ResultBean<String> obtainCAPTCHA(String phone, String CAPTCHA,
			HttpServletRequest request, HttpServletResponse response) {
		cn.gyyx.action.beans.ResultBean<String> result = new cn.gyyx.action.beans.ResultBean<>();
		result.setProperties(false, "发送短信验证码失败", "");
		if (StringUtils.isBlank(phone)) {
			result.setMessage("手机号码不能为空");
			return result;
		}
		if (StringUtils.isBlank(CAPTCHA)) {
			result.setMessage("图片验证码不能为空");
			return result;
		}
		if (WDNoviceCommonUtil.isRelease && !new Captcha(request, response).equals(CAPTCHA)) {
			result.setProperties(false, "图片验证码填写错误", "");
			return result;
		}
		result = new CallApi().getCAPTCHA(phone);
		return result;
	}
	
	/**
	 * PC端-领取新手卡
	 */
	@RequestMapping("/card/receive")
	@ResponseBody
	public ResultBean<String> receive(HttpServletRequest request,
			NoviceParameter parameter, HttpServletResponse response) {
		
		parameter.setGameId(gameId);
		ResultBean<String> result = new ResultBean<String>(false,"领取新手卡接口异常","");
		String uuid = UUID.randomUUID().toString().replace("-", "");
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if(userInfo == null){
				return new ResultBean<String>(false,"很抱歉，登陆超时,请重新登录!","");
			}
			parameter.setUserInfo(userInfo);
			parameter.setAccount(userInfo.getAccount());
			
			// 验证验证码
			logger.info("用户输入的验证码为{}", parameter.getCode());
			if (WDNoviceCommonUtil.isRelease && !new Captcha(request, response).equals(parameter.getCode())) {
				result.setProperties(false, "很抱歉，您的验证码填写不正确", "");
				return result;
			}
			
			result = commonService.receiveInPcOfCard(parameter, request,uuid);
		} catch (Exception e) {
			logger.error("PC端媒体开始领取新手卡失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		
		return result;
	}
	
	/**
	 * PC端-领取新手礼包
	 */
	@RequestMapping("/bag/receive")
	@ResponseBody
	public ResultBean<String> receiveInPcOfBag(HttpServletRequest request,NoviceParameter parameter, HttpServletResponse response) {
		parameter.setGameId(gameId);
		ResultBean<String> result = new ResultBean<String>(false,"领取新手礼包接口异常","");
		String uuid = UUID.randomUUID().toString().replace("-", "");
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if(userInfo == null){
				return new ResultBean<String>(false,"很抱歉，登陆超时,请重新登录!","");
			}
			parameter.setUserInfo(userInfo);
			parameter.setAccount(userInfo.getAccount());
			
			result = commonService.receiveInPcOfBag(parameter, request,uuid);
		} catch (Exception e) {
			logger.error("PC端官网开始领取新手礼包失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
		}
		
		return result;
	}
	
	/**
	 * 记录登录日志
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<Object> login(int aid,HttpServletRequest request, Model model) {
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				return new ResultBean<>(false, "no login", null);
			}
			if (StringUtils.isEmpty(userInfo.getLoginID())) {
				return new ResultBean<>(false, "the parameter of loginId is not legal", null);
			}
			ActionUserLoginLog bean = new ActionUserLoginLog();
			bean.setLoginId(userInfo.getLoginID());
			int count = actionUserLoginLogBLL.getListPagingCount(bean);
			if(count == 0){
				bean.setAccount(userInfo.getAccount()+"");
				bean.setUserId(userInfo.getUserId()+"");
				bean.setActivityCode(aid);
				bean.setLoginTime(new Date());
				bean.setLoginIp(userInfo.getLoginIP());
				actionUserLoginLogBLL.insert(bean);
			}
			return new ResultBean<Object>(true, "操作成功", "");
		} catch (Exception e) {
			logger.error("记录登录信息失败,错误堆栈:{}", Throwables.getStackTraceAsString(e));
			return new ResultBean<>(false, "记录登录信息接口异常", null);
		}
	}
	
}
