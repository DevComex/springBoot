package cn.gyyx.action.dao.config;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;

public interface IActionLotteryChanceDAO {

	List<ActionLotteryChancePO> getDataList(int activityId);
	
	List<ActionLotteryChancePO> getDataListByAvailable(int activityId);
	
	ActionLotteryChancePO getData(ActionLotteryChancePO po);
	
	int putNumber(ActionLotteryChancePO po, SqlSession session) throws Exception ;
}
