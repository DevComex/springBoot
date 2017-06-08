package cn.gyyx.action.bll.lottery.impl;

import java.util.Map;

import cn.gyyx.action.bll.lottery.ILotteryCountBLL;
import cn.gyyx.action.dao.lottery.IActionQualificationDAO;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;

public class LotteryCountBLLImpl implements ILotteryCountBLL {

	private IActionQualificationDAO actionQualificationDAO = new ActionQualificationDAOImpl();
	
	// 根据用户ID、活动ID获得抽奖次数、必中抽奖次数
	@Override
	public Map<String, Integer> getLotteryCountAndMustWinCount(String userId, int activityId) {
		return actionQualificationDAO.getLotteryCountAndMustWinCount(userId, activityId);
	}
	
	
}
