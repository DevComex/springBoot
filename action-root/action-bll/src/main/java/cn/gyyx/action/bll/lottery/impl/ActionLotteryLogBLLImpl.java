package cn.gyyx.action.bll.lottery.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;

public class ActionLotteryLogBLLImpl implements IActionLotteryLogBLL {

	private IActionLotteryLogDAO actionLotteryLogDAO = new ActionLotteryLogDAOImpl();
	
	public List<ActionLotteryLogPO> getDataList(ActionLotteryLogPO po) {
		return actionLotteryLogDAO.getDataList(po);
	}
	
	public int putData(ActionLotteryLogPO po, SqlSession session) throws Exception {
		return actionLotteryLogDAO.putData(po, session);
	}
	
	// 奖品是否存在
	public int isExsits(ActionLotteryLogPO po) {
		logger.info("ActionLotteryLogBLLImpl->isExsits->params=" + JSON.toJSONString(po));
		return actionLotteryLogDAO.isExsits(po);
	}
	
	public List<ActionLotteryLogPO> getDataExceptThankYou(ActionLotteryLogPO params) {
		logger.info("ActionLotteryLogBLLImpl->getDataExceptThankYou->params=" + JSON.toJSONString(params));
		return actionLotteryLogDAO.selectExceptThankYou(params);
	}
	
	@Override
	public int getCountTodayByAccount(String account, int activityCode) {
		return actionLotteryLogDAO.getCountTodayByAccount(account,activityCode);
	}
}
