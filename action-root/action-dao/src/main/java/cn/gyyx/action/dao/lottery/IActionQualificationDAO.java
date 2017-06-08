package cn.gyyx.action.dao.lottery;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;

public interface IActionQualificationDAO {

	Map<String, Integer> getLotteryCountAndMustWinCount(String userId, int activityId);
	
	int getLotteryCount(String userId, int activityId);
	
	int getMustWinCount(String userId, int activityId);
	
	ActionQualificationPO getData(String userId, int activityId);
	
	int putLotteryCountMinusOne(ActionQualificationPO po, SqlSession session) throws Exception;
	
	int putMustWinCountMinusOne(ActionQualificationPO po, SqlSession session) throws Exception;
	
	int insert(ActionQualificationPO po, SqlSession session);
	
	int minusLotteryScore(ActionQualificationPO po, SqlSession session);
	
	int addLotteryScore(ActionQualificationPO po, SqlSession session);

	int putLotteryCountMinusOneByDate(ActionQualificationPO po, SqlSession session) throws Exception;
	
}
