package cn.gyyx.action.dao.lottery.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.mapper.IActionLotteryLogMapper;

public class ActionLotteryLogDAOImpl extends MyBatisBaseDAO implements IActionLotteryLogDAO {

	@Override
	public List<ActionLotteryLogPO> getDataList(ActionLotteryLogPO po) {
		List<ActionLotteryLogPO> result = null;
		
		try(SqlSession session = this.getSession()) {
			// 默认查询抽奖日志
			if (po.getActivityType() == null) po.setActivityType("lottery");
			
			IActionLotteryLogMapper mapper = session.getMapper(IActionLotteryLogMapper.class);
			result = mapper.getDataList(po);
		}
		catch(Exception e) {
			logger.error("ActionLotteryLogDAOImpl.getPrizesInfomations", e);
		}
		
		return result;
	}
	
	@Override
	public int putData(ActionLotteryLogPO po, SqlSession session) throws Exception {
		int result = 0;
		
		try {
			// 默认查询抽奖日志
			if (po.getActivityType() == null) po.setActivityType("lottery");
			
			IActionLotteryLogMapper mapper = session.getMapper(IActionLotteryLogMapper.class);
			result = mapper.putData(po);
		}
		catch(Exception e) {
			logger.error("ActionLotteryLogDAOImpl.getPrizesInfomations", e);
			throw new Exception(e.getCause());
		}
		
		return result;
	}
	
	@Override
	public int isExsits(ActionLotteryLogPO po) {
		int result = -1;
		
		logger.info("ActionLotteryLogDAOImpl->isExsits->params=" + JSON.toJSONString(po));
		
		try(SqlSession session = this.getSession()) {
			IActionLotteryLogMapper mapper = session.getMapper(IActionLotteryLogMapper.class);
			
			result = mapper.selectCount(po);
		}
		catch(Exception e) {
			logger.error("ActionLotteryLogDAOImpl->isExsits->errorCause:" + e.getCause());
			logger.error("ActionLotteryLogDAOImpl->isExsits->errorMessage:" + e.getMessage());
			logger.error("ActionLotteryLogDAOImpl->isExsits->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionLotteryLogDAOImpl->isExsits->result=" + result);
		
		return result;
	}

	public List<ActionLotteryLogPO> selectExceptThankYou(ActionLotteryLogPO params) {
		List<ActionLotteryLogPO> result = null;
		logger.info("ActionLotteryLogDAOImpl->selectExceptThankYou->start.");
		
		try(SqlSession session = this.getSession()) {
			IActionLotteryLogMapper mapper = session.getMapper(IActionLotteryLogMapper.class);
			result = mapper.selectExceptThankYou(params);
		}
		catch(Exception e) {
			logger.error("ActionLotteryLogDAOImpl->selectExceptThankYou->errorCause:" + e.getCause());
			logger.error("ActionLotteryLogDAOImpl->selectExceptThankYou->errorMessage:" + e.getMessage());
			logger.error("ActionLotteryLogDAOImpl->selectExceptThankYou->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionLotteryLogDAOImpl->selectExceptThankYou->result=" + JSON.toJSONString(result));
		
		return result;
	}
	
	@Override
	public int getCountTodayByAccount(String account, int activityCode) {
		try(SqlSession session = this.getSession()) {
			IActionLotteryLogMapper mapper = session.getMapper(IActionLotteryLogMapper.class);
			return mapper.getCountTodayByAccount(account,activityCode);
		}
	}
}
