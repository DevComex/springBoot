package cn.gyyx.action.bll.lottery.impl;

import java.util.List;

import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;
import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.bll.lottery.ILotteryProbabilityBLL;
import cn.gyyx.action.dao.config.IActionLotteryChanceDAO;
import cn.gyyx.action.dao.config.IActionPrizeDAO;
import cn.gyyx.action.dao.config.Impl.ActionLotteryChanceDAOImpl;
import cn.gyyx.action.dao.config.Impl.ActionPrizeDAOImpl;

public class LotteryProbabilityBLLImplByAvailable implements ILotteryProbabilityBLL {

	private IActionLotteryChanceDAO actionLotteryChanceDAO = new ActionLotteryChanceDAOImpl();
	
	@Override
	public List<ActionLotteryChancePO> getDataList(int activityId) {
		return actionLotteryChanceDAO.getDataListByAvailable(activityId);
	} 
}
