package cn.gyyx.action.bll.lottery.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.ibm.icu.util.Calendar;

import cn.gyyx.action.beans.lottery.po.ActionQualificationLogPO;
import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.bll.lottery.IActionQualificationBLL;
import cn.gyyx.action.dao.lottery.IActionQualificationDAO;
import cn.gyyx.action.dao.lottery.IActionQualificationLogDAO;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationLogDAOImpl;
import cn.gyyx.distribute.lock.DistributedLock;

public class ActionQualificationBLLImpl implements IActionQualificationBLL {

	private IActionQualificationDAO actionQualificationDAO = new ActionQualificationDAOImpl();
	private IActionQualificationLogDAO actionQualificationLogDAO = new ActionQualificationLogDAOImpl();
	
	@Override
	public int getScore(int activityId, String userId) {
		ActionQualificationPO po =  actionQualificationDAO.getData(userId, activityId);
		if (null != po && null != po.getLotteryScore()) {
			return po.getLotteryScore();
		}
		
		return -1;
	}
	
	@Override
	public int addLotteryScore(int activityId, String userId, int score, String type, String ip, SqlSession session) {
		if (score > 0) {
			ActionQualificationPO params = new ActionQualificationPO();
			params.setUserId(userId);
			params.setActivityId(activityId);
			params.setLotteryScore(score);
			
			String key = activityId + "-" + userId + "-" + this.getClass().getName();
			try(DistributedLock lock = new DistributedLock(key)) {
				lock.weakLock(30, 0);
				
				if (actionQualificationDAO.addLotteryScore(params, session) > 0) {
					ActionQualificationLogPO logParams = new ActionQualificationLogPO();
					logParams.setActivityId(activityId);
					logParams.setUserId(userId);
					logParams.setIp(ip);
					logParams.setCreateAt(Calendar.getInstance().getTime());
					
					Map<String, Object> operateMap = new HashMap<String, Object>();
					operateMap.put("type", type);
					operateMap.put("add", score);
					
					logParams.setOperate(JSON.toJSONString(operateMap));
					
					return actionQualificationLogDAO.insert(logParams, session);
				}
			}
			catch(Exception e) {
				logger.error("ActionQualificationBLLImpl->addLotteryScore->errorCause:" + e.getCause());
				logger.error("ActionQualificationBLLImpl->addLotteryScore->errorMessage:" + e.getMessage());
				logger.error("ActionQualificationBLLImpl->addLotteryScore->StackTrace:" + e.getStackTrace());
			}
		}
		
		return -1;
	}
	
	@Override
	public int minusLotteryScore(int activityId, String userId, int score, String type, String ip, SqlSession session) {
		if (score > 0) {
			ActionQualificationPO params = new ActionQualificationPO();
			params.setUserId(userId);
			params.setActivityId(activityId);
			params.setLotteryScore(score);
			
			String key = activityId + "-" + userId + "-" + this.getClass().getName();
			try(DistributedLock lock = new DistributedLock(key)) {
				lock.weakLock(30, 0);
				
				if (actionQualificationDAO.minusLotteryScore(params, session) > 0) {
					ActionQualificationLogPO logParams = new ActionQualificationLogPO();
					logParams.setActivityId(activityId);
					logParams.setUserId(userId);
					logParams.setIp(ip);
					logParams.setCreateAt(Calendar.getInstance().getTime());
					
					Map<String, Object> operateMap = new HashMap<String, Object>();
					operateMap.put("type", type);
					operateMap.put("minus", score);
					
					logParams.setOperate(JSON.toJSONString(operateMap));
					
					return actionQualificationLogDAO.insert(logParams, session);
				}
			}
			catch(Exception e) {
				logger.error("ActionQualificationBLLImpl->minusLotteryScore->errorCause:" + e.getCause());
				logger.error("ActionQualificationBLLImpl->minusLotteryScore->errorMessage:" + e.getMessage());
				logger.error("ActionQualificationBLLImpl->minusLotteryScore->StackTrace:" + e.getStackTrace());
			}
		}
		
		return -1;
	}

	@Override
	public boolean getState(int activityId, String userId, String type) {
		ActionQualificationLogPO params = new ActionQualificationLogPO();
		params.setActivityId(activityId);
		params.setUserId(userId);
		params.setOperate(type);
		params.setCreateAt(Calendar.getInstance().getTime());
		
		List<ActionQualificationLogPO> result = actionQualificationLogDAO.selectDataToday(params);
		
		if (null == result || 0 == result.size()) {
			return false;
		}
		
		return true;
	}
}
