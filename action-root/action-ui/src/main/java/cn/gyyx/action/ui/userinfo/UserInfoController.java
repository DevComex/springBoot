package cn.gyyx.action.ui.userinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ibm.icu.util.Calendar;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ServerBean;
import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.beans.userinfo.vo.UserInfoAndQualificationVO;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.action.service.userinfo.IActionUserInfoService;
import cn.gyyx.action.service.userinfo.IGameDataConvertService;
import cn.gyyx.action.service.userinfo.impl.ConvertByLevelAndProficiencyService;
import cn.gyyx.action.service.userinfo.impl.DefaultActionUserInfoService;
import cn.gyyx.action.ui.BaseController;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.service.WDGameAgent;

@Controller
@RequestMapping("/user")
public class UserInfoController extends BaseController {

	private IActionUserInfoService actionUserInfoService = new DefaultActionUserInfoService();
	private IGameDataConvertService gameDataConvertService = new ConvertByLevelAndProficiencyService();
	
	@ResponseBody
	@RequestMapping(value = "/y/state", method = RequestMethod.GET)
	public ResultBean<Map<String, Boolean>> getState(int activityId, HttpServletRequest request) {
		ResultBean<Map<String, Boolean>> result = new ResultBean<Map<String, Boolean>>();
		result.setIsSuccess(false);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("ActionExchangeController->isExsits->please login.");
				result.setStateCode(100);
				return result;
			}
			
