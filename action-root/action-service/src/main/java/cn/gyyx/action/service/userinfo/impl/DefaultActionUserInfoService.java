package cn.gyyx.action.service.userinfo.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.beans.userinfo.vo.UserInfoAndQualificationVO;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.userinfo.IActionUserInfoBLL;
import cn.gyyx.action.bll.userinfo.impl.DefaultActionUserInfoBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.userinfo.IActionUserInfoService;
import cn.gyyx.distribute.lock.DistributedLock;

public class DefaultActionUserInfoService implements IActionUserInfoService {

	private IActionUserInfoBLL actionUserInfoBLL = new DefaultActionUserInfoBLL();
	private IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();
	
	@Override
	public ResultBean<Map<String, Boolean>> getState(int activityId, String userId) {
		ResultBean<Map<String, Boolean>> result = new ResultBean<Map<String, Boolean>>();
		result.setIsSuccess(false);
		
		// 防止重复提交
		try(DistributedLock lock = new DistributedLock(activityId + "-" + userId + "-state-service")) {
			lock.weakLock(30, 0);
			
			ActionUserInfoPO po = actionUserInfoBLL.get(activityId, userId);
			if (null == po) po = new ActionUserInfoPO();
			
			Map<String, Boolean> stateMap = new HashMap<String, Boolean>();
			stateMap.put("isSign", StringUtils.isEmpty(po.getUserId()) ? false : true);
			stateMap.put("isBindGame", StringUtils.isEmpty(po.getServerName()) ? false : true);
			
			result.setData(stateMap);
			result.setIsSuccess(true);
		}
		catch(Exception e) {
			logger.error("DefaultActionUserInfoService->getState->errorCause:" + e.getCause());
			logger.error("DefaultActionUserInfoService->getState->errorMessage:" + e.getMessage());
			logger.error("DefaultActionUserInfoService->getState->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}
	
	public ResultBean<UserInfoAndQualificationVO> getUserInfo(int activityId, String userId) {
		ResultBean<UserInfoAndQualificationVO> result = new ResultBean<UserInfoAndQualificationVO>();
		result.setIsSuccess(false);
		
		UserInfoAndQualificationVO resultVO = actionUserInfoBLL.getUserInfo(activityId, userId);
		if (null != resultVO) {
			result.setData(resultVO);
			result.setIsSuccess(true);
		}
		
		return result;
	}
	
	@Override
	public ResultBean<String> signUp(ActionUserInfoPO params) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		
		if (null == params) {
			result.setMessage("报名参数不能为空！");
			return result;
		}
		if (null == params.getActivityId()) {
			result.setMessage("活动ID不能为空！");
			return result;
		}
		if (null == params.getUserId()) {
			result.setMessage("用户ID不能为空！");
			return result;
		}
		
		// 防止重复提交
		try(DistributedLock lock = new DistributedLock(params.getActivityId() + "-" + params.getUserId() + "-signup-service")) {
			lock.weakLock(30, 0);
			
			// 是否在活动期间内
			int hdState = hdConfigBLL.getState(params.getActivityId());
			if (1 != hdState) {
				logger.info("ActionExchangeServiceImpl->exchange->activation is not in progress.");
				result.setStateCode(hdState);
				result.setMessage(hdConfigBLL.getMessage());
				return result;
			}
			
			ActionUserInfoPO po = actionUserInfoBLL.get(params.getActivityId(), params.getUserId());
			if (null != po && null != po.getUserId()) {
				result.setMessage("已经报过名！");
				return result;
			}
			
			SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();
			
			try {
				if (actionUserInfoBLL.put(params, session) > 0) {
					session.commit();
					
					result.setMessage("报名成功！");
					result.setIsSuccess(true);
				}
				else {
					session.rollback();
				}
			}
			catch(Exception sqle) {
				if (null != session) session.rollback();
				logger.error("DefaultActionUserInfoService->signUP->errorCause:" + sqle.getCause());
				logger.error("DefaultActionUserInfoService->signUP->errorMessage:" + sqle.getMessage());
				logger.error("DefaultActionUserInfoService->signUP->StackTrace:" + sqle.getStackTrace());
			}
			finally {
				if (null != session) session.close();
			}
		}
		catch(Exception e) {
			logger.error("DefaultActionUserInfoService->signUP->errorCause:" + e.getCause());
			logger.error("DefaultActionUserInfoService->signUP->errorMessage:" + e.getMessage());
			logger.error("DefaultActionUserInfoService->signUP->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}

	@Override
	public ResultBean<String> bindGameRole(ActionUserInfoPO params) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		
		if (null == params) {
			result.setMessage("报名参数不能为空！");
			return result;
		}
		if (null == params.getActivityId()) {
			result.setMessage("活动ID不能为空！");
			return result;
		}
		if (null == params.getUserId()) {
			result.setMessage("用户ID不能为空！");
			return result;
		}
		if (null == params.getServerId()) {
			result.setMessage("服务器ID不能为空！");
			return result;
		}
		if (null == params.getServerName()) {
			result.setMessage("服务器名称不能为空！");
			return result;
		}
		
		// 防止重复提交
		String key = params.getActivityId() + "-" + params.getUserId() + "-" + this.getClass().getName();
		try(DistributedLock lock = new DistributedLock(key)) {
			lock.weakLock(30, 0);
			
			// 是否在活动期间内
			int hdState = hdConfigBLL.getState(params.getActivityId());
			if (1 != hdState) {
				logger.info("ActionExchangeServiceImpl->exchange->activation is not in progress.");
				result.setStateCode(hdState);
				result.setMessage(hdConfigBLL.getMessage());
				return result;
			}
			
			ActionUserInfoPO po = actionUserInfoBLL.get(params.getActivityId(), params.getUserId());
			if (null == po) {
				result.setMessage("请先报名！");
				return result;
			}
			else if (null != po.getServerId()) {
				result.setMessage("已经绑定过服务器！");
				return result;
			}
			
			SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();
			
			try {
				if (actionUserInfoBLL.push(params, session) > 0) {
					session.commit();
					
					result.setMessage("绑定成功！");
					result.setIsSuccess(true);
				}
				else {
					session.rollback();
				}
			}
			catch(Exception sqle) {
				if (null != session) session.rollback();
				logger.error("DefaultActionUserInfoService->bindGameRole->errorCause:" + sqle.getCause());
				logger.error("DefaultActionUserInfoService->bindGameRole->errorMessage:" + sqle.getMessage());
				logger.error("DefaultActionUserInfoService->bindGameRole->StackTrace:" + sqle.getStackTrace());
			}
			finally {
				if (null != session) session.close();
			}
		}
		catch(Exception e) {
			logger.error("DefaultActionUserInfoService->bindGameRole->errorCause:" + e.getCause());
			logger.error("DefaultActionUserInfoService->bindGameRole->errorMessage:" + e.getMessage());
			logger.error("DefaultActionUserInfoService->bindGameRole->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}
}
