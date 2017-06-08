package cn.gyyx.action.dao.lottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;

public interface IActionLotteryLogDAO {
	
	List<ActionLotteryLogPO> getDataList(ActionLotteryLogPO po);
	
	int putData(ActionLotteryLogPO po, SqlSession session) throws Exception; 
	
	int isExsits(ActionLotteryLogPO po);
	
	List<ActionLotteryLogPO> selectExceptThankYou(ActionLotteryLogPO params);
	
	int getCountTodayByAccount(String account, int activityCode);
}