			result = actionUserInfoService.getState(activityId, userInfo.getAccount());
		}
		catch(Exception e) {
			logger.error("UserInfoController->getState->errorCause:" + e.getCause());
			logger.error("UserInfoController->getState->errorMessage:" + e.getMessage());
			logger.error("UserInfoController->getState->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/y/signup")
	public ResultBean<String> signUp(ActionUserInfoPO params,
			HttpServletRequest request) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("ActionExchangeController->isExsits->please login.");
				result.setStateCode(100);
				return result;
			}
			
			if (null == params || null == params.getActivityId()) {
				result.setMessage("参数不能为空！");
				return result;
			}
			
			params.setUserId(userInfo.getAccount());
			params.setUserNick(userInfo.getNickname());
			params.setIp(this.getIp(request));
			params.setSource(this.getUserAgent(request));
			params.setCreateAt(Calendar.getInstance().getTime());
			
			result = actionUserInfoService.signUp(params);
		}
		catch(Exception e) {
			logger.error("UserInfoController->signUp->errorCause:" + e.getCause());
			logger.error("UserInfoController->signUp->errorMessage:" + e.getMessage());
			logger.error("UserInfoController->signUp->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/y/put/game", method = RequestMethod.POST)
	public ResultBean<String> bindGame(int activityId, int serverId, String serverName, String roleId, String roleName,
			HttpServletRequest request) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("ActionExchangeController->isExsits->please login.");
				result.setStateCode(100);
				return result;
			}
			
			ActionUserInfoPO params = new ActionUserInfoPO();
			params.setActivityId(activityId);
			params.setUserId(userInfo.getAccount());
			params.setUserNick(userInfo.getNickname());
			params.setServerId(serverId);
			params.setServerName(serverName);
			params.setRoleId(roleId);
			params.setRoleName(roleName);
			params.setModifyAt(Calendar.getInstance().getTime());
			
			result = actionUserInfoService.bindGameRole(params);
		}
		catch(Exception e) {
			logger.error("UserInfoController->signUp->errorCause:" + e.getCause());
			logger.error("UserInfoController->signUp->errorMessage:" + e.getMessage());
			logger.error("UserInfoController->signUp->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/y/get", method = RequestMethod.GET)
	public ResultBean<UserInfoAndQualificationVO> get(@RequestParam int activityId, 
			HttpServletRequest request, HttpServletResponse response) {
		ResultBean<UserInfoAndQualificationVO> result = new ResultBean<UserInfoAndQualificationVO>();
		result.setIsSuccess(false);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("ActionExchangeController->get->please login.");
				result.setStateCode(100);
				return result;
			}
			
			result = actionUserInfoService.getUserInfo(activityId, userInfo.getAccount());
		}
		catch(Exception e) {
			logger.error("UserInfoController->get->errorCause:" + e.getCause());
			logger.error("UserInfoController->get->errorMessage:" + e.getMessage());
			logger.error("UserInfoController->get->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/y/servers")
	public ResultBean<ServerBean> getServers(int type, HttpServletRequest request) {
		ResultBean<ServerBean> result = new ResultBean<ServerBean>();
		result.setIsSuccess(false);
		
		logger.info("UserInfoController->getServers->start.type=" + type);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("UserInfoController->getServers->please login.");
				result.setStateCode(100);
				return result;
			}
			
			WDGameAgent gameAgent = new WDGameAgent();
			
			result.setData(gameAgent.getServers(2, type));
			result.setIsSuccess(true);
		}
		catch(Exception e) {
			logger.error("UserInfoController->getServers->errorCause:" + e.getCause());
			logger.error("UserInfoController->getServers->errorMessage:" + e.getMessage());
			logger.error("UserInfoController->getServers->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("UserInfoController->getServers->result=" + JSON.toJSONString(result));
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/y/role")
	public ResultBean<Map<String, Object>> getRole(int serverId, String code, 
			HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Map<String, Object>> result = new ResultBean<Map<String, Object>>();
		result.setIsSuccess(false);
		
		logger.info("UserInfoController->getRole->start.");
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("UserInfoController->getRole->please login.");
				result.setStateCode(100);
				return result;
			}
			
			if (!new Captcha(request, response).equals(code)) {
				logger.info("UserInfoController->getRole->validate code faild.");
				result.setMessage("验证码不正确！");
				return result;
			}
			
			String roleInfo = CallWebServiceInsuranceAgent.getRoleInfo(userInfo.getAccount(), serverId);
			logger.info("UserInfoController->getRole->roleInfo=" + roleInfo);
			
			Map<String, Object> roleMap = JSON.parseObject(roleInfo, HashMap.class);
			
			if (roleMap.containsKey("IsSuccess") && roleMap.containsKey("List")) {
				if (true == Boolean.parseBoolean(roleMap.get("IsSuccess").toString())) {
					if (null != roleMap.get("List")) {
						String roleStr = JSON.toJSONString(roleMap.get("List"));
						logger.info("UserInfoController->getRole->roleStr=" + roleStr);
						
						List<Map<String, Object>> roleList = JSON.parseObject(roleStr, ArrayList.class);
						if (null != roleList && roleList.size() > 0) {
							result.setRows(roleList);
							result.setIsSuccess(true);
						}
					}
				}
			}
		}
		catch(Exception e) {
			logger.error("UserInfoController->getRole->errorCause:" + e.getCause());
			logger.error("UserInfoController->getRole->errorMessage:" + e.getMessage());
			logger.error("UserInfoController->getRole->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("UserInfoController->getRole->result=" + JSON.toJSONString(result));
		
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/y/put/score", method = RequestMethod.GET)
	public ResultBean<String> pubScore(int activityId, HttpServletRequest request) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		
		logger.info("UserInfoController->pubScore->start.activityId=" + activityId);
		
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (null == userInfo) {
				logger.info("UserInfoController->pubScore->please login.");
				result.setStateCode(100);
				return result;
			}
			
			logger.info("UserInfoController->pubScore->userInfo=" + JSON.toJSONString(userInfo));
			
			result.setIsSuccess(gameDataConvertService.excute(activityId, userInfo.getAccount(), this.getIp(request)));
		}
		catch(Exception e) {
			logger.error("UserInfoController->pubScore->errorCause:" + e.getCause());
			logger.error("UserInfoController->pubScore->errorMessage:" + e.getMessage());
			logger.error("UserInfoController->pubScore->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("UserInfoController->pubScore->result=" + JSON.toJSONString(result));
		
		return result;
	}
}
